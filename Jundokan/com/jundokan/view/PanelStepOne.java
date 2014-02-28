package jundokan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import jundokan.connection.TypeGame;
import jundokan.model.DBFTGames;
import jundokan.model.DBNewGame;
import jundokan.model.DBTypeGames;

import org.jdesktop.swingx.JXTable;

import com.toedter.calendar.JDateChooser;

public class PanelStepOne extends JPanel {
	public JTextField txtNameGame;
	public JTextField txtVenue;
	public JDateChooser dcDateBegin;
	public JDateChooser dcDateEnd;
	private ResourceBundle bundle;
	private TextPrompt text;
	private JXTable table;
	private ArrayList<Integer> typeGameIds;
	private static PanelStepOne instance;

	private PanelStepOne() {
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("PanelStepOne", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setPreferredSize(new Dimension(1000, 600));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setMinimumSize(new Dimension(600, 400));
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 153, 0));
		panel.setBounds(new Rectangle(0, 0, 600, 400));
		panel.setMinimumSize(new Dimension(600, 400));
		panel.setMaximumSize(new Dimension(600, 400));
		panel.setPreferredSize(new Dimension(650, 400));
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelInfo = new JPanel();
		panelInfo.setPreferredSize(new Dimension(10, 70));
		panelInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(panelInfo, BorderLayout.NORTH);

		txtNameGame = new JTextField();
		txtNameGame.setBounds(10, 13, 310, 20);
		text = new TextPrompt(bundle.getString("txtNameGame"), txtNameGame);
		text.changeAlpha(0.5f);
		txtNameGame.setColumns(10);

		txtVenue = new JTextField();
		txtVenue.setBounds(326, 13, 314, 20);
		text = new TextPrompt(bundle.getString("txtVenue"), txtVenue);
		text.changeAlpha(0.5f);
		txtVenue.setColumns(10);

		dcDateBegin = new JDateChooser();
		dcDateBegin.setBounds(10, 39, 310, 20);
		text = new TextPrompt(bundle.getString("dcDateBegin"), (JTextField) dcDateBegin.getDateEditor());
		text.changeAlpha(0.5f);

		dcDateEnd = new JDateChooser();
		dcDateEnd.setBounds(326, 39, 314, 20);
		text = new TextPrompt(bundle.getString("dcDateEnd"), (JTextField) dcDateEnd.getDateEditor());
		text.changeAlpha(0.5f);
		panelInfo.setLayout(null);
		panelInfo.add(dcDateBegin);
		panelInfo.add(txtNameGame);
		panelInfo.add(dcDateEnd);
		panelInfo.add(txtVenue);

		JPanel panelTable = new JPanel();
		panelTable.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelTable.setPreferredSize(new Dimension(400, 200));
		panel.add(panelTable);
		panelTable.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 630, 319);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		table = new JXTable();
		table.setOpaque(false);
		table.setModel(new DefaultTableModel(
				new Object[][] {}, 
				new String[] {
					bundle.getString("column1"), bundle.getString("column2"), "pattern" }) {
			Class [] types  = new Class[]{
					String.class, Boolean.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
	            return types [columnIndex];
	        }
		});
		table.setSortable(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnExt(2).setVisible(false);
		table.setFillsViewportHeight(true);
		table.setComponentPopupMenu(getPopupMenu(true));
		fillingTable();
		scrollPane.setViewportView(table);
		panelTable.add(scrollPane);
	}
	public ArrayList<DBFTGames> get() {
		ArrayList<DBFTGames> dbftGames = new ArrayList<DBFTGames>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.getValueAt(i, 1).equals(true)){
				DBFTGames dbftGame = new DBFTGames();
				dbftGame.TGName = (String)table.getValueAt(i, 0);
				dbftGame.TGPattern = ((boolean)((DefaultTableModel)table.getModel()).getValueAt(i, 2));
				dbftGames.add(dbftGame);
			}
		}
		return dbftGames;
	}
	private void  fillingTable(){
		typeGameIds = new ArrayList<Integer>();
		DefaultTableModel mod = (DefaultTableModel) table.getModel();
		for(DBTypeGames tg: new TypeGame().select()) {
			mod.addRow(new Object[]{tg.TGName, false, tg.TGPattern});
			typeGameIds.add(tg.TGID);
		}
	}
	public ArrayList<Integer> getSelectedTypeGameIds() {
		ArrayList<Integer> selectedTypeGameIds = new ArrayList<Integer>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.getValueAt(i, 1).equals(true)){
				selectedTypeGameIds.add(typeGameIds.get(i));
			}
		}
		return selectedTypeGameIds;
	}
	public DBNewGame getNewGame(){
		DBNewGame newGame = new DBNewGame();
		newGame.NGName = txtNameGame.getText();
		newGame.NGCity = txtVenue.getText();
		newGame.NGStatus = true;
		newGame.NGDBegin = Date.getDate(dcDateBegin.getDate());
		newGame.NGDEnd = Date.getDate(dcDateEnd.getDate());
		return newGame;
	}
	public boolean isSelectedCell() {
		for (int i = 0; i < table.getRowCount(); i++)
			if (table.getValueAt(i, 1).equals(true))
				return true;
		return false;
	}
	public boolean isEmptyRow() {
		return table.getRowCount() > 0 ? true : false;
	}
	private JPopupMenu getPopupMenu(boolean status) {
		JPopupMenu popupMenu = new JPopupMenu();
		Action typeGames = new AbstractAction(bundle.getString("add")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GUITypeGame().setVisible(true);
			}
		};
		popupMenu.add(typeGames);
		return popupMenu;
	}
	public static PanelStepOne getInstance(){
		return instance == null ? new PanelStepOne() : instance;
	}
	public void addRow(DBTypeGames tg){
		((DefaultTableModel)table.getModel()).addRow(new Object[]{tg.TGName, false, tg.TGPattern});
		typeGameIds.add(tg.TGID);
	}
	public void updateRow(DBTypeGames tg){
		table.setValueAt(tg.TGName, typeGameIds.indexOf(tg.TGID), 0);
	}
	public void removeRow(int id){
		((DefaultTableModel)table.getModel()).removeRow(typeGameIds.indexOf(id));
		typeGameIds.remove(typeGameIds.indexOf(id));
	}
}