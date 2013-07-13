package com.luzi82.yukilayout.unittest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.luzi82.yukilayout.YlLayout;

public class MileStone1Test {

	@Test
	public void milestone1() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/milestone1.xml"));
		layout.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();
		YlGraphicsRecorder.Record record;

		int i = 0;
		YlGraphicsRecorder.Img img;

		record = recordAry[i++];
		Assert.assertTrue(record.getClass().getName(),
				record instanceof YlGraphicsRecorder.Clear);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Img);
		img = (YlGraphicsRecorder.Img) record;
		Assert.assertEquals(0f, img.x0f);
		Assert.assertEquals(800f, img.x1f, 0.00001);
		Assert.assertEquals(0f, img.y0f);
		Assert.assertEquals(600f, img.y1f, 0.00001);

		Assert.assertEquals(i, recordAry.length);
	}

}
