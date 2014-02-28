private package jundokan.connection;
import java.sql.*;

import javax.swing.JOptionPane;
/*
public abstract class Config {
	public static Connection getConnection() { 
			try {
				Class.forName(Config.DRIVER);
				return DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException | ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				return null;
			} 	
		}
	static String USER = "root";
	static String PASSWORD = "634780";
	static String URL = "jdbc:mysql://localhost:3306/Jundokan";
	public static String DRIVER = "com.mysql.jdbc.Driver";
}*/
public abstract class Config {
	public static Connection getConnection() { 
			try {
				Class.forName("org.sqlite.JDBC");
				Connection connection = DriverManager.getConnection(URL);//, USER, PASSWORD);
				PreparedStatement prest = connection.prepareStatement("pragma foreign_keys = on;");
				prest.execute();
				return connection;
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
				return null;
			} 	
		}
	static String USER = "root";
	static String PASSWORD = "634780";
	static String URL = "jdbc:sqlite:Jundokan.sqlite3";
	public static String DRIVER = "org.sqlite.JDBC";
}
