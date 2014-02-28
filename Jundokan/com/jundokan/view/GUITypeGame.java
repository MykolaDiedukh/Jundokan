package jundokan.view;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import org.jdesktop.swingx.JXTable;
import jundokan.connection.TypeGame;
import jundokan.model.DBTypeGames;

public final class GUITypeGame extends JDialog {
	protected JPanel contentPane;
	protected JXTable table;
	protected ArrayList<DBTypeGames> typeGames;
	protected String message;
	private JPanel panelButton;
	private JButton btnAdd;
	private JButton btnRemove;
	private ResourceBundle bundle;
	private JComboBox combo;
	private ITransferData transferData;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITypeGame frame = new GUITypeGame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void setTransferDataListener(ITransferData transferData) {
		this.transferData = transferData;
	}
	class ComboBoxCellRenderer implements TableCellRenderer {
		JComboBox combo;

		public ComboBoxCellRenderer() {
			this.combo = new JComboBox();
			combo.setBackground(SystemColor.control);
			combo.addItem(bundle.getString("kata"));
			combo.addItem(bundle.getString("kymite"));
			combo.setBorder(null);
			combo.setEnabled(false);
			combo.setEditable(true);
			combo.setSelectedIndex(-1);
			((JTextField) combo.getEditor().getEditorComponent()).setBorder(null);
			TextPrompt tp = new TextPrompt(bundle.getString("message2"), (JTextField) combo.getEditor().getEditorComponent());
			tp.changeAlpha(0.5f);
		}

		public Component getTableCellRendererComponent(JTable jtable,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			combo.setSelectedItem(value);
			if (isSelected) {
				((JTextField)combo.getEditor().getEditorComponent()).setBackground(SystemColor.control);
			}
			return combo;
		}
	}

	public GUITypeGame() {
		setAlwaysOnTop(true);
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("GUITypeGame", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTitle(bundle.getString("title"));
		message = bundle.getString("message");
		DimensionFrame dim = new DimensionFrame(getClass().getSimpleName(),	358, 300);
		addWindowListener(dim.getWindowAdapter(this));
		setBounds(dim.getBounds());
		setModal(true);
		setBounds(100, 100, 358, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(10, 10));
		scrollPane.getVerticalScrollBar().setPreferredSize(	new Dimension(10, 10));
		contentPane.add(scrollPane, BorderLayout.CENTER);

		combo = new JComboBox();
		combo.setBorder(null);
		combo.setBackground(SystemColor.control);
		combo.addItem(bundle.getString("kata"));
		combo.addItem(bundle.getString("kymite"));
		//combo.setEditable(true);
		TextPrompt tp = new TextPrompt(bundle.getString("message2"), (JTextField) combo.getEditor()
				.getEditorComponent());
		tp.changeAlpha(0.5f);
		table = new JXTable() {
			DefaultCellEditor cellEditor = new DefaultCellEditor(combo);
			public TableCellEditor getCellEditor(int row, int column) {
				int modelColumn = convertColumnIndexToModel(column);
				if (modelColumn == 0 && row >= typeGames.size())
					return cellEditor;
				else
					return super.getCellEditor(row, column);
			}

			public TableCellRenderer getCellRenderer(int row, int column) {
				int modelColumn = convertColumnIndexToModel(column);
				if (modelColumn == 0 && row >= typeGames.size())
					return new ComboBoxCellRenderer();
				else
					return super.getCellRenderer(row, column);	
			}

			public boolean isCellEditable(int row, int column) {
				if (column == 0 && row < typeGames.size()) return false;
				return true;
			}
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
	    			Component c = super.prepareRenderer(renderer, row, column);
	    			((JComponent)c).setBorder(null);
	    			return c;
	    	}
		};
		scrollPane.setViewportView(table);
		table.setIntercellSpacing(new Dimension(0,0));
		table.setOpaque(false);
		table.setSortable(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setShowVerticalLines(false);
		table.setFillsViewportHeight(false);
		table.setSelectionBackground(SystemColor.control);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				bundle.getString("typePattern"),bundle.getString("typeGame") }) {

			Class[] types = new Class[] { String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
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
				if (typeGames.size() < table.getModel().getRowCount()) {
					table.getModel().setValueAt("", table.getRowCount() - 1, 1);
				} else if (!table.getModel()
						.getValueAt((table.getRowCount() - 1), 1).equals("")) {
					((DefaultTableModel) table.getModel())
							.addRow(new Object[] { "" });
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
				if ((table.getSelectedRow() != -1) && (typeGames.size() > table.getSelectedRow())) {
					deleteRow();
				}
			}
		});
		panelButton.add(btnRemove);
		table.getColumnModel().getColumn(1).setCellRenderer(new TextFildRender());
		selectRow();
		((DefaultTableModel) table.getModel()).addRow(new Object[] { "",""});
		table.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					if ((!table.getValueAt(table.getSelectedRow(), 0).equals("")) && (!table.getValueAt(table.getSelectedRow(), 1).equals(""))) {
						if (typeGames.size() <= table.getSelectedRow()){
							insertRow();
						}
						else
							update();
					}
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
	}
	private class TextFildRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JTextField text = new JTextField();
			text.setBorder(null);
			TextPrompt prom = new TextPrompt(message, text);
			prom.changeAlpha(0.5f);
			if (isSelected)
				text.setBackground(SystemColor.control);
			text.setText((String) value);
			return text;
		}
	}

	protected void update() {
		int id = table.getSelectedRow();
		DBTypeGames tg = typeGames.get(id);
		tg.TGName = (String) table.getValueAt(id, 1);
		new TypeGame().update(tg);
		if(this.transferData != null) this.transferData.transferUpdate(tg);
	}

	protected void insertRow() {
		int id = table.getSelectedRow();
		DBTypeGames tg = new DBTypeGames();
		tg.TGName = (String) table.getValueAt(id, 1);
		tg.TGPattern = ((String) table.getValueAt(id, 0)).equals(bundle.getString("kata")) ? true : false;
		tg.TGID = new TypeGame().insert(tg);
		typeGames.add(tg);
		if(this.transferData != null) this.transferData.transferAdd(tg);
	}

	protected void deleteRow() {
		int id = table.getSelectedRow();
		int idd = typeGames.get(id).TGID;
			boolean exception = true;
			try{
				new TypeGame().dalete(idd);
			} catch (Exception e) {
				e.printStackTrace();
				exception = false;
			}			
			if(this.transferData != null && exception) {
				typeGames.remove(id);
				((DefaultTableModel) table.getModel()).removeRow(id);
				this.transferData.transferRemove(idd);
			}
		
	}

	protected void selectRow() {
		DefaultTableModel mod = (DefaultTableModel) table.getModel();
		typeGames = new TypeGame().select();
		for (DBTypeGames tg : typeGames) {
			mod.addRow(new Object[] { tg.TGPattern ? bundle.getString("kata") : bundle.getString("kymite"), tg.TGName });
		}
	}
}
