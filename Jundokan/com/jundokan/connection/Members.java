package jundokan.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jundokan.model.DBMembers;
import jundokan.model.DBNewGame;

public class Members {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;
	public Members() {
		connection = Config.getConnection();
	}
	public ArrayList<DBMembers> select(int ProtID) {
		ArrayList<DBMembers> members = new ArrayList<DBMembers>();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select FMID from Members where ProtID = '"+ProtID+"';");
			while (resSet.next()) {
				DBMembers member = new DBMembers();
				member.FMID = resSet.getInt("FMID");
				members.add(member);
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
		return members;
	}
	public void delete (int ProtID){
		try {
			prest = connection.prepareStatement("delete from Members where ProtID = '"+ProtID+"';");
			prest.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection != null)connection.close(); // close Connection to server
				if (prest != null)prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}
}
