package com.luzi82.yukilayout.gdx;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.layout.YlLayout;

public interface YlgPlatformAbstractLayer {
	
	public class TextBlock{
		public TextureRegion mTextureRegion;
		public float mOffsetX;
		public float mOffsetY;
	}

	public TextBlock createText(String text, int align, float fontSize, YlColor color);
	
	public InputProcessor createInputProcessor(YlLayout aLayout);
	
}
