package com.luzi82.yukilayout.gdx.desktop;

import com.badlogic.gdx.InputProcessor;
import com.luzi82.yukilayout.layout.YlElement;
import com.luzi82.yukilayout.layout.YlLayout;
import com.luzi82.yukilayout.layout.YlScrollElement;

public class YlgdLayoutInputProcessor implements InputProcessor {

	public YlLayout pLayout;

	int lastCursorX = -1;
	int lastCursorY = -1;

	public YlgdLayoutInputProcessor(YlLayout aLayout) {
		this.pLayout = aLayout;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		lastCursorX = x;
		lastCursorY = y;
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		lastCursorX = x;
		lastCursorY = y;
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		lastCursorX = x;
		lastCursorY = y;
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		lastCursorX = x;
		lastCursorY = y;
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		System.err.println("scrolled");
		if (lastCursorX == -1)
			return false;
		if (lastCursorY == -1)
			return false;
		YlLayout.ShootResult shootResult = pLayout.shoot(lastCursorX,
				lastCursorY);
		for (YlLayout.ShootElement shootElement : shootResult.elementList) {
			YlElement element = shootElement.element;
			System.err.println("Scrolled " + element.getClass().getName());
			if (element instanceof YlScrollElement) {
				float offsetY = YlElement.toFloat(element.attr("offsetY"));
				offsetY += amount;
				offsetY = (float) Math.floor(offsetY);
				element.setAttrVal("offsetY", offsetY);
			}
		}
		return false;
	}

}
