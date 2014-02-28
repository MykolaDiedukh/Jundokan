package jundokan.view;

import java.awt.EventQueue;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import jundokan.connection.Degrees;
import jundokan.model.DBDegrees;

public class GUIDegree extends Template<DBDegrees>{
	
	private ResourceBundle bundle;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDegree frame = new GUIDegree();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public GUIDegree() {
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("GUIDegree", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle(bundle.getString("title"));
		message = bundle.getString("message");
		DimensionFrame dim = new DimensionFrame(getClass().getSimpleName(), 358, 300);
        addWindowListener(dim.getWindowAdapter(this));
        setBounds(dim.getBounds());
	}
	
	protected void update() {
		int id = table.getSelectedRow();
		DBDegrees degree =  typeGames.get(id);
		degree.Degree = (String)table.getValueAt(id, 0);
		new Degrees().update(degree);
	}
	
	protected void insertRow(){
		int id = table.getSelectedRow();
		DBDegrees degree = new DBDegrees();
		degree.DegreeID = new Degrees().insert((String)table.getValueAt(id, 0));
		degree.Degree = (String)table.getValueAt(id, 0);
		typeGames.add(degree);
	}
	
	protected void deleteRow() {
		int id = table.getSelectedRow();
		int idd = typeGames.get(id).DegreeID;
		if (new Degrees().delete(idd)) {
			typeGames.remove(id);
			((DefaultTableModel)table.getModel()).removeRow(id);
		} else JOptionPane.showMessageDialog(null, bundle.getString("mesage2"));
	}

	protected void selectRow() {
		DefaultTableModel mod = (DefaultTableModel) table.getModel();
		typeGames = new Degrees().select();
		for ( DBDegrees degree : typeGames) {
			mod.addRow(new Object[] { degree.Degree });
		}
	}
}
