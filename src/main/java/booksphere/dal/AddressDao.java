package booksphere.dal;

import booksphere.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static AddressDao instance = null;
	protected AddressDao() {
		connectionManager = new ConnectionManager();
	}
	public static AddressDao getInstance() {
		if(instance == null) {
			instance = new AddressDao();
		}
		return instance;
	}
	
	/**
	 * Save the Users instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Address create(Address address) throws SQLException {
		String insertAddress = "INSERT INTO Address(Street1,Street2,City,State,Zip,BillingAddress,MailingAddress,UserID) VALUES(?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAddress);
			// Users has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertAddress,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, address.getStreet1());
			insertStmt.setString(2, address.getStreet2());
			insertStmt.setString(3, address.getCity());
			insertStmt.setString(4, address.getState());
			insertStmt.setString(5, address.getZip());
			insertStmt.setBoolean(6, address.isBillingAddress());
			insertStmt.setBoolean(7, address.isMailingAddress());
			insertStmt.setInt(8, address.getUser().getUserID());

			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int addressID = -1;
			if(resultKey.next()) {
				addressID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			address.setAddressID(addressID);
			return address;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}

	/**
	 * Get the Address record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Users instance.
	 */
	public Address getAddressByAddressID(int addressID) throws SQLException {
		String selectAddress = "SELECT AddressID, Street1, Street2, City, State, Zip, "
				+ "BillingAddress, MailingAddress, UserID FROM Address WHERE AddressID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAddress);
			selectStmt.setInt(1, addressID);

			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultAddressID = results.getInt("AddressID");
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				String zip = results.getString("Zip");
				Boolean billingAddress = results.getBoolean("BillingAddress");
				Boolean mailingAddress = results.getBoolean("MailingAddress");
				int userID = results.getInt("UserID");
				Users user = usersDao.getUserByUserID(userID);
						
				Address address = new Address(resultAddressID, street1, street2, city, state, zip, billingAddress,mailingAddress,user);
				return address;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}

	/**
	 * Delete the Address instance.
	 * This runs a DELETE statement.
	 */
	public Address delete(Address address) throws SQLException {
		String deleteAddress = "DELETE FROM Address WHERE AddressID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAddress);
			deleteStmt.setInt(1, address.getAddressID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Address instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

}
