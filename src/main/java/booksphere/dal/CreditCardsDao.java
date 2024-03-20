package booksphere.dal;

import booksphere.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data access object (DAO) class to interact with the underlying CreditCards table in your MySQL
 * instance. This is used to store {@link CreditCards} into your MySQL instance and retrieve 
 * {@link CreditCards} from MySQL instance.
 */
public class CreditCardsDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static CreditCardsDao instance = null;
	protected CreditCardsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CreditCardsDao getInstance() {
		if(instance == null) {
			instance = new CreditCardsDao();
		}
		return instance;
	}

	/**
	 * Save the CreditCards instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public CreditCards create(CreditCards creditCard) throws SQLException {
		String insertCreditCard = "INSERT INTO CreditCards(CardNumber,ExpirationDate,UserID,AddressID) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCreditCard);

			insertStmt.setLong(1, creditCard.getCardNumber());
			insertStmt.setTimestamp(2, new Timestamp(creditCard.getExpirationDate().getTime()));
			insertStmt.setInt(3, creditCard.getUser().getUserID());
			insertStmt.setInt(4, creditCard.getAddress().getAddressID());

			insertStmt.executeUpdate();
			
			return creditCard;
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
		}
	}

	/**
	 * Get the CreditCards record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single CreditCards instance.
	 */
	public CreditCards getCreditCardByCardNumber(long cardNumber) throws SQLException {
		String selectCreditCard = "SELECT CardNumber,ExpirationDate,UserID,AddressID FROM CreditCards WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCreditCard);
			selectStmt.setLong(1, cardNumber);

			results = selectStmt.executeQuery();

			UsersDao usersDao = UsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();
			if(results.next()) {
				long resultCardNumber = results.getLong("CardNumber");
				Date expirationDate = new Date(results.getTimestamp("ExpirationDate").getTime());
				int userID = results.getInt("UserID");
				Users user = usersDao.getUserByUserID(userID);
				int addressID = results.getInt("AddressID");
				Address address = addressDao.getAddressByAddressID(addressID);
				CreditCards creditCard = new CreditCards(resultCardNumber, expirationDate,user,address);
				return creditCard;
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
	 * Get the CreditCards records by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns an list of CreditCards instance.
	 */

	public List<CreditCards> getCreditCardsByUserID(int userID)
			throws SQLException {
		List<CreditCards> creditCards = new ArrayList<CreditCards>();
		String selectCreditCards =
			"SELECT CreditCards.CardNumber AS CardNumber, ExpirationDate, CreditCards.UserID AS UserID, "
			+ "CreditCards.AddressID AS AddressID " +
			"FROM CreditCards INNER JOIN Users ON CreditCards.UserID = Users.UserID "
			+ "INNER JOIN Address ON CreditCards.AddressID = Address.AddressID " +
			"WHERE CreditCards.UserID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCreditCards);
			selectStmt.setInt(1, userID);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();

			while(results.next()) {
				Long cardNumber = results.getLong("CardNumber");
				Date expirationDate = new Date(results.getTimestamp("ExpirationDate").getTime());
				Users user = usersDao.getUserByUserID(userID);
				Address address = addressDao.getAddressByAddressID(results.getInt("AddressID"));
				CreditCards creditCard = new CreditCards(cardNumber, expirationDate, user, address);
				creditCards.add(creditCard);
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
		return creditCards;
	}
	
	/**
	 * Update the expiration of the CreditCards instance.
	 * This runs a UPDATE statement.
	 */

	public CreditCards updateExpiration(CreditCards creditCard, Date newExpiration) throws SQLException {
		String updateExpiration = "UPDATE CreditCards SET ExpirationDate=? WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateExpiration);
			java.sql.Timestamp newExpirationSqlTimeStamp = new java.sql.Timestamp(newExpiration.getTime());
			updateStmt.setTimestamp(1, newExpirationSqlTimeStamp);
			updateStmt.setLong(2, creditCard.getCardNumber());
			updateStmt.executeUpdate();
			
			// Update the creditCard param before returning to the caller.
			creditCard.setExpirationDate(newExpiration);
			return creditCard;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	/**
	 * Delete the CreditCards instance.
	 * This runs a DELETE statement.
	 */
	public CreditCards delete(CreditCards creditCard) throws SQLException {
		String deleteCreditCard = "DELETE FROM CreditCards WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCreditCard);
			deleteStmt.setLong(1, creditCard.getCardNumber());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Users instance.
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
