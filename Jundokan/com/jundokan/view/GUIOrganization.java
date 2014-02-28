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

import javax.swing.table.DefaultTableModel;

import jundokan.connection.Organization;
import jundokan.model.DBOrganization;

public class GUIOrganization extends Template<DBOrganization> {
	
	private ResourceBundle bundle;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIOrganization frame = new GUIOrganization();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public GUIOrganization() {
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("GUIOrganization", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
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
		DBOrganization org =  typeGames.get(id);
		org.OrgName = (String)table.getValueAt(id, 0);
		new Organization().update(org);
	}
	
	protected void insertRow(){
		int id = table.getSelectedRow();
		DBOrganization org = new DBOrganization();
		org.OrgID = new Organization().insert((String)table.getValueAt(id, 0));
		org.OrgName = (String)table.getValueAt(id, 0);
		typeGames.add(org);
	}
	
	protected void deleteRow() {
		int id = table.getSelectedRow();
		int idd = typeGames.get(id).OrgID;
		typeGames.remove(id);
		((DefaultTableModel)table.getModel()).removeRow(id);
		new Organization().dalete(idd);
	}

	protected void selectRow() {
		DefaultTableModel mod = (DefaultTableModel) table.getModel();
		typeGames = new Organization().select();
		for ( DBOrganization org : typeGames) {
			mod.addRow(new Object[] { org.OrgName });
		}
	}
}
