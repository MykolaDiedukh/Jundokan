package jundokan.connection;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import jundokan.model.DBDegrees;

public class Degrees {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;

	public Degrees() {
		connection = Config.getConnection();
	}
	public boolean delete (int DegreeID){
		try {
			prest = connection.prepareStatement("delete from Degrees where DegreeID = '"+DegreeID+"';");
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
	
	public void update(DBDegrees degree){
		try{
			prest = connection.prepareStatement("update Degrees set Degree = '"+degree.Degree+"'  where DegreeID = '"+degree.DegreeID+"';");
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

	public int insert(String degree) {
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
	}

	public ArrayList<DBDegrees> select() {
		ArrayList<DBDegrees> degrees = new ArrayList<DBDegrees>();

		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select * from Degrees;");
			while (resSet.next()) {
				DBDegrees degree = new DBDegrees();
				degree.DegreeID = resSet.getInt("DegreeID");
				degree.Degree = resSet.getString("Degree");
				degrees.add(degree);
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
		return degrees;
	}
	public String select(int degreeID) {
		String degree = "";
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select Degree from Degrees where DegreeID ='" + degreeID + "';");
			if (resSet.next()) {
				degree = resSet.getString("Degree");
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
		return degree;
	}
}