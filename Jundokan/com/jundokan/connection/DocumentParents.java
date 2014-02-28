package jundokan.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import jundokan.model.DBDocumentParents;

public class DocumentParents {
	private Connection connection = null;
	private PreparedStatement prest = null;
	public DocumentParents() {
		connection = Config.getConnection();
	}
		public void insert(DBDocumentParents documentParents) {
			try {
				prest = connection.prepareStatement("insert into DocumentParents(DParent, PersID)" + "VALUES (?, ?)");
				prest.setString(1, documentParents.DParent);
				prest.setInt(2, documentParents.PersID);
				prest.executeUpdate();
			} catch (SQLException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage());
			} finally {
				try {
					connection.close(); // close Connection to server
					prest.close(); // close PrepareStatement to serve
				} catch (Exception e) {
					e.printStackTrace();
					//JOptionPane.showConfirmDialog(null, e.getMessage());
				}
			}
		}

}
