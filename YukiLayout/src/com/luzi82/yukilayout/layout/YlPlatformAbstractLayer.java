package com.luzi82.yukilayout.layout;

public interface YlPlatformAbstractLayer {

	public float pixelPerInch();

	public float pixelPerMeter();

	static public class Default implements YlPlatformAbstractLayer {

		private static final float PPI = 120;

		@Override
		public float pixelPerInch() {
			return PPI;
		}

		@Override
		public float pixelPerMeter() {
			return PPI * 39.3700787f;
		}

	}

}
