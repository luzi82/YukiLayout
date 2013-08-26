package com.luzi82.yukilayout.gdx.demo;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.gdx.YlgPlatformAbstractLayer;
import com.luzi82.yukilayout.layout.YlLayout;

public class AndroidPal implements YlgPlatformAbstractLayer {

	public TextBlock createText(String text, int align, float fontSize,
			YlColor color) {

		int fontSizePx = getMaxFontSizePt(fontSize);
		int[] limit = getFontLimit(fontSizePx);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(fontSizePx);
		paint.setTypeface(Typeface.DEFAULT);
		paint.setHinting(Paint.HINTING_OFF);
		paint.setColor(Color.argb(color.alphaI, color.redI, color.greenI,
				color.blueI));
		paint.setStyle(Paint.Style.FILL);

		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);

		int x0int = Math.min(0, (int) Math.floor(rect.left));
		int x1int = Math.max(0, (int) Math.ceil(rect.right));

		int txtW = x1int - x0int;
		int txtH = limit[1] - limit[0];

		int imgW = 1;
		while (imgW < txtW) {
			imgW <<= 1;
		}
		int imgH = 1;
		while (imgH < txtH) {
			imgH <<= 1;
		}

		Bitmap bmp = Bitmap.createBitmap(imgW, imgH, Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		canvas.drawText(text, -x0int, -limit[0], paint);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 0, baos);
		byte[] buf = baos.toByteArray();

		TextBlock textBlock = new TextBlock();

		Pixmap p = new Pixmap(buf, 0, buf.length);
		Texture texture = new Texture(p);
		textBlock.mTextureRegion = new TextureRegion(texture, 0, txtH, txtW,
				-txtH);

		switch ((align - 1) % 3) {
		case 0: {
			textBlock.mOffsetX = x0int;
			break;
		}
		case 1: {
			textBlock.mOffsetX = -txtW / 2;
			break;
		}
		case 2: {
			textBlock.mOffsetX = -txtW;
			break;
		}
		}

		switch ((align - 1) / 3) {
		case 0: {
			textBlock.mOffsetY = -txtH;
			break;
		}
		case 1: {
			textBlock.mOffsetY = -(txtH / 2);
			break;
		}
		case 2: {
			textBlock.mOffsetY = 0;
			break;
		}
		}

		return textBlock;
	}

	public static int getMaxFontSizePt(float fontSize) {
		// find font size in px
		int fontSizePx = 1; // out

		while (true) {
			int[] limit = getFontLimit(fontSizePx);

			if ((limit[1] - limit[0]) > fontSize) {
				return fontSizePx - 1;
			}
			++fontSizePx;
		}
	}

	public static int[] getFontLimit(float fontSizePx) {
		int top = 0; // negative
		int bottom = 0; // positive

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(fontSizePx);
		paint.setTypeface(Typeface.DEFAULT);
		paint.setHinting(Paint.HINTING_OFF);
		paint.setStyle(Paint.Style.FILL);

		FontMetrics fm = paint.getFontMetrics();

		top = Math.min(top, (int) Math.floor(fm.top));
		top = Math.min(top, (int) Math.floor(fm.ascent));
		bottom = Math.max(bottom, (int) Math.ceil(fm.bottom));
		bottom = Math.max(bottom, (int) Math.ceil(fm.descent));

		return new int[] { top, bottom };
	}

	public InputProcessor createInputProcessor(YlLayout aLayout) {
		return null;
	}

}
