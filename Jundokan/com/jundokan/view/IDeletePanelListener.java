package jundokan.view;
import java.util.ArrayList;
import javax.swing.JPanel;
import jundokan.model.DBFTGames;

public interface IDeletePanelListener {
	public void closePanel(JPanel panel, ArrayList<DBFTGames> dbftGames);
}
