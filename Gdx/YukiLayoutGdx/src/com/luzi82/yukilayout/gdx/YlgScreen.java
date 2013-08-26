package com.luzi82.yukilayout.gdx;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.gdx.YlgPlatformAbstractLayer.TextBlock;
import com.luzi82.yukilayout.layout.YlLayout;

public class YlgScreen implements Screen, YlGraphics {

	boolean mAllocDone = false;
	YlgPlatformAbstractLayer iPal;

	YlLayout layout;
	InputProcessor mInputProcessor;

	GL20 gl;
	SpriteBatch spriteBatch;

	HashMap<String, Texture> mImgMap;
	HashMap<String, TextBlock> mTextMap;

	public YlgScreen(String string, Object arg, YlgPlatformAbstractLayer aPal)
			throws ParserConfigurationException, SAXException, IOException {
		this.layout = new YlLayout(Gdx.files.internal(string).read(), arg);
		// this.layout.setRootSize(Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight());
		iPal = aPal;
	}

	// public void setLayoutArg(String key, Object value) {
	// this.layout.setArg(key, value);
	// }

	@Override
	public void dispose() {
		free();
	}

	@Override
	public void hide() {
		free();
	}

	@Override
	public void pause() {
		free();
	}

	@Override
	public void render(float arg0) {
		stateStack.clear();
		currentState = new YlgState();
		stateStack.push(currentState);

		spriteBatch.begin();
		this.layout.paint(this);
		spriteBatch.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// this.layout.setRootSize(arg0, arg1);
		this.layout.mRoot.setAttrVal("width", arg0);
		this.layout.mRoot.setAttrVal("height", arg1);
		if (mAllocDone) {
			free();
			alloc();
		}
	}

	@Override
	public void resume() {
		alloc();
	}

	@Override
	public void show() {
		alloc();
	}

	@Override
	public void clear(YlColor c) {
		gl.glClearColor(c.redF, c.greenF, c.blueF, c.alphaF);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	LinkedList<YlgState> stateStack = new LinkedList<YlgState>();
	YlgState currentState;

	@Override
	public void push() {
		currentState = new YlgState(currentState);
		stateStack.push(currentState);
	}

	@Override
	public void pop() {
		stateStack.pop();
		currentState = stateStack.getFirst();
	}

	@Override
	public void translate(float xf, float yf) {
		currentState.transform.translate(xf, yf, 0);
		transformDone = false;
	}

	boolean transformDone = false;

	private void updateTransform() {
		if (transformDone)
			return;
		spriteBatch.setTransformMatrix(currentState.transform);
		transformDone = true;
	}

	@Override
	public void text(String text, float xf, float yf, int align,
			float fontSize, YlColor color) {
		updateTransform();
		String key = text + ":" + align + ":" + fontSize + ":" + color.argb;
		TextBlock tb = mTextMap.get(key);
		if (tb == null) {
			System.err.println(String.format("TextBlock not found: %s", key));
			return;
		}
		spriteBatch.draw(tb.mTextureRegion, xf + tb.mOffsetX, yf + tb.mOffsetY);
	}

	@Override
	public void img(String src, float x0f, float y0f, float x1f, float y1f,
			float u0, float v0, float u1, float v1) {
		updateTransform();
		Texture t = mImgMap.get(src);
		if (t == null) {
			System.err.println(String.format("img not found: %s", src));
			return;
		}
		spriteBatch.draw(t, x0f, y0f, x1f - x0f, y1f - y0f, u0 / t.getWidth(),
				v0 / t.getHeight(), u1 / t.getWidth(), v1 / t.getHeight());
	}

	public void alloc() {
		if (mAllocDone)
			return;
		this.mAllocDone = true;

		Graphics g = Gdx.graphics;

		this.gl = Gdx.graphics.getGL20();
		this.gl.glDisable(GL20.GL_DEPTH_TEST);

		this.spriteBatch = new SpriteBatch();
		this.spriteBatch.setProjectionMatrix(new Matrix4().setToOrtho(0,
				g.getWidth(), g.getHeight(), 0, 1, -1));

		this.mImgMap = new HashMap<String, Texture>();
		this.mTextMap = new HashMap<String, TextBlock>();

		// Ele[] eleV = layout.getElementArray();
		// for (Ele ele : eleV) {
		// if (ele instanceof Img) {
		// Img img = (Img) ele;
		// // TODO should check dynamic
		// String src = img.src.string();
		// if (!mImgMap.containsKey(src)) {
		// mImgMap.put(src, new Texture(Gdx.files.internal(src)));
		// }
		// } else if (ele instanceof Text) {
		// // Text text = (Text) ele;
		// // // TODO should check dynamic
		// // String txt = text.text.string();
		// }
		// }
		this.mInputProcessor = iPal.createInputProcessor(this.layout);
		Gdx.input.setInputProcessor(mInputProcessor);

		this.layout.paint(new YlgPrepare());
	}

	public void free() {
		if (!mAllocDone)
			return;
		this.mAllocDone = false;

		this.gl = null;
		this.spriteBatch.dispose();
		this.spriteBatch = null;

		for (Texture t : mImgMap.values()) {
			t.dispose();
		}
		mImgMap = null;

		for (TextBlock t : mTextMap.values()) {
			t.mTextureRegion.getTexture().dispose();
		}
		mTextMap = null;

		mInputProcessor = null;
	}

	class YlgPrepare implements YlGraphics {

		@Override
		public void clear(YlColor c) {
		}

		@Override
		public void push() {
		}

		@Override
		public void pop() {
		}

		@Override
		public void translate(float xf, float yf) {
		}

		@Override
		public void text(String text, float xf, float yf, int align,
				float fontSize, YlColor color) {
			String key = text + ":" + align + ":" + fontSize + ":" + color.argb;
			mTextMap.put(key, iPal.createText(text, align, fontSize, color));
		}

		@Override
		public void img(String src, float x0f, float y0f, float x1f, float y1f,
				float u0, float v0, float u1, float v1) {
			mImgMap.put(src, new Texture(Gdx.files.internal(src)));
		}

	}

}
