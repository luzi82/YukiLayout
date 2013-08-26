package com.luzi82.yukilayout.gdx.desktop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.gdx.YlgPlatformAbstractLayer;
import com.luzi82.yukilayout.layout.YlLayout;

public class DesktopPal implements YlgPlatformAbstractLayer {

	static final String TEST_STRING;
	static {
		StringBuffer testStringBuf = new StringBuffer();
		for (int i = 32; i < 127; ++i) {
			testStringBuf.append((char) i);
		}
		TEST_STRING = testStringBuf.toString();
	}

	@Override
	public TextBlock createText(String text, int align, float fontSize,
			YlColor color) {
		// System.err.printf("createText text %s, fontSize %f\n", text,
		// fontSize);

		FontRenderContext frc = createFontRenderContext();

		int fontSizePt = getMaxFontSizePt(fontSize, frc);
		// System.err.printf("fontSizePt %d\n", fontSizePt);
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, fontSizePt);
		int[] limit = getFontLimit(font, frc);

		Rectangle2D rect = font.getStringBounds(text, frc);
		int x0int = Math.min(0, (int) Math.floor(rect.getMinX()));
		int x1int = Math.max(0, (int) Math.ceil(rect.getMaxX()));

		int txtW = x1int - x0int;
		int txtH = limit[1] - limit[0];

		// System.err.printf("createText txtW %d, txtH %d\n", txtW, txtH);

		int imgW = 1;
		while (imgW < txtW) {
			imgW <<= 1;
		}
		int imgH = 1;
		while (imgH < txtH) {
			imgH <<= 1;
		}

		frc = null;

		BufferedImage img = new BufferedImage(imgW, imgH,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(font);
		g2d.setBackground(new Color(0, 0, 0, 0));
		// g2d.setBackground(Color.RED);
		g2d.setColor(new Color(color.redI, color.greenI, color.blueI,
				color.alphaI));
		g2d.clearRect(0, 0, imgW, imgH);
		g2d.drawString(text, -x0int, -limit[0]);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", baos);
		} catch (IOException e) {
			throw new Error(e);
		}

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

	public static int getMaxFontSizePt(float fontSize, FontRenderContext frc) {
		// find font size in pt
		int fontSizePt = 1; // out

		while (true) {
			Font tmpFont = new Font(Font.SANS_SERIF, Font.PLAIN, fontSizePt);

			int[] limit = getFontLimit(tmpFont, frc);

			if ((limit[1] - limit[0]) > fontSize) {
				return fontSizePt - 1;
			}
			++fontSizePt;
		}
	}

	public static int[] getFontLimit(Font font, FontRenderContext frc) {
		int top = 0; // negative
		int bottom = 0; // positive

		// System.err.printf("tmpFont.hasUniformLineMetrics() %s\n",
		// font.hasUniformLineMetrics());
		// if (font.hasUniformLineMetrics()) {
		LineMetrics metrics = font.getLineMetrics(TEST_STRING, frc);
		top = Math.min(top, (int) Math.floor(metrics.getAscent()));
		bottom = Math.max(bottom, (int) Math.ceil(metrics.getDescent()));
		// }

		Rectangle2D rect = font.getStringBounds(TEST_STRING, frc);
		top = Math.min(top, (int) Math.floor(rect.getMinY()));
		bottom = Math.max(bottom, (int) Math.ceil(rect.getMaxY()));

		// System.err.printf("size %d, top %d, bottom %d\n", font.getSize(),
		// top,
		// bottom);

		return new int[] { top, bottom };
	}

	public static FontRenderContext createFontRenderContext() {
		BufferedImage tmpImg = new BufferedImage(1, 1,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D tmpG2d = tmpImg.createGraphics();
		tmpG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		return tmpG2d.getFontRenderContext();
	}

	@Override
	public InputProcessor createInputProcessor(YlLayout aLayout) {
		return new YlgdLayoutInputProcessor(aLayout);
	}
}
