package com.luzi82.yukilayout.gdx;

import com.badlogic.gdx.math.Matrix4;

public class YlgState {
	public Matrix4 transform;

	public YlgState() {
		this.transform = new Matrix4();
	}

	public YlgState(YlgState state) {
		this.transform = state.transform.cpy();
	}
}
