package jundokan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import jundokan.connection.Degrees;
import jundokan.connection.Persons;
import jundokan.model.DBFTGames;
import jundokan.model.DBFilterMembers;
import jundokan.model.DBPersonExtend;
import jundokan.model.DBPersons;
import jundokan.model.DBTypeGames;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;

public class PanelStepTwo extends JPanel {
	private ResourceBundle bundle;
	private DefaultTableModel tableModel;
	private static PanelStepTwo instance;
	private JXTable table;
	
	private PanelStepTwo() {
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("PanelStepTwo", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);		
		table = new JXTable(){
			public boolean isCellEditable (int row, int column){
			if(column <8)
			return false;
			return true;
		}};
		table.setHorizontalScrollEnabled(true);
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(10,10));
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10,10));
		table.setFillsViewportHeight(false);
		tableModel = new DefaultTableModel(
			new Object[][] {},
			new String[] {
				bundle.getString("foto"), bundle.getString("firstName"), bundle.getString("lastName"),
				bundle.getString("midlleName"), bundle.getString("age"), bundle.getString("sex"),
				bundle.getString("weight"), bundle.getString("degree"), "PersID"
			}
		){			
			public Class getColumnClass(int column)  
			{  
			    if (column == 0) {  
			        return Icon.class;  
			    } else if (column > 0 && column <9){
			    	return Object.class;
			    }
			    return Boolean.class;
			} 
		};	
		table.setModel(tableModel);
		table.getColumnExt(8).setVisible(false);
		scrollPane.setViewportView(table);
		table.setRowHeight(46);
		
		JXTaskPane taskPane = new JXTaskPane();
		taskPane.setTitle(bundle.getString("taskPane"));
		taskPane.setCollapsed(true);
		taskPane.add(new PanelOfFilters(table));
		add(taskPane, BorderLayout.NORTH);
		new Thread(){
			public void run(){
				fillingTable();
				}}.start();	
	}
	public ArrayList<DBFilterMembers> getFilterMembers(int FGID, String columnName) {
		ArrayList<DBFilterMembers> filterMembers = new ArrayList<DBFilterMembers>();
		int columnId = table.getColumnExt(columnName).getModelIndex();
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if(tableModel.getValueAt(i, columnId) != null)
			if(tableModel.getValueAt(i, columnId).equals(true)){
				DBFilterMembers filterMember = new DBFilterMembers();
				filterMember.FGID = FGID;
				filterMember.FMStatus = true;
				filterMember.PersID = (int)tableModel.getValueAt(i, 8);
				filterMembers.add(filterMember);
			}
		}
		return filterMembers;
	}
	public void setColumns(ArrayList<DBFTGames> dbftGames) {
		addColumnTable(dbftGames);
		isVisibleColumn(dbftGames);
	}
	private void addColumnTable(ArrayList<DBFTGames> dbftGames) {
		for (DBFTGames dbftGame : dbftGames) 
			if(tableModel.findColumn(dbftGame.TGName) == -1){
				tableModel.addColumn(dbftGame.TGName, new Object[] {});
			}				
	}
	private void isVisibleColumn(ArrayList<DBFTGames> dbftGames) {
		while (table.getColumnCount() != 8) {
			table.getColumnExt(table.getColumnCount()-1).setVisible(false);	
		}
		for (DBFTGames dbftGame : dbftGames) {
			table.getColumnExt(dbftGame.TGName).setVisible(true);
		}
	}
	public static PanelStepTwo getInstance(){
		return instance = instance == null ? new PanelStepTwo() : instance;
	}
	private void fillingTable() {
		
		for (DBPersons person : new Persons().select()) {
			String degreeName = new Degrees().select(person.DegreeID);
			ImageIcon icon = new ImageIcon();
			try {
				if(person.PersPhoto != null)
				if(!person.PersPhoto.equals(""))
				icon = ScalingImage.getScalingImage(ImageIO.read(new File(person.PersPhoto)), new Dimension(45, 45));
			} catch (IOException e) {
				e.printStackTrace();
			}			
			tableModel.addRow(new Object [] {
					icon, person.PersFN, person.PersLN, person.PersMN, person.PersAge, 
					person.PersSex ? bundle.getString("male") : bundle.getString("female"), 
					person.PersWeight, degreeName, person.PersID});
			table.getColumnModel().getColumn(0).setMaxWidth(47);
		}
	}
	public void addRow(DBPersonExtend person){
		ImageIcon icon = new ImageIcon();
		try {
			if(person.PersPhoto != null)
			if(!person.PersPhoto.equals(""))
			icon = ScalingImage.getScalingImage(ImageIO.read(new File(person.PersPhoto)), new Dimension(45, 45));
		} catch (IOException e) {
			e.printStackTrace();
		}			
		tableModel.addRow(new Object [] {
				icon, person.PersFN, person.PersLN, person.PersMN, person.PersAge, 
				person.PersSex ? bundle.getString("male") : bundle.getString("female"), 
				person.PersWeight, person.Deegree, person.PersID});
	}
	public void updateRow(DBPersonExtend person){
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.getModel().getValueAt(i, 8).equals(person.PersID)){
				table.setEditable(true);
				ImageIcon icon = new ImageIcon();
				try {
					if(!person.PersPhoto.equals(""))
					icon = ScalingImage.getScalingImage(ImageIO.read(new File(person.PersPhoto)), new Dimension(45, 45));
				} catch (IOException e) {
					e.printStackTrace();
				}
				table.getModel().setValueAt(icon, i,0);
				table.getModel().setValueAt(person.PersFN, i,1);
				table.getModel().setValueAt(person.PersLN, i,2);
				table.getModel().setValueAt(person.PersMN, i,3);
				table.getModel().setValueAt(person.PersAge, i,4);
				table.getModel().setValueAt(person.PersSex ? bundle.getString("male") : bundle.getString("female"), i,5);
				table.getModel().setValueAt(person.PersWeight, i,6);
				table.getModel().setValueAt(person.Deegree, i,7);
				//table.revalidate();
				table.setEditable(false);
				break;
				
			}
		}
	}
}