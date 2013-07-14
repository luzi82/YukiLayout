package com.luzi82.yukilayout.gdx.demo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "YukiLayoutGdxDemo";
		cfg.useGL20 = true;
		cfg.width = 640;
		cfg.height = 360;

		new LwjglApplication(new YukiLayoutGdxDemo(new DesktopPal()), cfg);
	}
}
