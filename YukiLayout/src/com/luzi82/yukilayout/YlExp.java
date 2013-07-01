package com.luzi82.yukilayout;

import java.text.ParseException;
import java.util.LinkedList;

public class YlExp {

	enum Token {
		NUMBER, OPERATOR, CHAR, NULL, DOT, HEX
	}

	public static String[] parse(String string) throws ParseException {
		LinkedList<String> out = new LinkedList<String>();
		LinkedList<String> stack = new LinkedList<String>();

		int off = 0;

		Token lastToken = Token.NULL;

		while (off < string.length()) {
			Token token = Token.NULL;
			StringBuffer sb = new StringBuffer();
			while (off < string.length()) {
				char c = string.charAt(off);
				Token t = toToken(c);
				if (t == null) {
					throw new ParseException(string, off);
				}
				if (token == Token.NULL) {
					if (t != Token.NULL) {
						sb.append(c);
					}
					token = t;
					++off;
				} else if (token == Token.NUMBER) {
					if (sb.toString().equals("0") && c == 'x') {
						token = Token.HEX;
						sb.append(c);
						++off;
					} else if ((t != Token.NUMBER) && (t != Token.DOT)) {
						break;
					} else {
						sb.append(c);
						++off;
					}
				} else if (token == Token.OPERATOR) {
					break;
				} else if (token == Token.CHAR) {
					if ((t != Token.CHAR) && (t != Token.NUMBER))
						break;
					sb.append(c);
					++off;
				} else if (token == Token.HEX) {
					if ((t != Token.CHAR) && (t != Token.NUMBER))
						break;
					sb.append(c);
					++off;
				} else if (token == Token.DOT) {
					break;
				}
			}
			String s = sb.toString();
			if (token == Token.NULL) {
				// do nothing
			} else if ((token == Token.CHAR) || (token == Token.NUMBER)
					|| (token == Token.HEX)) {
				out.addLast(s);
			} else if ((token == Token.DOT) || (token == Token.OPERATOR)) {
				if (s.equals("-") && (lastToken != Token.CHAR)
						&& (lastToken != Token.NUMBER)) {
					s = "--";
				}
				if (s.equals("(") && (lastToken != Token.OPERATOR)
						&& (lastToken != Token.NULL)) {
					s = "((";
				}
				if (s.equals("(") || s.equals("((")) {
					stack.push(s);
				} else if (s.equals(",")) {
					while (true) {
						if (stack.size() <= 0) {
							throw new ParseException(string, off);
						}
						if ("(".equals(stack.peek()))
							break;
						if ("((".equals(stack.peek()))
							break;
						if (",".equals(stack.peek()))
							break;
						out.addLast(stack.pop());
					}
					stack.push(s);
				} else if (s.equals(")")) {
					int commaCount = 0;
					while (true) {
						if (stack.size() <= 0) {
							throw new ParseException(string, off);
						}
						if (",".equals(stack.peek())) {
							++commaCount;
							stack.pop();
							continue;
						}
						if ("(".equals(stack.peek()))
							break;
						if ("((".equals(stack.peek()))
							break;
						out.addLast(stack.pop());
					}
					if (stack.peek().equals("(")) {
						if (commaCount > 0) {
							throw new ParseException(string, off);
						}
					} else if (stack.peek().equals("((")) {
						out.addLast("" + (commaCount + 1));
						out.addLast("@");
					}
					stack.pop();
				} else {
					while (true) {
						if (stack.size() <= 0)
							break;
						String peek = stack.peek();
						if (peek.equals("("))
							break;
						if (peek.equals("(("))
							break;
						if (peek.equals(","))
							break;
						if (priority(s) <= priority(peek)) {
							out.addLast(stack.pop());
							continue;
						}
						break;
					}
					stack.push(s);
				}
			}

			System.err.println(string);
			System.err.println(s);
			System.err.print("out");
			for (String i : out) {
				System.err.print(" " + i);
			}
			System.err.println();
			System.err.print("stack");
			for (String i : stack) {
				System.err.print(" " + i);
			}
			System.err.println();
			System.err.println();

			lastToken = token;
		}
		while (stack.size() > 0) {
			String pop = stack.pop();
			if (!pop.equals("(")) {
				out.addLast(pop);
			}
		}

		System.err.println(string);
		for (String i : out) {
			System.err.print(i + " ");
		}
		System.err.println();
		System.err.println();

		return out.toArray(new String[0]);
	}

	public static Token toToken(char c) {
		if (c == ' ')
			return Token.NULL;
		if ((c >= 'a') && (c <= 'z'))
			return Token.CHAR;
		if ((c >= 'A') && (c <= 'Z'))
			return Token.CHAR;
		if ((c >= '0') && (c <= '9'))
			return Token.NUMBER;
		if (c == '.')
			return Token.DOT;
		if ("+-*/(),".indexOf(c) != -1)
			return Token.OPERATOR;
		return null;
	}

	public static int priority(String s) {
		int ret = 0;
		if (s.equals("+"))
			return ret;
		if (s.equals("-"))
			return ret;
		++ret;
		if (s.equals("*"))
			return ret;
		if (s.equals("/"))
			return ret;
		++ret;
		if (s.equals("--"))
			return ret;
		++ret;
		if (s.equals("."))
			return ret;
		return -1;
	}

}
