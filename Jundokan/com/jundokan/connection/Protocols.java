package jundokan.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import jundokan.model.DBProtocols;
import jundokan.view.templates.DataProtocol;

public class Protocols {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;
	public Protocols() {
		connection = Config.getConnection();
	}
	public DBProtocols select(int ProtID) {
		DBProtocols protocol = new DBProtocols();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select * from Protocols where ProtID = '"+ProtID+"';");
			if(resSet.next()){
				protocol.ProtAge = resSet.getString("ProtAge");
				protocol.ProtCategory = resSet.getString("ProtCategory");
				protocol.ProtName = resSet.getString("ProtName");
				protocol.ProtSex = resSet.getBoolean("ProtSex");
				protocol.ProtData = resSet.getBytes("ProtData");
				protocol.ProtID = ProtID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close(); // close Connection to server
				resSet.close(); // close ResultSet to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return protocol;
	}
	public ArrayList<DBProtocols> selects(int FGID) {
		ArrayList<DBProtocols> dbProtocols = new ArrayList<DBProtocols>();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select * from Protocols where FGID = '"+FGID+"';");
			while(resSet.next()){
				DBProtocols protocol = new DBProtocols();
				protocol.ProtAge = resSet.getString("ProtAge");
				protocol.ProtCategory = resSet.getString("ProtCategory");
				protocol.ProtName = resSet.getString("ProtName");
				protocol.ProtSex = resSet.getBoolean("ProtSex");
				protocol.ProtData = resSet.getBytes("ProtData");
				protocol.ProtID = resSet.getInt("ProtID");
				dbProtocols.add(protocol);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close(); // close Connection to server
				resSet.close(); // close ResultSet to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return dbProtocols;
	}
	public ArrayList<HashMap<String, String>> selectGrids(int NGID, boolean pattern){
		ArrayList<HashMap<String, String>> grids = new ArrayList<HashMap<String, String>>();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("SELECT ProtData FROM FilterGames, TypeGames, Protocols "+
			"where FilterGames.NGID = '"+NGID+"' and FilterGames.TGID = TypeGames.TGID and TypeGames.TGPattern = '"+(pattern?1:0)+"' and FilterGames.FGID = Protocols.FGID;");
			while(resSet.next()){
				DataProtocol dp = new DataProtocol();
				dp.unSerializationDataProtocol(resSet.getBytes("ProtData"));
				grids.add(dp.getDataProtocol());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close(); // close Connection to server
				resSet.close(); // close ResultSet to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return grids;
	}
	public ArrayList<HashMap<String, String>> selectAllGrids(int FGID) {
		ArrayList<HashMap<String, String>> grids = new ArrayList<HashMap<String, String>>();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select * from Protocols where FGID = '"+FGID+"';");
			while(resSet.next()){
				DataProtocol dp = new DataProtocol();
				dp.unSerializationDataProtocol(resSet.getBytes("ProtData"));
				grids.add(dp.getDataProtocol());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close(); // close Connection to server
				resSet.close(); // close ResultSet to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return grids;
	}
	public int insert(DBProtocols protocol) {
		int id = -1;
		try {
			prest = connection.prepareStatement("insert into Protocols(ProtName, ProtData, ProtAge, ProtCategory, ProtSex, FGID)"
					+ "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			prest.setString(1, protocol.ProtName);
			prest.setBytes(2, protocol.ProtData);
			prest.setString(3, protocol.ProtAge);
			prest.setString(4, protocol.ProtCategory);
			prest.setInt(5, protocol.ProtSex ? 1 : 0);
			prest.setInt(6, protocol.FGID);
			prest.executeUpdate();
			resSet = prest.getGeneratedKeys();
			if (resSet.next()) {
				id = resSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return id;
	}
	public void update(DBProtocols protocol) {
		try {
			prest = connection.prepareStatement("update Protocols set ProtData = ?, ProtAge = ?, ProtCategory = ?, ProtSex = ?"
					+ " where ProtID = '"+protocol.ProtID+"'; ");
			prest.setBytes(1, protocol.ProtData);
			prest.setString(2, protocol.ProtAge);
			prest.setString(3, protocol.ProtCategory);
			prest.setBoolean(4, protocol.ProtSex);
			prest.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}
	public void update(int ProtID, String ProtName) {
		try {
			prest = connection.prepareStatement("update Protocols set ProtName = ?"
					+ " where ProtID = '"+ProtID+"'; ");
			prest.setString(1, ProtName);
			prest.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}
	public void delete (int ProtID){
		try {
			prest = connection.prepareStatement("delete from Protocols where ProtID = '"+ProtID+"';");
			prest.execute();
		} catch (Exception e) {
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
}
