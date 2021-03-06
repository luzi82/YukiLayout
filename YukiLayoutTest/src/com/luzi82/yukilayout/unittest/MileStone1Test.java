package com.luzi82.yukilayout.unittest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.luzi82.yukilayout.layout.YlBoxElement;
import com.luzi82.yukilayout.layout.YlLayout;
import com.luzi82.yukilayout.layout.YlLayout.ShootElement;
import com.luzi82.yukilayout.layout.YlLayout.ShootResult;

public class MileStone1Test {

	@Test
	public void box() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/box.xml"), null,null);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;
		YlGraphicsRecorder.Record record;
		YlGraphicsRecorder.Translate translate;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(0, translate.x, 0.000001);
		Assert.assertEquals(2, translate.y, 0.000001);
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
		YlLayout layout = new YlLayout(new File("res/rect.xml"), null,null);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;
		YlGraphicsRecorder.Record record;
		YlGraphicsRecorder.Translate translate;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);
		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
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
	public void milestone1() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/milestone1.xml"), new UU(),null);

		ShootResult shotResult;
		ShootElement shootElement;

		shotResult = layout.shoot(400, 40);
		Assert.assertEquals(400f, shotResult.x);
		Assert.assertEquals(40f, shotResult.y);

		Iterator<ShootElement> shootElementItr = shotResult.elementList
				.iterator();

		shootElement = shootElementItr.next();
		Assert.assertTrue(shootElement.element instanceof YlBoxElement);
		Assert.assertEquals(370f, shootElement.x);
		Assert.assertEquals(10f, shootElement.y);

		if (shootElementItr.hasNext()) {
			shootElement = shootElementItr.next();
			System.err.println(shootElement.element.getClass().getSimpleName());
			Assert.fail("shootElementItr.hasNext()");
		}
	}

	@Test
	public void milestone1a() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/milestone1a.xml"), new UU(),null);

		ShootResult shotResult;
		ShootElement shootElement;

		shotResult = layout.shoot(400, 40);
		Assert.assertEquals(400f, shotResult.x);
		Assert.assertEquals(40f, shotResult.y);

		Iterator<ShootElement> shootElementItr = shotResult.elementList
				.iterator();

		// shootElement = shootElementItr.next();
		// Assert.assertTrue(shootElement.element instanceof YlBoxElement);
		// Assert.assertEquals(370f, shootElement.x);
		// Assert.assertEquals(10f, shootElement.y);

		if (shootElementItr.hasNext()) {
			shootElement = shootElementItr.next();
			System.err.println(shootElement.element.getClass().getSimpleName());
			Assert.fail("shootElementItr.hasNext()");
		}
	}

}
