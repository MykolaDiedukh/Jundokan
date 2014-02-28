package jundokan.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jundokan.model.DBFTGames;

public class GUIMain extends JFrame implements IDeletePanelListener, ActionListener{
	private PanelGame panelGame;
	private MenuBar menuBar;
	private ResourceBundle bundle;
	public GUIMain() {	
		panelGame = new PanelGame();
		panelGame.setDeletePanelListaner(this);
		add(panelGame);
		init();
		menuBar.setPanelGame(panelGame);
		setTitle(bundle.getString("title1"));
	}
	private void init() {
		try {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			menuBar = new MenuBar();
			menuBar.setCloseGameItemListener(this);
			setJMenuBar(menuBar);
			Property prop = new Property(Paths.get(System.getProperty("user.dir"),"jundokan.ini").toString());
			Locale.setDefault(new Locale(prop.getKey("locale")));
			Path path = Paths.get(System.getProperty("user.dir"),"/lang/",Locale.getDefault().toString());
			bundle = ResourceBundle.getBundle("GUIMain", Locale.getDefault(), new URLClassLoader(new URL[]{path.toUri().toURL()}));
			DimensionFrame dim = new DimensionFrame(getClass().getSimpleName(), 358, 300);
			addWindowListener(dim.getWindowAdapter(this));
			setBounds(dim.getBounds());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public GUIMain(int NGID) {
		PanelManagerGame panelManagerGame = new PanelManagerGame(NGID);
		add(panelManagerGame);
		init();
		menuBar.setPanelManagerGame(panelManagerGame);
		setTitle(bundle.getString("title2"));
	}

	@Override
	public void closePanel(JPanel panel, ArrayList<DBFTGames> dbftGames) {
		this.remove(panel);
		PanelManagerGame panelManagerGame = new PanelManagerGame(dbftGames);
		menuBar.setPanelManagerGame(panelManagerGame);
		this.add(panelManagerGame);
		this.validate();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		new GUIQuickStart().setVisible(true);
		dispose();		
	}

}
