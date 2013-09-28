package com.luzi82.yukilayout.unittest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.luzi82.yukilayout.layout.YlLayout;
import com.luzi82.yukilayout.layout.YlPlatformAbstractLayer;

/**
 * Aim: support real world physical unit
 * 
 * @author luzi82
 */
public class MileStone3Test {

	@Test
	public void milestone3() throws ParserConfigurationException, SAXException, IOException{
		YlLayout layout = new YlLayout(new File("res/milestone3.xml"), null,new YlPlatformAbstractLayer() {
			@Override
			public float pixelPerMeter() {
				return 4000;
			}
			@Override
			public float pixelPerInch() {
				return 200;
			}
		});

		YlGraphicsRecorder graphicsRecorder;
		YlGraphicsRecorder.Record[] recordAry;
		YlGraphicsRecorder.Record record;

		int i;
		YlGraphicsRecorder.Translate translate;

		graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);
		recordAry = graphicsRecorder.getRecordAry();
		i = 0;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record.getClass().getName(),
				record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(40f, translate.x);
		Assert.assertEquals(400f, translate.y);
	}
	
}
