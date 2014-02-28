package jundokan.connection;

import java.sql.*;
import java.util.ArrayList;

import jundokan.model.DBPFM;

/*
 * P = persons
 * FM = filtermemebrs
 * */
public class PFM {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;
	
	public PFM() {
		connection = Config.getConnection();
	}
	public ArrayList<DBPFM> select(int FGID) {
		ArrayList<DBPFM> persons = new ArrayList<DBPFM>();
		try {
			statement = connection.createStatement();
			resSet = statement.executeQuery("select Persons.PersID,PersFN,PersLN,PersMN,PersAge,PersDate,PersSex,PersWeight,PersPhoto,DegreeID,OrgID,FMStatus,FMID from persons, filtermembers where filtermembers.FGID = '"+FGID+"' and persons.PersID = filtermembers.PersID;");
			while (resSet.next()) {
				DBPFM person = new DBPFM();
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
				person.FMStatus = resSet.getInt("FMStatus") == 0 ? false : true;
				person.FGID = FGID;
				person.FMID = resSet.getInt("FMID");
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
}
