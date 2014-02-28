package jundokan.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalButtonUI;
import javax.swing.plaf.metal.MetalMenuBarUI;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdesktop.swingx.decorator.HighlighterFactory.UIColorHighlighter;

import jundokan.connection.FilterGame;
import jundokan.connection.FilterMembers;
import jundokan.connection.NewGame;
import jundokan.model.DBFTGames;
import jundokan.model.DBFilterGames;
import jundokan.model.DBPersonExtend;
import jundokan.model.DBTypeGames;

import javax.swing.border.BevelBorder;

public class PanelGame extends JPanel implements ITransferData{
	private PanelStepTwo stepTwo;
	private PanelStepThree stepThree;
	private JButton btnStepOne;
	private JButton btnStepTwo;
	private JButton btnStepThree;
	private JPanel cardsPanel;
	private ResourceBundle bundle;
	private PanelStepOne stepOne;
	private IDeletePanelListener closePanelListener;
	private ArrayList<DBFTGames> dbftGames;
	
	public void setDeletePanelListaner(IDeletePanelListener close) {
		this.closePanelListener = close;
	}	
	public PanelGame() {
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("PanelGame", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		setLayout(new BorderLayout(0, 0));
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(SystemColor.control);
		add(panelButtons, BorderLayout.NORTH);
		panelButtons.setLayout(new GridLayout(0, 3, -1, 0));

		cardsPanel = new JPanel();
		add(cardsPanel, BorderLayout.CENTER);
		cardsPanel.setLayout(new CardLayout(0, 0));
		
		stepOne = PanelStepOne.getInstance();
		cardsPanel.add(stepOne, "stepOne");

		btnStepOne = new JButton(bundle.getString("btnStepOne"));
		btnStepOne.setFocusPainted(false);
		btnStepOne.setUI(new MetalButtonUI());
		btnStepOne.setBorder(BorderFactory.createLineBorder(Color.black));
		btnStepOne.setBackground(new Color(10, 147, 252));
		btnStepOne.setForeground(Color.WHITE);
		btnStepOne.setToolTipText(bundle.getString("tipStepOne"));
		btnStepOne.setFont(new Font("Trebuchet MS", Font.ITALIC, 14));
		btnStepOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnStepOne.setBackground(new Color(10, 147, 252));
				btnStepOne.setForeground(Color.WHITE);
				btnStepTwo.setBackground(Color.WHITE);
				btnStepTwo.setForeground(Color.BLACK);
				btnStepThree.setBackground(Color.WHITE);
				btnStepThree.setForeground(Color.BLACK);
				CardLayout cl = (CardLayout) (cardsPanel.getLayout());
				cl.show(cardsPanel, "stepOne");
			}
		});
		panelButtons.add(btnStepOne);
		btnStepTwo = new JButton(bundle.getString("btnStepTwo"));
		btnStepTwo.setBackground(Color.WHITE);
		btnStepTwo.setForeground(Color.BLACK);
		btnStepTwo.setFocusPainted(false);
		btnStepTwo.setUI(new MetalButtonUI());
		btnStepTwo.setBorder(BorderFactory.createLineBorder(Color.black));
		btnStepTwo.setToolTipText(bundle.getString("tipStepTwo"));
		btnStepTwo.setFont(new Font("Trebuchet MS", Font.ITALIC, 14));
		btnStepTwo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cheking()){
					btnStepTwo.setBackground(new Color(10, 147, 252));
					btnStepTwo.setForeground(Color.WHITE);
					btnStepOne.setBackground(Color.WHITE);
					btnStepOne.setForeground(Color.BLACK);
					btnStepThree.setBackground(Color.WHITE);
					btnStepThree.setForeground(Color.BLACK);
					stepTwo = PanelStepTwo.getInstance();
					cardsPanel.add(stepTwo, "stepTwo");
					CardLayout cl = (CardLayout) (cardsPanel.getLayout());
					cl.show(cardsPanel, "stepTwo");
					stepTwo.setColumns(stepOne.get());
				}
			}
		});
		panelButtons.add(btnStepTwo);
		btnStepThree = new JButton(bundle.getString("btnStepThree"));
		btnStepThree.setBackground(Color.WHITE);
		btnStepThree.setForeground(Color.BLACK);
		btnStepThree.setFocusPainted(false);
		btnStepThree.setUI(new MetalButtonUI());
		btnStepThree.setBorder(BorderFactory.createLineBorder(Color.black));
		btnStepThree.setToolTipText(bundle.getString("tipStepThree"));
		btnStepThree.setFont(new Font("Trebuchet MS", Font.ITALIC, 14));
		btnStepThree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cheking()){
					btnStepThree.setBackground(new Color(10, 147, 252));
					btnStepThree.setForeground(Color.WHITE);
					btnStepOne.setBackground(Color.WHITE);
					btnStepOne.setForeground(Color.BLACK);
					btnStepTwo.setBackground(Color.WHITE);
					btnStepTwo.setForeground(Color.BLACK);
					new Thread(new Runnable() {
						@Override
						public void run() {
							stepThree = PanelStepThree.getInstance();
							cardsPanel.add(stepThree, "stepThree");
							CardLayout cl = (CardLayout) (cardsPanel.getLayout());
							cl.show(cardsPanel, "stepThree");	
							btnStepOne.setVisible(false);
							btnStepTwo.setVisible(false);
							btnStepThree.setVisible(false);
						}
					}).start();				
					deletePanel(createGame());				
				}
			}
		});
		panelButtons.add(btnStepThree);

	}
	private void deletePanel(int NGID) {
		closePanelListener.closePanel(this, dbftGames);
	}
	private int createGame() {
		
		int NGID = new NewGame().insert(stepOne.getNewGame());
		dbftGames = stepOne.get();
		if(stepTwo != null)	stepTwo.setColumns(dbftGames);
		int id = 0;
		for (int TGID: stepOne.getSelectedTypeGameIds()) {
			DBFilterGames filterGame = new DBFilterGames();
			filterGame.NGID = NGID;
			filterGame.TGID = TGID;
			int FGID = new FilterGame().insert(filterGame);
			dbftGames.get(id).FGID = FGID;
			if(stepTwo != null) new FilterMembers().insert(stepTwo.getFilterMembers(FGID, dbftGames.get(id).TGName));
			id++;
		}
		return NGID;
	}
	class CheckBoxRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JCheckBox checkBox = new JCheckBox();
			if(isSelected) checkBox.setSelected(true);
			else checkBox.setSelected(false);
			return new JCheckBox();
		}
	}
	private boolean isEmpty() {
		boolean flag = true;
		if (!ValidationComponents.isEmpty(stepOne.txtNameGame)) flag = false;
		if (!ValidationComponents.isEmpty(stepOne.txtVenue)) flag = false;
		if (!ValidationComponents.isEmpty(stepOne.dcDateBegin)) flag = false;
		if (!ValidationComponents.isEmpty(stepOne.dcDateEnd)) flag = false;
		return flag;
	}
	private boolean cheking() {
		if (!isEmpty()) JOptionPane.showMessageDialog(null, "Please filing fields");
		else {
			if (!stepOne.isEmptyRow()) JOptionPane.showMessageDialog(null, "Please add min one type game");
			else if (!stepOne.isSelectedCell()) JOptionPane.showMessageDialog(null, "Please select min one type game");
			else return true;
		}
		return false;
	}
	@Override
	public void transferAdd(Object data) {
		if(data instanceof DBTypeGames) stepOne.addRow((DBTypeGames)data);
		else stepTwo.addRow((DBPersonExtend)data);
	}
	@Override
	public void transferUpdate(Object data) {
		if(data instanceof DBTypeGames) stepOne.updateRow((DBTypeGames)data);
		else stepTwo.updateRow((DBPersonExtend)data);
	}
	@Override
	public void transferRemove(int id) {
		stepOne.removeRow(id);
	}
}
