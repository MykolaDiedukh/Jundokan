package jundokan.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;

public class DimensionFrame {
	public DimensionFrame(String className, int default_width, int default_height) {
		this.className = className;
		String userDir = System.getProperty("user.home");
		File propertiesDir = new File(userDir, ".jundokan");
		if (!propertiesDir.exists())
			propertiesDir.mkdir();

		propertiesFile = new File(propertiesDir, className + ".properties");

		Properties defaultSettings = new Properties();
		defaultSettings.put("left", "0");
		defaultSettings.put("top", "0");
		defaultSettings.put("width", "" + default_width);
		defaultSettings.put("height", "" + default_height);
		setting = new Properties(defaultSettings);

		if (propertiesFile.exists())
			try {
				FileInputStream in = new FileInputStream(propertiesFile);
				setting.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		left = Integer.parseInt(setting.getProperty("left"));
		top = Integer.parseInt(setting.getProperty("top"));
		width = Integer.parseInt(setting.getProperty("width"));
		height = Integer.parseInt(setting.getProperty("height"));
	}

	public Rectangle getBounds() {
		return new Rectangle(left, top, width, height);
	}

	public WindowAdapter getWindowAdapter(final JFrame frame) {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setting.put("left", "" + frame.getX());
				setting.put("top", "" + frame.getY());
				setting.put("width", "" + frame.getWidth());
				setting.put("height", "" + frame.getHeight());
				try {
					FileOutputStream out = new FileOutputStream(propertiesFile);
					setting.store(out, className + ".properties");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		};
	}
	
	public WindowAdapter getWindowAdapter(final JDialog dialog) {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setting.put("left", "" + dialog.getX());
				setting.put("top", "" + dialog.getY());
				setting.put("width", "" + dialog.getWidth());
				setting.put("height", "" + dialog.getHeight());
				try {
					FileOutputStream out = new FileOutputStream(propertiesFile);
					setting.store(out, className + ".properties");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				dialog.dispose();
			}
		};
	}
	
	private int left;
	private int top;
	private int width;
	private int height;
	private File propertiesFile;
	private Properties setting;
	private String className;
}