package jundokan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
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
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

import jundokan.connection.Degrees;
import jundokan.connection.FilterMembers;
import jundokan.connection.Organization;
import jundokan.connection.Persons;
import jundokan.model.DBFilterMembers;
import jundokan.model.DBPersonExtend;
import jundokan.model.DBPersons;
import jundokan.view.templates.ConvertToPdf;
import jundokan.view.templates.ProtocolOne;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;

public class PanelNewGrid extends JPanel implements ITransferData{
	private JXTable table;
	private ArrayList<DBFilterMembers> filterMembers;
	private ArrayList<String[]> list;
	private ResourceBundle bundle;
	private int FGID;
	private HashMap<String, ITransferData> event;
	public PanelNewGrid(final int FGID, final boolean pattern, final JTabbedPane tabbedPane, final int index, final DefaultListModel listTaskPane) {
		this.FGID = FGID;
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("PanelNewGrid", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}	
		
		setLayout(new BorderLayout(0, 0));
		JButton createGrid = new JButton();
		
		JXTaskPane taskPane = new JXTaskPane();
		taskPane.setCollapsed(true);
		taskPane.setTitle(bundle.getString("taskPane"));
		
		add(taskPane, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JXTable(){
			public boolean isCellEditable (int row, int column){
			if(column <9)
			return false;
			return true;
		}};
		
		table.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {
						bundle.getString("foto"), bundle.getString("firstName"), bundle.getString("lastName"),
						bundle.getString("midlleName"), bundle.getString("age"), bundle.getString("sex"),
						bundle.getString("weight"), bundle.getString("degree"), bundle.getString("orgName"), 
						"PersID", bundle.getString("accept") 
				}
			){	
				public Class getColumnClass(int column)  
				{  
				    if (column == 0) {  
				        return Icon.class;  
				    } else if (column > 0 && column <10){
				    	return Object.class;
				    }
				    return Boolean.class;
				} 
			});
		table.getColumnModel().getColumn(0).setMaxWidth(47);
		table.setRowHeight(46);
		scrollPane.setViewportView(table);
		table.getColumnExt(9).setVisible(false);
		taskPane.add(new PanelOfFilters(table));
		FlowLayout flow = new FlowLayout(new FlowLayout().RIGHT);
		JPanel buttonPanel = new JPanel(flow);
		flow.setHgap(0);
		flow.setVgap(0);
		JButton btnCancel = new JButton(bundle.getString("btnCancel"));
		//btnCancel.setMargin(new Insets(0, 0, 0, 0));
		btnCancel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				event.remove(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
				tabbedPane.remove(tabbedPane.getSelectedIndex());
			}
		});
		buttonPanel.add(btnCancel);
		JButton btnCreateGrid = new JButton(bundle.getString("btnCreateGrid"));
		//btnCreateGrid.setMargin(new Insets(0, 0, 0, 0));
		btnCreateGrid.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isBounds()){					
					if(pattern)	{
						String title = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
						listTaskPane.addElement(title);
						event.remove(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
						tabbedPane.remove(tabbedPane.getSelectedIndex());
						ConvertToPdf convertToPdf = new ConvertToPdf(list, FGID, title);
						tabbedPane.addTab(title,convertToPdf);
						tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane, null));
						tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).setName(String.valueOf(convertToPdf.getProtID()));
						tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
						new FilterMembers().update(filterMembers, convertToPdf.getProtID()); //оце данні передаються
					}
					else {
						String title = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
						listTaskPane.addElement(title);
						event.remove(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
						tabbedPane.remove(tabbedPane.getSelectedIndex());
						ProtocolOne protocolOne = new ProtocolOne(list, FGID, title);
						tabbedPane.addTab(title,protocolOne);
						tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane, null));
						tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).setName(String.valueOf(protocolOne.getProtID()));
						tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
						new FilterMembers().update(filterMembers, protocolOne.getProtID());
					}
				}else {JOptionPane.showMessageDialog(null, bundle.getString("or"));}
			}
		});
		buttonPanel.add(btnCreateGrid);
		add(buttonPanel, BorderLayout.SOUTH);
		new Thread(){
			public void run(){
				fillingTable();}
			}.start();
	}
	private boolean isBounds(){
		filterMembers = new ArrayList<DBFilterMembers>();		
		list = new ArrayList<String[]>();
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			if(table.getModel().getValueAt(i, 10).equals(true)){
				DBFilterMembers filterMember = new DBFilterMembers();
				String [] array = new String [2];
				array[0] = (String)table.getModel().getValueAt(i, 8);
				array[1] = String.format("%s %s", table.getModel().getValueAt(i, 1), table.getModel().getValueAt(i, 2));
				filterMember.FGID = FGID;
				filterMember.FMStatus = false;
				filterMember.PersID = (int)table.getModel().getValueAt(i, 9);
				filterMembers.add(filterMember);
				list.add(array);
			}
		}
		return ((filterMembers.size() >1) && (filterMembers.size()<17)) ? true : false;
	}
	private void fillingTable() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		for (DBPersons person : new Persons().selectType(FGID)) {
			String degreeName = new Degrees().select(person.DegreeID);
			String orgName = new Organization().select(person.OrgID);
			ImageIcon icon = new ImageIcon();
			try {
				if(!person.PersPhoto.equals(""))
				icon = ScalingImage.getScalingImage(ImageIO.read(new File(person.PersPhoto)), new Dimension(45, 45));
			} catch (IOException e) {
				e.printStackTrace();
			}
			model.addRow(new Object [] {
					icon, person.PersFN, person.PersLN, person.PersMN, person.PersAge, 
					person.PersSex ? bundle.getString("male") : bundle.getString("female"), person.PersWeight, degreeName, orgName, person.PersID, false});
		}
	}
	@Override
	public void transferAdd(Object data) {}
	@Override
	public void transferUpdate(Object data) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.getModel().getValueAt(i, 9).equals(((DBPersonExtend)data).PersID)){				
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
				table.getColumnExt(8).setEditable(true);
				table.getModel().setValueAt(((DBPersonExtend)data).Organization, i,8);
				table.getColumnExt(8).setEditable(false);
				break;
				
			}
		}
	}
	@Override
	public void transferRemove(int id) {}
	public void setHashMap(HashMap<String, ITransferData> event) {
		this.event = event; 
	}
}