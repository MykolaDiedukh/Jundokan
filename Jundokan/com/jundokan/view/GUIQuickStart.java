package jundokan.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import jundokan.connection.NewGame;
import jundokan.connection.Protocols;
import jundokan.connection.TypeGame;
import jundokan.model.DBNewGame;
import jundokan.model.DBTypeGames;
import jundokan.view.templates.CollectionProtocolOne;
import jundokan.view.templates.KataProtocol;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jdesktop.swingx.JXTable;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;

public class GUIQuickStart extends JFrame {

	private JPanel contentPane;
	private ResourceBundle bundle;
	private JXTable table;

	/**
	 * Create the frame.
	 */
	public GUIQuickStart() {
		try {
			setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") +"/resource/gui/Jundokan.jpg"));
			setMinimumSize(new Dimension(700, 400));
	        Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
	        Locale.setDefault(new Locale(prop.getKey("locale")));
	        Path path = Paths.get(System.getProperty("user.dir"),"//lang//",Locale.getDefault().toString());
		    bundle = ResourceBundle.getBundle("QuickStart", Locale.getDefault(), new URLClassLoader(new URL[]{path.toUri().toURL()}));
	        init();
	        DimensionFrame dim = new DimensionFrame(getClass().getSimpleName(), 700, 400);
	        addWindowListener(dim.getWindowAdapter(this));
	        setBounds(dim.getBounds());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		setTitle(bundle.getString("title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelLeft = new JPanel();
		contentPane.add(panelLeft, BorderLayout.CENTER);
		panelLeft.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelLeft.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
        scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(10, 10));
		table = new JXTable(){
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
    			Component c = super.prepareRenderer(renderer, row, column);
    			((JComponent)c).setBorder(null);
    			return c;
			}
		};
		table.getTableHeader().setReorderingAllowed(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setGridColor(table.getSelectionBackground());
		table.setShowVerticalLines(false);
		table.setRolloverEnabled(false);
		table.setFillsViewportHeight(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("column1"), bundle.getString("column2"), 
            		bundle.getString("column3"), bundle.getString("column4"), "NGID"
			}));
		fillingTable();
		table.getColumnExt(1).setMaxWidth(150);
		table.getColumnExt(1).setMinWidth(150);
		table.getColumnExt(1).setPreferredWidth(150);
		table.getColumnExt(0).setMaxWidth(80);
		table.getColumnExt(4).setMaxWidth(0);
		table.getColumnExt(4).setMinWidth(0);
		table.getColumnExt(4).setPreferredWidth(0);
		table.setHorizontalScrollEnabled(true);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()){
					new GUIMain((int)table.getValueAt(table.getSelectedRow(), 4)).setVisible(true);
					dispose();
				}
				if (e.getClickCount() == 1 && !e.isConsumed()){
					if(table.isRowSelected(table.getSelectedRow()))
					table.setComponentPopupMenu(getPopupMenu(table.getValueAt(table.getSelectedRow(), 0) == bundle.getString("statusBegin")? true : false));
				}
			}
		});
		
		scrollPane.setViewportView(table);
		
		JPanel panelRight = new JPanel();
		panelRight.setPreferredSize(new Dimension(200, 100));
		contentPane.add(panelRight, BorderLayout.EAST);
		panelRight.setLayout(null);
		
		JLabel lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(System.getProperty("user.dir") +"/resource/gui/IconMainWindows.png"));
		lblImage.setBounds(10, 0, 188, 221);
		panelRight.add(lblImage);
		
		JButton btnCreateGame = new JButton(bundle.getString("btnCreateGame"));
		btnCreateGame.setBounds(10, 230, 185, 23);
		panelRight.add(btnCreateGame);
		btnCreateGame.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new GUIMain().setVisible(true);	
				dispose();
			}
		});
	}
	private JPopupMenu getPopupMenu(final boolean status) {
		JPopupMenu popupMenu = new JPopupMenu();
		Action printProtocol = new AbstractAction(bundle.getString("print")){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PDDocument.load(getPDFBytes()).print();
				} catch (PrinterException | IOException ex) {}
			}
		};
		Action save = new AbstractAction(bundle.getString("save")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
				int chooser = fileChooser.showSaveDialog(GUIQuickStart.this);
				if(!(chooser == JFileChooser.CANCEL_OPTION))
				try {
					String outputPath = Paths.get(fileChooser.getCurrentDirectory().getAbsolutePath(), String.format("%s.pdf",fileChooser.getSelectedFile().getName())).toString();
					mergePDFs(outputPath);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		//save.putValue(Action.SMALL_ICON, new ImageIcon("resource/add-icon.png"));
		Action delete = new AbstractAction(bundle.getString("delete")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewGame().delete((int)table.getValueAt(table.getSelectedRow(), 4));
				((DefaultTableModel)table.getModel()).removeRow(table.convertRowIndexToModel(table.getSelectedRow()));			
			}
		};
		//delete.putValue(Action.SMALL_ICON, new ImageIcon("resource/Delete-icon.png"));
		Action start = new AbstractAction(bundle.getString("start")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("End NGID = "+table.getValueAt(table.getSelectedRow(), 4));
				new NewGame().update((int)table.getValueAt(table.getSelectedRow(), 4), false);
				table.setValueAt(bundle.getString("statusEnd"),table.getSelectedRow() , 0);
				//table.getColumnModel().
			}
		};
		//start.putValue(Action.SMALL_ICON, new ImageIcon("resource/add-icon.png"));
		Action finish = new AbstractAction(bundle.getString("finish")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Begin NGID = "+table.getValueAt(table.getSelectedRow(), 4));
				new NewGame().update((int)table.getValueAt(table.getSelectedRow(), 4), true);
				table.setValueAt(bundle.getString("statusBegin"),table.getSelectedRow() , 0);
				//table.fireTableCellUpdated(table.getSelectedRow(), 0);
			}
		};
		//finish.putValue(Action.SMALL_ICON, new ImageIcon("resource/add-icon.png"));
		
		popupMenu.add(save);
		popupMenu.add(delete);
		popupMenu.add(status ? start : finish);	
		popupMenu.add(printProtocol);
		return popupMenu;
	}
	private void  fillingTable(){
		DefaultTableModel mod = (DefaultTableModel) table.getModel();
		for(DBNewGame ng: new NewGame().select()) { //true = statusEnd
			mod.addRow(new Object[]{ng.NGStatus ? bundle.getString("statusBegin") : bundle.getString("statusEnd"), ng.NGDBegin + " - " + ng.NGDEnd  , ng.NGCity, ng.NGName, ng.NGID});
		}
	}
	private ByteArrayInputStream getPDFBytes() {
		try {			
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			int NGID = (int)table.getValueAt(table.getSelectedRow(), 4);
			ArrayList<HashMap<String, String>> kymiteGrids = new Protocols().selectGrids(NGID, false);
			ArrayList<HashMap<String, String>> kataGrids = new Protocols().selectGrids(NGID, true);
			if(kymiteGrids.size() > 0 || kataGrids.size() > 0){
				PdfCopyFields mergeFiles = new PdfCopyFields(bytes);
				if(kymiteGrids.size() > 0) mergeFiles.addDocument(new PdfReader(new CollectionProtocolOne(kymiteGrids).getByteArrayPDF()));
				if(kataGrids.size() > 0) mergeFiles.addDocument(new PdfReader(new KataProtocol(kataGrids).getByteArrayPDF()));
				mergeFiles.close();	
				return new ByteArrayInputStream(bytes.toByteArray());
			} else JOptionPane.showMessageDialog(null, bundle.getString("nohaventGrids"));
			
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private void mergePDFs(String outputPath){
		try {			
			int NGID = (int)table.getValueAt(table.getSelectedRow(), 4);
			ArrayList<HashMap<String, String>> kymiteGrids = new Protocols().selectGrids(NGID, false);
			ArrayList<HashMap<String, String>> kataGrids = new Protocols().selectGrids(NGID, true);
			if(kymiteGrids.size() > 0 || kataGrids.size() > 0){
				PdfCopyFields mergeFiles = new PdfCopyFields(new FileOutputStream(outputPath));
				if(kymiteGrids.size() > 0) mergeFiles.addDocument(new PdfReader(new CollectionProtocolOne(kymiteGrids).getByteArrayPDF()));
				if(kataGrids.size() > 0) mergeFiles.addDocument(new PdfReader(new KataProtocol(kataGrids).getByteArrayPDF()));
				mergeFiles.close();	
			} else JOptionPane.showMessageDialog(null, bundle.getString("nohaventGrids"));
			
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
}
