package jundokan.connection;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import jundokan.model.DBOrganization;
import jundokan.model.DBTypeGames;

public class Organization {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;

	public Organization() {
		connection = Config.getConnection();
	}
	public void dalete (int id){
		try {
			prest = connection.prepareStatement("delete from Organization where OrgID = '"+id+"';");
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
	
	public void update(DBOrganization org){
		try{
			prest = connection.prepareStatement("update Organization set OrgName = '"+org.OrgName+"'  where OrgID = '"+org.OrgID+"';");
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
	public int insert(String org) {
		int id = -1;
		try {
			prest = connection.prepareStatement("insert into Organization(OrgName)" + "VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			prest.setString(1, org);
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
				JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return id;
	}

	public ArrayList<DBOrganization> select() {
		ArrayList<DBOrganization> orgnization = new ArrayList<DBOrganization>();

		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select * from Organization;");
			while (resSet.next()) {
				DBOrganization org = new DBOrganization();
				org.OrgID = resSet.getInt("OrgID");
				org.OrgName = resSet.getString("OrgName");
				orgnization.add(org);
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
				JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return orgnization;
	}
	
	public String select(int OrgID) {
		String OrgName = "";
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select OrgName from Organization where OrgID ='" + OrgID + "';");
			if (resSet.next()) {
				OrgName = resSet.getString("OrgName");
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
		return OrgName;
	}
}