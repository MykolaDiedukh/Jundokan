package jundokan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import jundokan.connection.Degrees;
import jundokan.connection.FilterMembers;
import jundokan.connection.PFM;
import jundokan.connection.Persons;
import jundokan.model.DBFilterMembers;
import jundokan.model.DBPFM;
import jundokan.model.DBPersonExtend;
import jundokan.model.DBPersons;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;

public class PanelSubscribeAndUnsubscribe extends JPanel implements ITransferData{
	private ResourceBundle bundle;
	private JXTable table;
	private int FGID;
	private ArrayList<Boolean> idPersons;
	private final boolean SUBSCRIBE = false;
	private final boolean UNSUBSCRIBE = true;
	private final boolean ISSUBSCRIBE;
	private JButton btnSubscribe;
	private HashMap<String, ITransferData> listeners;
	
	public PanelSubscribeAndUnsubscribe(int FGID, final JTabbedPane tabbedPane) {
		this.ISSUBSCRIBE = true;
		initComponent(FGID, false);
		initButtonPanel(tabbedPane, bundle.getString("btnSubscribe"));
		btnSubscribe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				personsSubscribe();
				listeners.remove(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
				tabbedPane.remove(tabbedPane.getSelectedIndex());
			}
		});	
		new Thread(){
			public void run(){
				fillingTableSubscribe();}
			}.start();		
	}
	public PanelSubscribeAndUnsubscribe(int FGID, final JTabbedPane tabbedPane,  final boolean flag) {	
		this.ISSUBSCRIBE = false;
		initComponent(FGID, true);
		initButtonPanel(tabbedPane, bundle.getString("btnUnsubscribe"));
		((DefaultTableModel)table.getModel()).addColumn("Test", new Object []{});
		table.getColumnExt(10).setVisible(false);
		btnSubscribe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				personsUnsubscribe();
				listeners.remove(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
				tabbedPane.remove(tabbedPane.getSelectedIndex());
			}
		});	
		new Thread(){
			public void run(){
				fillingTableUnsubscribe();}
			}.start();	
	}
	private void initButtonPanel(final JTabbedPane tabbedPane, String name){
		FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);
		JPanel buttonPanel = new JPanel(flow);
		flow.setHgap(0);
		flow.setVgap(0);
		JButton btnCancel = new JButton(bundle.getString("btnCancel"));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listeners.remove(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
				tabbedPane.remove(tabbedPane.getSelectedIndex());
			}
		});		
		btnSubscribe = new JButton(name);
		buttonPanel.add(btnCancel);
		buttonPanel.add(btnSubscribe);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	private void initComponent(int FGID, final boolean flag) {
		this.FGID = FGID;
		setLayout(new BorderLayout(0, 0));
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("GUIAddingPersonsInGame", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);	
		table = new JXTable(){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if(flag)
				if((boolean)table.getValueAt(rowIndex, 9)) {
					if(idPersons.get(table.convertRowIndexToModel(rowIndex))) return UNSUBSCRIBE;
					return SUBSCRIBE;
				}
		        return colIndex < 9? false : true;   //Disallow the editing of any cell
		    }
		};
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
					bundle.getString("weight"), bundle.getString("degree"), "ID", bundle.getString("Subscribe")
			})
		{	
			public Class getColumnClass(int column) {  
			    if (column == 0) {  
			        return Icon.class;  
			    }else if (column == 9){
			    	return Boolean.class;
			    } 
			    return Object.class;
			}
		});
		table.getColumnModel().getColumn(0).setMaxWidth(47);
		table.setRowHeight(46);
		table.getColumnExt(8).setMinWidth(0);
		table.getColumnExt(8).setMaxWidth(0);
		table.setAutoCreateRowSorter(true); 
		JXTaskPane taskPane = new JXTaskPane();
		taskPane.setCollapsed(true);
		taskPane.setTitle(bundle.getString("taskPane"));
		taskPane.add(new PanelOfFilters(table));
		add(taskPane, BorderLayout.NORTH);
		scrollPane.setViewportView(table);
	}
	private void personsSubscribe (){
		ArrayList<DBFilterMembers> filterMembers = new ArrayList<DBFilterMembers>();
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			if(table.getModel().getValueAt(i, 9).equals(UNSUBSCRIBE)){
				DBFilterMembers filterMember = new DBFilterMembers();
				filterMember.FGID = FGID;
				filterMember.FMStatus = UNSUBSCRIBE;
				filterMember.PersID = (int)table.getModel().getValueAt(i, 8);
				filterMembers.add(filterMember);
			}
		}
		new FilterMembers().insert(filterMembers);
	}
	private void personsUnsubscribe (){
		ArrayList<Integer> FMIds = new ArrayList<Integer>();
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			if(table.getModel().getValueAt(i, 9).equals(SUBSCRIBE)){
				FMIds.add((int)table.getModel().getValueAt(i, 10));
			}
		}
		new FilterMembers().delete(FMIds);
	}
	private void fillingTableSubscribe() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		for (DBPersons person : new Persons().selects(FGID)) {
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
					person.PersWeight, degreeName, person.PersID, SUBSCRIBE});
		}
	}
	private void fillingTableUnsubscribe() {
		idPersons = new ArrayList<Boolean>();
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		for (DBPFM person : new PFM().select(FGID)) {
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
					person.PersWeight, degreeName, person.PersID, UNSUBSCRIBE, person.FMID});
			idPersons.add(person.FMStatus);
			
		}
	}
	@Override
	public void transferAdd(Object data) {
		if (ISSUBSCRIBE) {
			DBPersonExtend person = (DBPersonExtend) data;
			ImageIcon icon = new ImageIcon();
			try {
				if (!person.PersPhoto.equals(""))
					icon = ScalingImage.getScalingImage(ImageIO.read(new File(person.PersPhoto)), new Dimension(45, 45));
			} catch (IOException e) {
				e.printStackTrace();
			}
			((DefaultTableModel) table.getModel()).addRow(new Object[] {
					icon, person.PersFN, person.PersLN, person.PersMN,
					person.PersAge, person.PersSex ? bundle.getString("male") : bundle.getString("female"),
					person.PersWeight, person.Deegree, person.PersID, SUBSCRIBE });
		}
	}
	@Override
	public void transferUpdate(Object data) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.getModel().getValueAt(i, 8).equals(((DBPersonExtend)data).PersID)){				
				ImageIcon icon = new ImageIcon();
				try {
					if(!((DBPersonExtend)data).PersPhoto.equals(""))
					icon = ScalingImage.getScalingImage(ImageIO.read(new File(((DBPersonExtend)data).PersPhoto)), new Dimension(45, 45));
				} catch (IOException e) {
					e.printStackTrace();
				}
				table.getColumnExt(0).setEditable(true);
				table.getModel().setValueAt(icon, i,0);
				table.getColumnExt(0).setEditable(false);
				table.getColumnExt(1).setEditable(true);
				table.getModel().setValueAt(((DBPersonExtend)data).PersFN, i,1);
				table.getColumnExt(1).setEditable(false);
				table.getColumnExt(2).setEditable(true);
				table.getModel().setValueAt(((DBPersonExtend)data).PersLN, i,2);
				table.getColumnExt(2).setEditable(false);
				table.getColumnExt(3).setEditable(true);
				table.getModel().setValueAt(((DBPersonExtend)data).PersMN, i,3);
				table.getColumnExt(3).setEditable(false);
				table.getColumnExt(4).setEditable(true);
				table.getModel().setValueAt(((DBPersonExtend)data).PersAge, i,4);
				table.getColumnExt(4).setEditable(false);
				table.getColumnExt(5).setEditable(true);
				table.getModel().setValueAt(((DBPersonExtend)data).PersSex ? bundle.getString("male") : bundle.getString("female"), i,5);
				table.getColumnExt(5).setEditable(false);
				table.getColumnExt(6).setEditable(true);
				table.getModel().setValueAt(((DBPersonExtend)data).PersWeight, i,6);
				table.getColumnExt(6).setEditable(false);
				table.getColumnExt(7).setEditable(true);
				table.getModel().setValueAt(((DBPersonExtend)data).Deegree, i,7);
				table.getColumnExt(7).setEditable(false);
				break;
				
			}
		}
	}
	@Override
	public void transferRemove(int id) {}
	public void setHashMap(HashMap<String, ITransferData> event) {
		this.listeners = event; 
	}
}