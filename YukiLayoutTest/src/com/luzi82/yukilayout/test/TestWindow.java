package com.luzi82.yukilayout.test;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestWindow extends JFrame {

	public TestWindow(String string) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setVisible(true);

		setContentPane(panel);
		pack();

		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] argv) {
		TestWindow win = new TestWindow("res/test0.xml");
		win.setVisible(true);
	}

}
