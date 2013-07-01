package com.luzi82.yukilayout.unittest;

import java.text.ParseException;

import junit.framework.Assert;

import org.junit.Test;

import com.luzi82.yukilayout.YlExp;

public class YlExpTest {

	@Test
	public void exp() throws ParseException {
		String[] exp;
		int i;

		exp = YlExp.parse("1+2");
		i = 0;
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("a+b");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("1-2");
		i = 0;
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("-", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("1*2");
		i = 0;
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("*", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("1/2");
		i = 0;
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("/", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("1+2*3");
		i = 0;
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("3", exp[i++]);
		Assert.assertEquals("*", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("1*2+3");
		i = 0;
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("*", exp[i++]);
		Assert.assertEquals("3", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("1*(2+3)");
		i = 0;
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("3", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals("*", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("-a");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("--", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("1*-2");
		i = 0;
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("--", exp[i++]);
		Assert.assertEquals("*", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("-a+b");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("--", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("a");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("abc");
		i = 0;
		Assert.assertEquals("abc", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("(a)");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("-(a)");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("--", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("(-a)");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("--", exp[i++]);
		Assert.assertEquals(i, exp.length);
	}

	@Test
	public void function() throws ParseException {
		String[] exp;
		int i;

		exp = YlExp.parse("f(a)");
		i = 0;
		Assert.assertEquals("f", exp[i++]);
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("f(a,b)");
		i = 0;
		Assert.assertEquals("f", exp[i++]);
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("f(a,b,c)");
		i = 0;
		Assert.assertEquals("f", exp[i++]);
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("c", exp[i++]);
		Assert.assertEquals("3", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("f(a)+g(b)");
		i = 0;
		Assert.assertEquals("f", exp[i++]);
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals("g", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("f(a+b)");
		i = 0;
		Assert.assertEquals("f", exp[i++]);
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("f(g(a))");
		i = 0;
		Assert.assertEquals("f", exp[i++]);
		Assert.assertEquals("g", exp[i++]);
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("f(g(a),h(b))");
		i = 0;
		Assert.assertEquals("f", exp[i++]);
		Assert.assertEquals("g", exp[i++]);
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals("h", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("f(g(a,b))");
		i = 0;
		Assert.assertEquals("f", exp[i++]);
		Assert.assertEquals("g", exp[i++]);
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("2", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals(i, exp.length);
	}

	@Test
	public void member() throws ParseException {
		String[] exp;
		int i;

		exp = YlExp.parse("a.b");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals(".", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("a.b.c");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals(".", exp[i++]);
		Assert.assertEquals("c", exp[i++]);
		Assert.assertEquals(".", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("1.3");
		i = 0;
		Assert.assertEquals("1.3", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("f(a).b");
		i = 0;
		Assert.assertEquals("f", exp[i++]);
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("1", exp[i++]);
		Assert.assertEquals("@", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals(".", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("(a+b).c");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals("c", exp[i++]);
		Assert.assertEquals(".", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("a.b+c");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals(".", exp[i++]);
		Assert.assertEquals("c", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("a+b.c");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("c", exp[i++]);
		Assert.assertEquals(".", exp[i++]);
		Assert.assertEquals("+", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("a.b*c");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals(".", exp[i++]);
		Assert.assertEquals("c", exp[i++]);
		Assert.assertEquals("*", exp[i++]);
		Assert.assertEquals(i, exp.length);

		exp = YlExp.parse("a*b.c");
		i = 0;
		Assert.assertEquals("a", exp[i++]);
		Assert.assertEquals("b", exp[i++]);
		Assert.assertEquals("c", exp[i++]);
		Assert.assertEquals(".", exp[i++]);
		Assert.assertEquals("*", exp[i++]);
		Assert.assertEquals(i, exp.length);
	}
	
	@Test
	public void hex() throws ParseException {
		String[] exp;
		int i;

		exp = YlExp.parse("0x1000");
		i = 0;
		Assert.assertEquals("0x1000", exp[i++]);
		Assert.assertEquals(i, exp.length);
	}


}
