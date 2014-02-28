package jundokan.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import jundokan.model.DBFilterMembers;
import jundokan.model.DBMembers;

public class FilterMembers {
	private Connection connection = null;
	private PreparedStatement prest = null;
	private ResultSet resSet = null;
	private Statement statement = null;
	private static int NOUSE = 1; 
	private static int ONUSE = 0; 
	
	public FilterMembers() {
		connection = Config.getConnection();
	}
	public void insert(ArrayList<DBFilterMembers> filterMembers) {
		try {
			for (DBFilterMembers filterMember : filterMembers) {
				prest = connection.prepareStatement("insert into FilterMembers(FMStatus, PersID, FGID)" + "VALUES (?,?,?)");
				prest.setInt(1, filterMember.FMStatus?1:0);
				prest.setInt(2, filterMember.PersID);
				prest.setInt(3, filterMember.FGID);
				prest.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				if(prest != null)prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}
	public void update(ArrayList<DBFilterMembers> filterMembers, int ProtID) {
		try {
			for (DBFilterMembers filterMember : filterMembers) {
				prest = connection.prepareStatement("update FilterMembers set FMStatus = ? where PersID = '"+filterMember.PersID+"' and FGID ='"+filterMember.FGID+"';");
				prest.setInt(1, filterMember.FMStatus ? NOUSE : ONUSE);
				prest.executeUpdate();
				prest = connection.prepareStatement("insert into Members (ProtID, FMID) values ('"+ProtID+
				"', (select FMID from FilterMembers  where PersID = '"+filterMember.PersID+"' and FGID ='"+filterMember.FGID+"'));");
				prest.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				if(prest != null)prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}
	public void update(ArrayList<DBMembers> members) {
		try {
			for (DBMembers member : members) {
				prest = connection.prepareStatement("update FilterMembers set FMStatus = ? where FMID = '"+member.FMID+"';");
				prest.setInt(1, NOUSE);
				prest.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				if(prest != null)prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}
	public void delete(ArrayList<Integer> FMIds) {
		try {
			for (Integer FMID : FMIds) {
				prest = connection.prepareStatement("delete from FilterMembers where FMID = '"+FMID+"';");
				prest.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showConfirmDialog(null, e.getMessage());
		} finally {
			try {
				connection.close(); // close Connection to server
				if(prest != null)prest.close(); // close PrepareStatement to server
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showConfirmDialog(null, e.getMessage());
			}
		}
	}
}
