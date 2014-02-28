package jundokan.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

import org.jdesktop.swingx.JXTable;

public abstract class Template<T> extends JDialog {
	protected JPanel contentPane;
	protected JXTable table;
	protected ArrayList<T> typeGames;
	protected String message;
	private JPanel panelButton;
	private JButton btnAdd;
	private JButton btnRemove;

	public Template() {
		//setDefaultCloseOperation(JFrame.);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 358, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JPanel panelTable = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panelTable);
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(10, 10));
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
		panelTable.setLayout(new BorderLayout());
		contentPane.add(scrollPane, BorderLayout.CENTER);
		table = new JXTable();
		table.setOpaque(false);
		table.setShowVerticalLines(false);
		table.setFillsViewportHeight(false);
		panelTable.add(table, BorderLayout.CENTER);
		table.setSelectionBackground(Color.BLUE);
		table.setModel(new DefaultTableModel(new Object[][] {}, 
				new String[] {""}) {

			public Class getColumnClass(int column)  
			{  
			    if (column == 1) {  
			        return JComponent.class;  
			    } 
			    return Object.class;
			} 
		});
		
		panelButton = new JPanel();
		contentPane.add(panelButton, BorderLayout.SOUTH);
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
		
		btnAdd = new JButton("+");
		btnAdd.setFocusable(false);
		btnAdd.setPreferredSize(new Dimension(21, 21));
		btnAdd.setBackground(SystemColor.menu);
		btnAdd.setFont(new Font("Arial Black", Font.BOLD, 11));
		btnAdd.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(typeGames.size() < table.getModel().getRowCount()){
					table.getModel().setValueAt("",table.getRowCount()-1, 0);
				}
				else if (!table.getModel().getValueAt((table.getRowCount() - 1), 0).equals("") ){
					/*if(typeGames.size() < table.getRowCount() )insertRow();*/
					((DefaultTableModel) table.getModel()).addRow(new Object[] { "" });
				 }
			}
		});
		panelButton.add(btnAdd);
		
		btnRemove = new JButton("-");
		btnRemove.setBackground(SystemColor.menu);
		btnRemove.setFocusable(false);
		btnRemove.setPreferredSize(new Dimension(21, 21));
		btnRemove.setFont(new Font("Arial Black", Font.BOLD, 11));
		btnRemove.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, table.getSelectedRow());
				if (table.getSelectedRow() != -1) {
					deleteRow();
				}		
			}
		});
		panelButton.add(btnRemove);			
		table.getColumnModel().getColumn(0).setCellRenderer(new TextFildRender());
		selectRow();
		((DefaultTableModel) table.getModel()).addRow(new Object[] { "" });
		table.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					if(!table.getValueAt(table.getSelectedRow(), 0).equals("")) { 
						if(typeGames.size() <= table.getSelectedRow() )insertRow();
						else update();
					}
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
	}
	
	protected abstract void update();	
	protected abstract void insertRow();
	protected abstract void deleteRow();
	protected abstract void selectRow();
	
	class TextFildRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JTextField text = new JTextField();			
			TextPrompt prom = new TextPrompt(message, text);
			prom.changeAlpha(0.5f);
			if (isSelected) text.setBackground(SystemColor.control);
			text.setText((String) value);
			return text;
		}
	}
}
