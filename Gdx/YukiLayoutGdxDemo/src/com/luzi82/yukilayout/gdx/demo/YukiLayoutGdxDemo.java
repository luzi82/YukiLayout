package com.luzi82.yukilayout.gdx.demo;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.badlogic.gdx.Game;
import com.luzi82.yukilayout.gdx.YlgPlatformAbstractLayer;
import com.luzi82.yukilayout.gdx.YlgScreen;

public class YukiLayoutGdxDemo extends Game {

	public class U {
		public String name;

		public U(String n) {
			this.name = n;
		}
	}

	YlgPlatformAbstractLayer iPal;

	public YukiLayoutGdxDemo(YlgPlatformAbstractLayer aPal) {
		iPal = aPal;
	}

	@Override
	public void create() {
		try {
			// setScreen(new YlgScreen("data/clear.xml"));
			// setScreen(new YlgScreen("data/img.xml"));
			U[] uu = { new U("a"), new U("b"), new U("c"), new U("d"),
					new U("e"), };
			YlgScreen screen = new YlgScreen("data/milestone0.xml", iPal);
			screen.setLayoutArg("itemlist", uu);
			setScreen(screen);
		} catch (ParserConfigurationException e) {
			throw new Error(e);
		} catch (SAXException e) {
			throw new Error(e);
		} catch (IOException e) {
			throw new Error(e);
		}
	}

}
