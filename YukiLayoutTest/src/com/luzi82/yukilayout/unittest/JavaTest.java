package com.luzi82.yukilayout.unittest;

import junit.framework.Assert;

import org.junit.Test;

public class JavaTest {

	private class X {
		public int x = 0;

		public X() {
			x = 1;
		}
	}

	private class Y extends X {
		public int y = x;

		public Y() {
		}
	}

	@Test
	public void runOrder() {
		X x = new X();
		Assert.assertEquals(1, x.x);

		Y y = new Y();
		Assert.assertEquals(1, y.y);
	}

}
