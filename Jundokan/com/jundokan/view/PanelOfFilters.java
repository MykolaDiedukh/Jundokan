package jundokan.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import javax.swing.RowSorter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.JXTable;

import jundokan.connection.Degrees;
import jundokan.model.DBDegrees;
import jundokan.view.range_slider.RangeSlider;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
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

public class PanelOfFilters extends JPanel {
	private JTextField txtSearch;
	private JTextField txtWeightBegin;
	private JTextField txtWeightEnd;
	private JTextField txtAgeBegin;
	private JTextField txtAgeEnd;
	private JXTable table;
	private JComboBox jcbDegree;
	private JComboBox jcbSex;
	private RangeSlider sliderAge;
	private RangeSlider sliderWeight;
	private ResourceBundle bundle;
	private TextPrompt txtPromt;
	private TableRowSorter<TableModel> sorted;	
	
	public PanelOfFilters(JXTable table) {

		this.table = table;
		this.sorted = new TableRowSorter<TableModel>(this.table.getModel());
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
        Locale.setDefault(new Locale(prop.getKey("locale")));
        try {
        	Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("PanelOfFilters", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setAlignmentY(Component.CENTER_ALIGNMENT);
		setAlignmentX(Component.CENTER_ALIGNMENT);		
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(383, 160));
		panel.setMaximumSize(new Dimension(383, 160));
		panel.setMinimumSize(new Dimension(383, 160));
		add(panel);
		panel.setLayout(null);
		
		jcbDegree = new JComboBox();
		jcbDegree.setEditable(true);
		txtPromt = new TextPrompt(bundle.getString("jcbDegree"), (JTextField) jcbDegree.getEditor().getEditorComponent());
		txtPromt.changeAlpha(0.5f);
		jcbDegree.setBounds(10, 104, 174, 20);
		jcbDegree.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jcbDegree.getSelectedIndex()==0) jcbDegree.setSelectedItem("");
				setFilter();
				//flag ? setFilter();(flag) : setFilter();();
				//setFilter();();
			}
		});
		panel.add(jcbDegree);
		
		txtSearch = new JTextField();
		TextPrompt txtPromt = new TextPrompt(bundle.getString("txtSearch"), txtSearch);
		txtPromt.changeAlpha(0.5f);
		txtSearch.setBounds(10, 135, 363, 20);
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setFilter();
			}			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setFilter();
			}			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		panel.add(txtSearch);
		txtSearch.setColumns(10);
		
		jcbSex = new JComboBox();
		jcbSex.setEditable(true);
		txtPromt = new TextPrompt(bundle.getString("jcbSex"), (JTextField) jcbSex.getEditor().getEditorComponent());
		txtPromt.changeAlpha(0.5f);
		jcbSex.addItem(bundle.getString("allSex"));
		jcbSex.addItem("Чоловік");
		jcbSex.addItem("Жінка");
		jcbSex.setSelectedItem("");
		jcbSex.setBounds(199, 104, 174, 20);
		jcbSex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jcbSex.getSelectedIndex()==0) jcbSex.setSelectedItem("");
				setFilter();
			}
		});
		panel.add(jcbSex);
		
		JPanel panelWeight = new JPanel();
		panelWeight.setBorder(BorderFactory.createTitledBorder(bundle.getString("Weight")));
		panelWeight.setBounds(10, 11, 174, 82);
		panel.add(panelWeight);
		panelWeight.setLayout(null);
		
		sliderWeight = new RangeSlider();
		sliderWeight.setFocusable(false);
		sliderWeight.setMaximum(200);
		sliderWeight.setMinimum(0);
		sliderWeight.setValue(0);
		sliderWeight.setUpperValue(200);
		sliderWeight.setBounds(10, 14, 154, 26);
		sliderWeight.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				txtWeightBegin.setText(String.valueOf(sliderWeight.getValue()));
				txtWeightEnd.setText(String.valueOf(sliderWeight.getUpperValue()));
				setFilter();
			}
		});
		panelWeight.add(sliderWeight);
		
		txtWeightBegin = new JTextField();
		txtPromt = new TextPrompt(bundle.getString("txtWeightBegin"), txtWeightBegin);
		txtPromt.changeAlpha(0.5f);
		txtWeightBegin.setText(String.valueOf(sliderWeight.getValue()));
		txtWeightBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sliderWeight.setValue(txtWeightBegin.getText().length()>0 ? Integer.parseInt(txtWeightBegin.getText()): 0);
			}
		});
		txtWeightBegin.setBounds(20, 51, 38, 20);
		panelWeight.add(txtWeightBegin);
		txtWeightBegin.setColumns(10);
		
		txtWeightEnd = new JTextField();
		txtPromt = new TextPrompt(bundle.getString("txtWeightEnd"), txtWeightEnd);
		txtPromt.changeAlpha(0.5f);
		txtWeightEnd.setText(String.valueOf(sliderWeight.getUpperValue()));
		txtWeightEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sliderWeight.setUpperValue(txtWeightEnd.getText().length()>0 ? Integer.parseInt(txtWeightEnd.getText()): 0);
			}
		});
		txtWeightEnd.setColumns(10);
		txtWeightEnd.setBounds(100, 51, 38, 20);
		panelWeight.add(txtWeightEnd);
		
		JPanel panelAge = new JPanel();
		panelAge.setLayout(null);
		panelAge.setBounds(199, 11, 174, 82);
		panelAge.setBorder(BorderFactory.createTitledBorder(bundle.getString("Age")));
		panel.add(panelAge);
		
		sliderAge = new RangeSlider();
		sliderAge.setFocusable(false);
		sliderAge.setMaximum(100);
		sliderAge.setMinimum(0);
		sliderAge.setValue(0);
		sliderAge.setUpperValue(100);
		sliderAge.setBounds(10, 14, 154, 26);
		sliderAge.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				txtAgeBegin.setText(String.valueOf(sliderAge.getValue()));
				txtAgeEnd.setText(String.valueOf(sliderAge.getUpperValue()));
				setFilter();
			}
		});
		panelAge.add(sliderAge);
		
		txtAgeBegin = new JTextField();
		txtPromt = new TextPrompt(bundle.getString("txtAgeBegin"), txtAgeBegin);
		txtPromt.changeAlpha(0.5f);
		txtAgeBegin.setText(String.valueOf(sliderAge.getValue()));
		txtAgeBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sliderAge.setValue(txtAgeBegin.getText().length()>0 ? Integer.parseInt(txtAgeBegin.getText()): 0);
			}
		});
		txtAgeBegin.setColumns(10);
		txtAgeBegin.setBounds(20, 51, 38, 20);
		panelAge.add(txtAgeBegin);
		
		txtAgeEnd = new JTextField(sliderAge.getUpperValue());
		txtPromt = new TextPrompt(bundle.getString("txtAgeEnd"), txtAgeEnd);
		txtPromt.changeAlpha(0.5f);
		txtAgeEnd.setText(String.valueOf(sliderAge.getUpperValue()));
		txtAgeEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sliderAge.setUpperValue(txtAgeEnd.getText().length()>0 ? Integer.parseInt(txtAgeEnd.getText()): 0);
			}
		});
		txtAgeEnd.setColumns(10);
		txtAgeEnd.setBounds(100, 51, 38, 20);
		panelAge.add(txtAgeEnd);
		initDegrees();
	}
	
	private void initDegrees() {
		this.jcbDegree.addItem(bundle.getString("allDegree"));
		for (DBDegrees degree : new Degrees().select()) {
			this.jcbDegree.addItem(degree.Degree);
		}
		jcbDegree.setSelectedItem("");
	}
	
	private void setFilter( ) {
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
		RowFilter<Object,Object> filterWeight = new RowFilter<Object,Object>() {
			   public boolean include(Entry<? extends Object, ? extends Object> entry) {
				   return ((Integer)entry.getValue(6) >= sliderWeight.getValue() 
						   && (Integer)entry.getValue(6)  <= sliderWeight.getUpperValue());
			   }
			 };
		RowFilter<Object,Object> filterAge = new RowFilter<Object,Object>() {
			public boolean include(Entry<? extends Object, ? extends Object> entry) {
				return ((Integer)entry.getValue(4) >= sliderAge.getValue() 
						&& (Integer)entry.getValue(4)  <= sliderAge.getUpperValue());
			}
		};
		filters.add(filterAge);
		filters.add(filterWeight);
		filters.add(RowFilter.regexFilter(String.valueOf(this.jcbDegree.getSelectedItem()), 7));
		filters.add(RowFilter.regexFilter(String.valueOf(this.jcbSex.getSelectedItem()), 5));
		filters.add(RowFilter.regexFilter(txtSearch.getText(), 1,2,3));
		sorted.setRowFilter(RowFilter.andFilter(filters));
		table.setRowSorter(sorted);
    }
	
	
	/*private void newFilter( ) {
		ArrayList<RowFilter<Object, Object>> andFilters = new ArrayList<RowFilter<Object, Object>>();
		andFilters.add(RowFilter.regexFilter(lastName.getText(), 0));
		andFilters.add(RowFilter.regexFilter(firstName.getText(), 1));

		sorter.setRowFilter(RowFilter.andFilter(andFilters));

		table.setRowSorter(sorter);
    }*/
	
	/*public static void CrateGui(){
		JFrame frame = new JFrame();
		frame.setSize(550, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(new PanelOfFilters(null), BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public static void main(String [] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				CrateGui();
			}
		});
	}*/
}
