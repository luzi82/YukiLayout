package com.luzi82.yukilayout;

public class YlFunc {

	public static Object floor(Object aV) {
		Number n = toNumber(aV);
		return Math.floor(n.doubleValue());
	}

	public static Object round(Object aV) {
		Number n = toNumber(aV);
		return Math.round(n.doubleValue());
	}

	public static Object ceil(Object aV) {
		Number n = toNumber(aV);
		return Math.ceil(n.doubleValue());
	}

	public static Object min(Object aA, Object aB) {
		Number a = toNumber(aA);
		Number b = toNumber(aB);
		return Math.min(a.doubleValue(), b.doubleValue());
	}

	public static Object div(Object aA, Object aB) {
		Number a = toNumber(aA);
		Number b = toNumber(aB);
		return a.doubleValue() / b.doubleValue();
	}

	public static Object mul(Object aA, Object aB) {
		Number a = toNumber(aA);
		Number b = toNumber(aB);
		return a.doubleValue() * b.doubleValue();
	}

	public static Object sub(Object aA, Object aB) {
		Number a = toNumber(aA);
		Number b = toNumber(aB);
		return a.doubleValue() - b.doubleValue();
	}

	public static Object add(Object aA, Object aB) {
		Number a = toNumber(aA);
		Number b = toNumber(aB);
		return a.doubleValue() + b.doubleValue();
	}

	public static Object neg(Object aA) {
		Number a = toNumber(aA);
		return -a.doubleValue();
	}

	public static Object max(Object aA, Object aB) {
		Number a = toNumber(aA);
		Number b = toNumber(aB);
		return Math.max(a.doubleValue(), b.doubleValue());
	}

	private static Number toNumber(Object a) {
		if (a instanceof Number) {
			return (Number) a;
		}
		if (a instanceof String) {
			String s = (String) a;
			if (s.contains(".")) {
				return Float.parseFloat(s);
			} else {
				return Integer.parseInt(s);
			}
		}
		throw new IllegalArgumentException();
	}

}
