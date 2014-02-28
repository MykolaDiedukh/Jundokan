package jundokan.connection;

import java.sql.*;
import java.util.ArrayList;
import jundokan.model.DBFTGames;
/*
 * F = Filter Games
 * T = Type Games
 * */
public class FTGames {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;
	private static int KATA = 1;
	private static int KYMITE = 0;

	public FTGames() {
		connection = Config.getConnection();
	}
	public ArrayList<DBFTGames> select(int NGID) {
		ArrayList<DBFTGames> dbftGames = new ArrayList<DBFTGames>();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("SELECT FGID, TGPattern, TGName FROM filtergames, typegames where filtergames.NGID = '"+NGID+"' and typegames.TGID = filtergames.TGID;");
			while (resSet.next()) {
				DBFTGames dbftGame = new DBFTGames();
				dbftGame.FGID = resSet.getInt("FGID");
				dbftGame.TGPattern = resSet.getInt("TGPattern") == KATA ? true : false;// true = kata, false = irykomi
				dbftGame.TGName = resSet.getString("TGName");
				dbftGames.add(dbftGame);
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
		return dbftGames;
	}
}
