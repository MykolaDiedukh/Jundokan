package jundokan.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Property {
	private Properties properties;
	public Property(String path) {
			try {
				this.properties = new Properties();
				properties.load(new FileInputStream(path));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		
	}
	public String getKey(String key) {
		return this.properties.getProperty(key);
	}
}
