package jundokan.view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import jundokan.connection.*;
import jundokan.model.DBPersonExtend;
import jundokan.model.DBPersons;
import org.jdesktop.swingx.*;

public class GUIListPerson extends JDialog implements ITransferData{
	private JPanel contentPane;
	private ResourceBundle bundle;
	private JXTable table;
	//private ITransferData transferData;
	private ArrayList<ITransferData> listeners;
	public GUIListPerson() {
		setModal(true);
		DimensionFrame dim = new DimensionFrame(getClass().getSimpleName(), 500, 300);
        addWindowListener(dim.getWindowAdapter(this));
        setBounds(dim.getBounds());
        Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("GUIListPerson", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		setTitle(bundle.getString("title"));
		listeners = new ArrayList<ITransferData>();
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);		
		table = new JXTable();
		table.setEditable(false);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10,10));
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(10,10));
		table.setHorizontalScrollEnabled(true);
		table.setOpaque(false);
		table.setFillsViewportHeight(false);		
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("foto"), bundle.getString("firstName"), bundle.getString("lastName"),
					bundle.getString("midlleName"), bundle.getString("age"), bundle.getString("sex"),
					bundle.getString("weight"), bundle.getString("degree"), "PersID"
			})
		{	
			public Class getColumnClass(int column) {  
			    if (column == 0) {  
			        return Icon.class;  
			    } 
			    return Object.class;
			} 
		});
		table.getColumnModel().getColumn(0).setMaxWidth(47);
		table.setRowHeight(46);
		table.getColumnExt(8).setMinWidth(0);
		table.getColumnExt(8).setMaxWidth(0);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()){						
					GUIPersons person = new GUIPersons(new Persons().select((int)table.getValueAt(table.getSelectedRow(), 8)));
					person.addTransferDataListener(GUIListPerson.this);
					person.setVisible(true);
				}
			}
		});
		table.setAutoCreateRowSorter(true); 
		JXTaskPane taskPane = new JXTaskPane();
		taskPane.setCollapsed(true);
		taskPane.setTitle(bundle.getString("taskPane"));
		taskPane.add(new PanelOfFilters(table));
		add(taskPane, BorderLayout.NORTH);
		scrollPane.setViewportView(table);
		
		new Thread(){
			public void run(){
				fillingTable();}
			}.start();
		
	}
	private void fillingTable() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		for (DBPersons person : new Persons().select()) {
			String degreeName = new Degrees().select(person.DegreeID);
			ImageIcon icon = new ImageIcon();
			try {
				if(!person.PersPhoto.equals(""))
				icon = ScalingImage.getScalingImage(ImageIO.read(new File(person.PersPhoto)), new Dimension(45, 45));
			} catch (IOException e) {
				e.printStackTrace();
			}
			model.addRow(new Object [] {
					icon, person.PersFN, person.PersLN, person.PersMN, person.PersAge, 
					person.PersSex ? bundle.getString("male") : bundle.getString("female"), 
					person.PersWeight, degreeName, person.PersID});
		}
	}
	@Override
	public void transferAdd(Object data) {
		for (ITransferData transferData : listeners) {
			transferData.transferAdd(data);
		}
	}
	@Override
	public void transferUpdate(Object data) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.getModel().getValueAt(i, 8).equals(((DBPersonExtend)data).PersID)){
				table.setEditable(true);
				ImageIcon icon = new ImageIcon();
				try {
					if(!((DBPersonExtend)data).PersPhoto.equals(""))
					icon = ScalingImage.getScalingImage(ImageIO.read(new File(((DBPersonExtend)data).PersPhoto)), new Dimension(45, 45));
				} catch (IOException e) {
					e.printStackTrace();
				}
				table.getModel().setValueAt(icon, i,0);
				table.getModel().setValueAt(((DBPersonExtend)data).PersFN, i,1);
				table.getModel().setValueAt(((DBPersonExtend)data).PersLN, i,2);
				table.getModel().setValueAt(((DBPersonExtend)data).PersMN, i,3);
				table.getModel().setValueAt(((DBPersonExtend)data).PersAge, i,4);
				table.getModel().setValueAt(((DBPersonExtend)data).PersSex ? bundle.getString("male") : bundle.getString("female"), i,5);
				table.getModel().setValueAt(((DBPersonExtend)data).PersWeight, i,6);
				table.getModel().setValueAt(((DBPersonExtend)data).Deegree, i,7);
				//table.revalidate();
				table.setEditable(false);
				for (ITransferData transferData : listeners) {
					transferData.transferUpdate(data);
				}
				break;
				
			}
		}
	}
	@Override
	public void transferRemove(int id) {}
	public void addTransferDataListener(ITransferData transferData) {
		listeners.add(transferData);
	}
	
}