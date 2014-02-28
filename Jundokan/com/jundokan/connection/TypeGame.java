package jundokan.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import jundokan.model.DBDegrees;
import jundokan.model.DBTypeGames;

public class TypeGame {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;
	private static int KATA = 1;
	private static int KYMITE = 0;
	
	public TypeGame() {
		connection = Config.getConnection();
	}
	public void dalete (int id) throws Exception{
		try {
			prest = connection.prepareStatement("delete from TypeGames where TGID = '"+id+"';");
			prest.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
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
	
	public void update(DBTypeGames tg){
		try{
			prest = connection.prepareStatement("update TypeGames set TGName = '"+tg.TGName+"'  where TGID = '"+tg.TGID+"';");
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
	public int insert(DBTypeGames typeGame){//String TGName, boolean TGPattern) {
		int id = -1;
		try {
			prest = connection.prepareStatement("insert into TypeGames(TGName, TGPattern)"
					+ "VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
			prest.setString(1, typeGame.TGName);
			prest.setInt(2, typeGame.TGPattern ? KATA : KYMITE);
			prest.executeUpdate();
			resSet = prest.getGeneratedKeys();
			if (resSet.next()) {
				id = resSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	}
	
	public String select(int TGID) {
		String TGName = null;
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select TGName from TypeGames where TGID = '"+TGID+"';");
			resSet.next();
			TGName = resSet.getString("TGName");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				resSet.close(); // close ResultSet to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return TGName;
	}
	
	public ArrayList<DBTypeGames> select() {
		ArrayList<DBTypeGames> typeGames = new ArrayList<DBTypeGames>();

		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select * from TypeGames;");
			while (resSet.next()) {
				DBTypeGames typeGame = new DBTypeGames();
				typeGame.TGID = resSet.getInt("TGID");
				typeGame.TGName = resSet.getString("TGName");
				typeGame.TGPattern = resSet.getInt("TGPattern") == KATA ? true : false;// true = kata, false = irykomi
				typeGames.add(typeGame);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, e.getMessage());
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
		return typeGames;
	}
}
