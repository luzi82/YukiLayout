package com.luzi82.yukilayout.layout;


public abstract class YlVal {

	public abstract Object val();

//	public YlColor color() {
//		Object v = val();
//		if (v == null) {
//			return null;
//		} else {
//			return new YlColor(v);
//		}
//	}
//
//	public Float floatt() {
//		Object v = val();
//		if (v == null) {
//			return null;
//		} else if (v instanceof Float) {
//			return (Float) v;
//		} else {
//			return Float.valueOf(v.toString());
//		}
//	}
//
//	public Integer intt() {
//		Object v = val();
//		if (v == null) {
//			return null;
//		} else if (v instanceof Integer) {
//			return (Integer) v;
//		} else {
//			return Float.valueOf(v.toString()).intValue();
//		}
//	}
//
//	public List<?> list() {
//		Object v = val();
//		if (v == null) {
//			return null;
//		} else {
//			return YlLayout.toList(v);
//		}
//	}
//
//	public String string() {
//		Object v = val();
//		if (v == null) {
//			return null;
//		} else {
//			return v.toString();
//		}
//	}
}