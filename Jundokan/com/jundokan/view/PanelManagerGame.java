package jundokan.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.BorderUIResource;

import jundokan.connection.FTGames;
import jundokan.connection.FilterMembers;
import jundokan.connection.Members;
import jundokan.connection.Protocols;
import jundokan.model.DBFTGames;
import jundokan.model.DBProtocols;
import jundokan.view.templates.CollectionProtocolOne;
import jundokan.view.templates.ConvertToPdf;
import jundokan.view.templates.KataProtocol;
import jundokan.view.templates.ProtocolOne;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.VerticalLayout;

import com.itextpdf.text.log.SysoLogger;

public class PanelManagerGame extends JPanel implements ITransfer, ITransferData{
	private JSplitPane splitPane;
	private JTabbedPane tabbedPane;
	private JXTaskPaneContainer container;
	private VerticalLayout verticalLayout;
	private ArrayList<DBFTGames> dbftGames;
	private ArrayList<JList> listGrids;
	private ArrayList<ArrayList<Integer>> idProtocols;
	private HashMap<String, ITransferData> listeners;
	private ResourceBundle bundle;
	//Open old games
	public PanelManagerGame (int NGID) {		
		initComponents();
		initTaskContainer();
		this.dbftGames = new FTGames().select(NGID);
		initTaskPanesOldGame();
	}	
	//Create new game
	public PanelManagerGame(ArrayList<DBFTGames> dbftGames) {
		initComponents();
		initTaskContainer();
		this.dbftGames = dbftGames;
		initTaskPanesCreateGame();
	}
	private boolean isCloseble(){
		return splitPane.getDividerLocation() > 1? true : false;
	}
	private void initTaskPanesOldGame() {
		idProtocols = new ArrayList<ArrayList<Integer>>();
		listGrids = new ArrayList<JList>();
		int index = 0;
		for (DBFTGames dbftGame : dbftGames) {
			UIManager.put("TaskPane.titleBackgroundGradientStart", SystemColor.control);
			UIManager.put("TaskPane.titleBackgroundGradientEnd", SystemColor.controlHighlight);
			JXTaskPane taskPane = new JXTaskPane();
			taskPane.setTitle(dbftGame.TGName);
			((JComponent)taskPane.getContentPane()).setBorder(new BorderUIResource(BorderFactory.createEmptyBorder(0, 14, 0, 0)));
			
			
			JList listGrid = new JList();
			listGrid.addMouseListener(new MA(index){
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2){
						String name = String.valueOf(idProtocols.get(this.index).get(listGrids.get(this.index).getSelectedIndex()));
						if(!isOpenTab(name)){
							String title = (String)listGrids.get(this.index).getSelectedValue();
							tabbedPane.addTab(title, !dbftGames.get(index).TGPattern? new ProtocolOne(new Protocols().select(idProtocols.get(this.index).get(listGrids.get(this.index).getSelectedIndex()))) :
							new ConvertToPdf(new Protocols().select(idProtocols.get(this.index).get(listGrids.get(this.index).getSelectedIndex()))));
							tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane, null));
							tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).setName(name);
							tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
						}
					}
				}
			});
			DefaultListModel listModel = new DefaultListModel();
			ArrayList<Integer> idProtocol = new ArrayList<Integer>();			
			for(DBProtocols protocol: new Protocols().selects(dbftGame.FGID)){
				listModel.addElement(protocol.ProtName);
				idProtocol.add(protocol.ProtID);
			}
			listGrid.setModel(listModel);
			idProtocols.add(idProtocol);
			listGrids.add(listGrid);
			listGrid.setComponentPopupMenu(getPopupMenuList(index));
			taskPane.add(listGrid);
			taskPane.setComponentPopupMenu(getPopupMenuTaskPane(index));
			taskPane.getContentPane().setBackground(listGrid.getBackground());
			container.setBackground(SystemColor.control);
			this.container.add(taskPane);
			this.container.setOpaque(false);
			index++;
		}
	}
	private void initTaskPanesCreateGame() {
		idProtocols = new ArrayList<ArrayList<Integer>>();
		listGrids = new ArrayList<JList>();
		int index = 0;
		for (DBFTGames dbftGame : dbftGames) {
			JXTaskPane taskPane = new JXTaskPane();
			taskPane.setTitle(dbftGame.TGName);
			((JComponent)taskPane.getContentPane()).setBorder(new BorderUIResource(BorderFactory.createEmptyBorder(0, 14, 0, 0)));
			JList listGrid = new JList();
			idProtocols.add(new ArrayList<Integer>());
			listGrid.addMouseListener(new MA(index){
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2){
						String name = String.valueOf(idProtocols.get(this.index).get(listGrids.get(this.index).getSelectedIndex()));
						if(!isOpenTab(name)){
							String title = (String)listGrids.get(this.index).getSelectedValue();
							tabbedPane.addTab(title, !dbftGames.get(index).TGPattern? new ProtocolOne(new Protocols().select(idProtocols.get(this.index).get(listGrids.get(this.index).getSelectedIndex()))):
							new ConvertToPdf(new Protocols().select(idProtocols.get(this.index).get(listGrids.get(this.index).getSelectedIndex()))));
							tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane, null));
							tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).setName(name);
							tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
						}
					}
				}
			});
			listGrid.setModel(new DefaultListModel());
			listGrids.add(listGrid);
			listGrid.setComponentPopupMenu(getPopupMenuList(index));
			taskPane.add(listGrid);
			taskPane.setComponentPopupMenu(getPopupMenuTaskPane(index));
			taskPane.getContentPane().setBackground(listGrid.getBackground());
			container.setBackground(SystemColor.control);
			this.container.add(taskPane);
			this.container.setOpaque(false);
			index++;
		}
	}
	private boolean isOpenTab(String name) {
		for (int i = 0; i < tabbedPane.getTabCount(); i++)
			if(tabbedPane.getTabComponentAt(i).getName() != null)
			if(tabbedPane.getTabComponentAt(i).getName().equals(name)) {
				tabbedPane.setSelectedIndex(i);
				return true;
			}
		return false;
	}
	private class MA extends MouseAdapter {
		protected final int index;
		public MA(int index){
			this.index = index;
		}
	}
	public void initTaskContainer() {
		container = new JXTaskPaneContainer();
		verticalLayout = new VerticalLayout();
		verticalLayout.setGap(0);
		container.setLayout(verticalLayout);
		container.setBorder(BorderFactory.createEmptyBorder());
		container.setOpaque(false);		
		JScrollPane scroll = new JScrollPane(container);
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
		scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(10, 10));
		splitPane.setLeftComponent(scroll);		
	}	
	private void initComponents(){
		//add(Box.createRigidArea(new Dimension(0,0)));
		listeners = new HashMap<String, ITransferData>();
		Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
		Locale.setDefault(new Locale(prop.getKey("locale")));
		try {
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("PanelManagerGame", Locale.getDefault(), new URLClassLoader(new URL [] {path.toUri().toURL()}));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setLayout(new BorderLayout(0, 0));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(SystemColor.control);
		buttonPanel.setBorder(BorderFactory.createLineBorder(SystemColor.control));
		//buttonPanel.setSize(20, 10);
		buttonPanel.setPreferredSize(new Dimension(18, 9));
		add(buttonPanel, BorderLayout.WEST);
		buttonPanel.setLayout(null);
		JButton btnManagerGame = new JButton();//new VerticalButton(bundle.getString("btnMangerGame"), false);
		btnManagerGame.setBackground(SystemColor.control);
		btnManagerGame.setBorder(BorderFactory.createLineBorder(SystemColor.control));
		TextIcon ti = new TextIcon(btnManagerGame, bundle.getString("btnMangerGame"));
        RotatedIcon ri = new RotatedIcon(ti, RotatedIcon.Rotate.UP);
        btnManagerGame.setIcon(ri);
        btnManagerGame.setFocusable(false);
		btnManagerGame.setMargin(new Insets(0, 0, 0, 0));
		btnManagerGame.setBounds(-2, -2, 22, 150);
		btnManagerGame.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isCloseble()) splitPane.setDividerLocation(0);
				else splitPane.setDividerLocation(300);				
			}
		});
		buttonPanel.add(btnManagerGame, BorderLayout.NORTH);		
		splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setDividerLocation(300);
		splitPane.setDividerSize(1);
		splitPane.setContinuousLayout(true);
		add(splitPane, BorderLayout.CENTER);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);		
		splitPane.setRightComponent(tabbedPane);
	}
	private JPopupMenu getPopupMenuTaskPane(final int index) {
		JPopupMenu popupMenu = new JPopupMenu();
		Action printProtocol = new AbstractAction(bundle.getString("print")){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(dbftGames.get(index).TGPattern) 
						PDDocument.load(new ByteArrayInputStream(new KataProtocol(new Protocols().selectAllGrids(dbftGames.get(index).FGID)).getByteArrayPDF())).print();
					else 
						PDDocument.load(new ByteArrayInputStream(new CollectionProtocolOne(new Protocols().selectAllGrids(dbftGames.get(index).FGID)).getByteArrayPDF())).print();
				} catch (PrinterException | IOException ex) {
					ex.printStackTrace();
				}
			}
		};
		Action saveAllGrid = new AbstractAction(bundle.getString("saveAllGrid")) {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
				int chooser = fileChooser.showSaveDialog(PanelManagerGame.this);
				if(!(chooser == JFileChooser.CANCEL_OPTION))
				try {
					String path = Paths.get(fileChooser.getCurrentDirectory().getAbsolutePath(), String.format("%s.pdf",fileChooser.getSelectedFile().getName())).toString();
					FileOutputStream fos = new FileOutputStream(path);
					if(dbftGames.get(index).TGPattern) 
						fos.write(new KataProtocol(new Protocols().selectAllGrids(dbftGames.get(index).FGID)).getByteArrayPDF());
					else 
						fos.write(new CollectionProtocolOne(new Protocols().selectAllGrids(dbftGames.get(index).FGID)).getByteArrayPDF());
					fos.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		Action createProtocol = new AbstractAction(bundle.getString("createProtocol")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel list = (DefaultListModel)listGrids.get(index).getModel();
				String element = ((JXTaskPane)container.getComponent(index)).getTitle() + " " + list.getSize();
				if(!isOpenTab(element)){				
	                PanelNewGrid newGrid = new PanelNewGrid(dbftGames.get(index).FGID, dbftGames.get(index).TGPattern, tabbedPane, index, list);
	                listeners.put(element,newGrid);
	                newGrid.setHashMap(listeners);
	                tabbedPane.addTab(element, newGrid);
	                tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane, listeners));
	                tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).setName(element);
	                tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
				}
			}
		};
		Action unsubscribePersons = new AbstractAction(bundle.getString("unsubscribePersons")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				String element = String.format("%s: %s",bundle.getString("unsubscribePersons"), 
						((JXTaskPane)container.getComponent(index)).getTitle());
				if(!isOpenTab(element)){	
				PanelSubscribeAndUnsubscribe unsubscribe = new PanelSubscribeAndUnsubscribe(dbftGames.get(index).FGID, tabbedPane, true);
				listeners.put(element, unsubscribe);
				unsubscribe.setHashMap(listeners);				
				tabbedPane.addTab(element, unsubscribe);
				tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane, listeners));
				tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).setName(element);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
				}
			}
		};
		Action subscribePersons = new AbstractAction(bundle.getString("subscribePersons")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				String element = String.format("%s: %s",bundle.getString("subscribePersons"), 
						((JXTaskPane)container.getComponent(index)).getTitle());
				if(!isOpenTab(element)){
				PanelSubscribeAndUnsubscribe subscribe = new PanelSubscribeAndUnsubscribe(dbftGames.get(index).FGID, tabbedPane);
				listeners.put(element, subscribe);
				subscribe.setHashMap(listeners);	
				tabbedPane.addTab(element, subscribe);
				tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane, listeners));
				tabbedPane.getTabComponentAt(tabbedPane.getTabCount()-1).setName(element);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
				}
			}
		};
		popupMenu.add(createProtocol);
		popupMenu.add(subscribePersons);
		popupMenu.add(unsubscribePersons);
		popupMenu.add(saveAllGrid);
		popupMenu.add(printProtocol);
		return popupMenu;
	}
	private JPopupMenu getPopupMenuList(final int index) {
		JPopupMenu popupMenu = new JPopupMenu();
		Action deleteProtocol = new AbstractAction(bundle.getString("deleteProtocol")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultListModel list3 = (DefaultListModel)listGrids.get(index).getModel();
				if(listGrids.get(index).getSelectedIndex()>=0){
					int selectIndex = listGrids.get(index).getSelectedIndex();
					list3.remove(selectIndex);
					int protID = idProtocols.get(index).get(selectIndex);
					for (int i = 0; i < tabbedPane.getTabCount(); i++)
						if(tabbedPane.getTabComponentAt(i).getName().equals(String.valueOf(protID))) {
							tabbedPane.removeTabAt(i);
						}					
					Members members = new Members();
					new FilterMembers().update(members.select(protID));
					new Protocols().delete(protID);
					idProtocols.get(index).remove(selectIndex);
				}
			}
		};
		Action renameProtocol = new AbstractAction(bundle.getString("renameProtocol")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listGrids.get(index).getSelectedIndex() != -1) {
					final JDialog dialog = new JDialog();
					dialog.setBounds(100, 100, 271, 104);
					dialog.setResizable(false);
					dialog.setLocationRelativeTo(PanelManagerGame.this);
					dialog.setModal(true);
					dialog.setAlwaysOnTop(true);
					dialog.setLayout(null);
					dialog.setTitle(bundle.getString("title"));
					final JTextField txtRename = new JTextField((String) listGrids.get(index).getSelectedValue());
					txtRename.setBounds(10, 11, 246, 20);
					TextPrompt prompt = new TextPrompt(bundle.getString("prompt"), txtRename);
					prompt.changeAlpha(0.5f);
					dialog.add(txtRename, BorderLayout.CENTER);
					JButton btnCancel = new JButton(bundle.getString("btnCancel"));
					btnCancel.setBounds(10, 42, 124, 23);
					btnCancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							dialog.dispose();
						}
					});
					JButton btnRename = new JButton(bundle.getString("btnRename"));
					btnRename.setBounds(130, 42, 125, 23);
					btnRename.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(!txtRename.getText().equals("")){
								int protID = idProtocols.get(index).get(listGrids.get(index).getSelectedIndex());
								new Protocols().update(protID,txtRename.getText());
								((DefaultListModel) listGrids.get(index).getModel()).setElementAt(txtRename.getText(), listGrids.get(index).getSelectedIndex());
								for (int i = 0; i < tabbedPane.getTabCount(); i++)
									if(tabbedPane.getTabComponentAt(i).getName().equals(String.valueOf(protID))) {
										tabbedPane.setTitleAt(i, txtRename.getText());
									}
								dialog.dispose();
							} else JOptionPane.showMessageDialog(dialog, bundle.getString("message"));
						}
					});
					dialog.add(btnCancel);
					dialog.add(btnRename);
					dialog.setVisible(true);
				}
			}
		};
		popupMenu.add(deleteProtocol);
		popupMenu.add(renameProtocol);
		return popupMenu;
	}
	@Override
	public void transfer(int index, int ProtID) {
		idProtocols.get(index).add(ProtID);		
	}
	@Override
	public void transferAdd(Object data) {
		Iterator iter = (Iterator) listeners.entrySet().iterator();
		while(iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    ((ITransferData)entry.getValue()).transferAdd(data);
		}
	}
	@Override
	public void transferUpdate(Object data) {
		Iterator iter = (Iterator) listeners.entrySet().iterator();
		while(iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    ((ITransferData)entry.getValue()).transferUpdate(data);
		}
	}
	@Override
	public void transferRemove(int id) {}
}