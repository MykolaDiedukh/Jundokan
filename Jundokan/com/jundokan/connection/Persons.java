package jundokan.connection;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import jundokan.model.DBDocumentParents;
import jundokan.model.DBPersons;
import jundokan.model.DBTypeGames;

public class Persons {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;

	public Persons() {		
		try {
			connection = Config.getConnection();
			updateAgeSQLite();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void updateAgeMysql() throws SQLException {
		prest = connection.prepareStatement("update Persons set PersAge = (year(curdate()) - year(PersDate) - (date_format(curdate(), '%m%d') < date_format(PersDate, '%m%d')));");
		prest.executeUpdate();
		prest.close();
	}
	private void updateAgeSQLite() throws SQLException {
		prest = connection.prepareStatement("update Persons set PersAge = (strftime('%Y', 'now') - strftime('%Y', PersDate) - (strftime('%m-%d', 'now') < strftime('%m-%d', PersDate)));");
		prest.executeUpdate();
		prest.close();
	}
	public ArrayList<DBPersons> selectType(int FGID) {
		ArrayList<DBPersons> persons = new ArrayList<DBPersons>();

		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("Select Persons.PersID,PersFN,PersLN,PersMN,PersAge,PersDate,PersSex,PersWeight,PersPhoto,DegreeID,OrgID from Persons, FilterMembers where FilterMembers.FGID = '"+FGID+"' and FMStatus = 1 and  Persons.PersID = FilterMembers.PersID;");
			while (resSet.next()) {
				DBPersons person = new DBPersons();
					person.PersID = resSet.getInt("PersID");
					person.PersFN = resSet.getString("PersFN");
					person.PersLN = resSet.getString("PersLN");
					person.PersMN = resSet.getString("PersMN");
					person.PersAge = resSet.getInt("PersAge");
					person.PersDate = Date.valueOf(resSet.getString("PersDate"));
					person.PersSex = resSet.getBoolean("PersSex");
					person.PersWeight = resSet.getInt("PersWeight");
					person.PersPhoto = resSet.getString("PersPhoto") == null? "" : resSet.getString("PersPhoto");
					person.DegreeID = resSet.getInt("DegreeID");
					person.OrgID = resSet.getInt("OrgID");
					persons.add(person);
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
		return persons;
	}
	
	public DBPersons select(int PersID) {
		DBPersons person = new DBPersons();
		try {
			statement = connection.createStatement();
			resSet = statement
					.executeQuery("select * from Persons where PersID ="
							+ PersID + ";");
			if (resSet.next()) {
				person.PersID = resSet.getInt("PersID");
				person.PersFN = resSet.getString("PersFN");
				person.PersLN = resSet.getString("PersLN");
				person.PersMN = resSet.getString("PersMN");
				person.PersAge = resSet.getInt("PersAge");
				person.PersDate = Date.valueOf(resSet.getString("PersDate"));
				person.PersSex = resSet.getBoolean("PersSex");
				person.PersWeight = resSet.getInt("PersWeight");
				person.PersPhoto = resSet.getString("PersPhoto") == null? "" : resSet.getString("PersPhoto");
				person.DegreeID = resSet.getInt("DegreeID");
				person.OrgID = resSet.getInt("OrgID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				resSet.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				// JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return person;
	}

	public ArrayList<DBPersons> select() {
		ArrayList<DBPersons> persons = new ArrayList<DBPersons>();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select * from Persons;");
			while (resSet.next()) {
				DBPersons person = new DBPersons();
				person.PersID = resSet.getInt("PersID");
				person.PersFN = resSet.getString("PersFN");
				person.PersLN = resSet.getString("PersLN");
				person.PersMN = resSet.getString("PersMN");
				person.PersAge = resSet.getInt("PersAge");
				person.PersDate = Date.valueOf(resSet.getString("PersDate"));
				person.PersSex = resSet.getBoolean("PersSex");
				person.PersWeight = resSet.getInt("PersWeight");
				person.PersPhoto = resSet.getString("PersPhoto") == null? "" : resSet.getString("PersPhoto");
				person.DegreeID = resSet.getInt("DegreeID");
				person.OrgID = resSet.getInt("OrgID");
				persons.add(person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				resSet.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				// JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return persons;
	}
	public ArrayList<DBPersons> selects(int FGID) {
		ArrayList<DBPersons> persons = new ArrayList<DBPersons>();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select * from persons where Persons.PersID NOT IN(select filtermembers.persID from filtermembers where filtermembers.FGID = '"+FGID+"');");
			while (resSet.next()) {
				DBPersons person = new DBPersons();
				person.PersID = resSet.getInt("PersID");
				person.PersFN = resSet.getString("PersFN");
				person.PersLN = resSet.getString("PersLN");
				person.PersMN = resSet.getString("PersMN");
				person.PersAge = resSet.getInt("PersAge");
				person.PersDate = Date.valueOf(resSet.getString("PersDate"));
				person.PersSex = resSet.getBoolean("PersSex");
				person.PersWeight = resSet.getInt("PersWeight");
				person.PersPhoto = resSet.getString("PersPhoto") == null? "" : resSet.getString("PersPhoto");
				person.DegreeID = resSet.getInt("DegreeID");
				person.OrgID = resSet.getInt("OrgID");
				persons.add(person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				resSet.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				// JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
		return persons;
	}
	public void update(DBPersons p) {
		try {
			prest = connection.prepareStatement("update persons set PersFN = ?, PersLN = ?, PersMN = ?," +
							" PersSex = ?, PersDate = ?, PersWeight = ?, PersAge = ?, PersPhoto = ?," +
							" DegreeID = ?, OrgID = ? where PersID = "+ p.PersID + ";");
			prest.setString(1, p.PersFN);
			prest.setString(2, p.PersLN);
			prest.setString(3, p.PersMN);
			prest.setBoolean(4, p.PersSex);
			prest.setString(5, p.PersDate.toString());
			prest.setInt(6, p.PersWeight);
			prest.setInt(7, p.PersAge);
			prest.setString(8, p.PersPhoto);
			prest.setInt(9, p.DegreeID);
			prest.setInt(10, p.OrgID);
			prest.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close(); // close Connection to server
				prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				// JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}

	public int insert(DBPersons p) {
		int ID = -1;
		try {

			// Insert information of person into table persons
			prest = connection
					.prepareStatement(
							"insert into Persons(PersFN, PersLN, PersMN, PersSex, PersDate, PersWeight, PersAge, PersPhoto, DegreeID, OrgID)"
									+ "VALUES (?,?,?,?,?,?,?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);

			prest.setString(1, p.PersFN);
			prest.setString(2, p.PersLN);
			prest.setString(3, p.PersMN);
			prest.setBoolean(4, p.PersSex);
			prest.setString(5, p.PersDate.toString());
			prest.setInt(6, p.PersWeight);
			prest.setInt(7, p.PersAge);
			prest.setString(8, p.PersPhoto);
			prest.setInt(9, p.DegreeID);
			prest.setInt(10, p.OrgID);
			prest.executeUpdate();
			resSet = prest.getGeneratedKeys();
			if (resSet.next()) {
				ID = resSet.getInt(1);
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
		return ID;
	}
}
