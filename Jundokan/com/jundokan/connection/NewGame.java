package jundokan.connection;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import jundokan.model.DBNewGame;

public class NewGame {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;
	
	public NewGame() {
		connection = Config.getConnection();
	}
	public ArrayList<DBNewGame> select() {
		ArrayList<DBNewGame> game = new ArrayList<DBNewGame>();

		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select * from NewGames;");
			while (resSet.next()) {
				DBNewGame newGame = new DBNewGame();
				newGame.NGName = resSet.getString("NGName");
				newGame.NGCity = resSet.getString("NGCity");
				newGame.NGDBegin = resSet.getDate("NGDBegin");
				newGame.NGDEnd = resSet.getDate("NGDEnd");
				newGame.NGStatus = resSet.getBoolean("NGStatus");
				newGame.NGID = resSet.getInt("NGID");
				game.add(newGame);
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
		return game;
	}
	public boolean delete (int NewGameID){
		try {
			prest = connection.prepareStatement("delete from NewGames where NGID = '"+NewGameID+"';");
			prest.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return true;
	}
	
	public void update(DBNewGame newGames){
		try{
			prest = connection.prepareStatement("update NewGames set NGName = '"+newGames.NGName+"'," +
					"NGStatus = '"+newGames.NGStatus+"' , NGDBegin = '"+newGames.NGDBegin+"', " +
							"NGDEnd = '"+newGames.NGDEnd+"' , NGCity = '"+newGames.NGCity+"'   where NGID = '"+newGames.NGID+"';");
			prest.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}
	public void update(int NGID, boolean NGStatus){
		try{
			prest = connection.prepareStatement("update NewGames set NGStatus = ? where NGID = '"+NGID+"';");
			prest.setBoolean(1, NGStatus);
			prest.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}

/*	public int insert(String degree) {
		int id = -1;
		try {
			prest = connection.prepareStatement("insert into Degrees(Degree)"
					+ "VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			prest.setString(1, degree);
			prest.executeUpdate();
			resSet = prest.getGeneratedKeys();
			if (resSet.next()) {
				id = resSet.getInt(1);
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
				resSet.close(); // close ResultSet to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return id;
	}*/
	public int insert(DBNewGame newGame) {
		int id = -1;
		try {
			prest = connection.prepareStatement("insert into NewGames(NGName, NGCity, NGStatus, NGDBegin, NGDEnd)"
					+ "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			prest.setString(1, newGame.NGName);
			prest.setString(2, newGame.NGCity);
			prest.setBoolean(3, newGame.NGStatus);
			prest.setDate(4, newGame.NGDBegin);
			prest.setDate(5, newGame.NGDEnd);
			prest.executeUpdate();
			resSet = prest.getGeneratedKeys();
			if (resSet.next()) {
				id = resSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
				resSet.close(); // close ResultSet to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return id;
	}
}
