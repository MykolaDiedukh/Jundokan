package jundokan.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import jundokan.model.DBFilterGames;

public class FilterGame {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;

	public FilterGame() {
		connection = Config.getConnection();
	}
	public ArrayList<DBFilterGames> select(int NGID) {
		ArrayList<DBFilterGames> filterGames = new ArrayList<DBFilterGames>();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select FGID, TGID from FilterGames where NGID = '"+NGID+"';");
			while (resSet.next()) {
				DBFilterGames filterGame = new DBFilterGames();
				filterGame.FGID = resSet.getInt("FGID");
				filterGame.TGID = resSet.getInt("TGID");
				filterGames.add(filterGame);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				statement.close(); // close Statement to server
				resSet.close(); // close ResultSet to server
			} catch (SQLException e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return filterGames;
	}
	public int insert(DBFilterGames filterGame) {
		int FGID = -1;
		try {
			prest = connection.prepareStatement("insert into FilterGames(NGID, TGID)" + "VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
			prest.setInt(1, filterGame.NGID);
			prest.setInt(2, filterGame.TGID);
			prest.executeUpdate();
			resSet = prest.getGeneratedKeys();
			if (resSet.next()) {
				FGID = resSet.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
				resSet.close(); // close ResultSet to server
			} catch (Exception e) {
				e.printStackTrace();
				// JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return FGID;
	}
}
