package com.luzi82.yukilayout.gdx;

import com.badlogic.gdx.Gdx;
import com.luzi82.yukilayout.layout.YlPlatformAbstractLayer;

public class YlPal implements YlPlatformAbstractLayer {

	@Override
	public float pixelPerInch() {
		return Gdx.graphics.getPpiX();
	}

	@Override
	public float pixelPerMeter() {
		return Gdx.graphics.getPpcX();
	}

}
