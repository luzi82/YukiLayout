package com.luzi82.yukilayout.unittest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.luzi82.yukilayout.layout.YlBoxElement;
import com.luzi82.yukilayout.layout.YlElement;
import com.luzi82.yukilayout.layout.YlLayout;
import com.luzi82.yukilayout.layout.YlScreenElement;
import com.luzi82.yukilayout.layout.YlTextElement;

public class MileStone1Test {

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
	public void box() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/box.xml"), null);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;
		YlGraphicsRecorder.Record record;
		YlGraphicsRecorder.Translate translate;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		while (i < recordAry.length) {
			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
			translate = (YlGraphicsRecorder.Translate) record;
			Assert.assertEquals(translate.y, translate.x, 0.000001);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);
		}

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void rect() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/rect.xml"), null);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;
		YlGraphicsRecorder.Record record;
		YlGraphicsRecorder.Translate translate;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		while (i < recordAry.length) {
			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
			translate = (YlGraphicsRecorder.Translate) record;
			Assert.assertEquals(translate.y, translate.x, 0.000001);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);
		}

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void milestone1() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/milestone1.xml"), new UU());

		YlElement[] shotElementList = null;
		YlElement shotElement;
		int shotElementIdx;

		YlTextElement textElement;
		// YlBoxElement boxElement;

		shotElementList = layout.shoot(400, 40);
		shotElementIdx = 0;
		shotElement = shotElementList[shotElementIdx++];
		textElement = (YlTextElement) shotElement;
		Assert.assertEquals("a", textElement.attr("text"));
		shotElement = shotElementList[shotElementIdx++];
		Assert.assertTrue(shotElement instanceof YlScreenElement);
		Assert.assertEquals(shotElementIdx, shotElementList.length);
	}

}
