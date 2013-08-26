package com.luzi82.yukilayout.unittest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.luzi82.yukilayout.gdx.desktop.YlgdLayoutInputProcessor;
import com.luzi82.yukilayout.layout.YlLayout;

public class MileStone2Test {

	public static class UU {
		public U[] itemlist = { new U("a"), new U("b"), new U("c"), new U("d"),
				new U("e"), };
	}

	public static class U {
		public String name;

		public U(String n) {
			this.name = n;
		}
	}

	@Test
	public void milestone2() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/milestone2.xml"), new UU());

		YlGraphicsRecorder graphicsRecorder;
		YlGraphicsRecorder.Record[] recordAry;
		YlGraphicsRecorder.Record record;

		int i;
		YlGraphicsRecorder.Clear clear;
		YlGraphicsRecorder.Translate translate;

		graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);
		recordAry = graphicsRecorder.getRecordAry();
		i = 0;

		record = recordAry[i++];
		Assert.assertTrue(record.getClass().getName(),
				record instanceof YlGraphicsRecorder.Clear);
		clear = (YlGraphicsRecorder.Clear) record;
		Assert.assertEquals(0xff7f7f7f, clear.color.argb);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(0f, translate.x);
		Assert.assertEquals(0f, translate.y);

		YlgdLayoutInputProcessor ip = new YlgdLayoutInputProcessor(layout);
		ip.touchMoved(400, 40);
		ip.scrolled(10);

		graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);
		recordAry = graphicsRecorder.getRecordAry();
		i = 0;

		record = recordAry[i++];
		Assert.assertTrue(record.getClass().getName(),
				record instanceof YlGraphicsRecorder.Clear);
		clear = (YlGraphicsRecorder.Clear) record;
		Assert.assertEquals(0xff7f7f7f, clear.color.argb);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(0f, translate.x);
		Assert.assertEquals(10f, translate.y);

	}

}
