package com.luzi82.yukilayout.gdx.demo;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.badlogic.gdx.Game;
import com.luzi82.yukilayout.gdx.YlgScreen;

public class YukiLayoutGdxDemo extends Game {

	@Override
	public void create() {
		try {
//			setScreen(new YlgScreen("data/clear.xml"));
			setScreen(new YlgScreen("data/milestone0.xml"));
		} catch (ParserConfigurationException e) {
			throw new Error(e);
		} catch (SAXException e) {
			throw new Error(e);
		} catch (IOException e) {
			throw new Error(e);
		}
	}

}
