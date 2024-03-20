package booksphere.dal;

import booksphere.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RatingDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static RatingDao instance = null;
	protected RatingDao() {
		connectionManager = new ConnectionManager();
	}
	public static RatingDao getInstance() {
		if(instance == null) {
			instance = new RatingDao();
		}
		return instance;
	}
	
	
	/**
	 * Save the Rating instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Rating create(Rating rating) throws SQLException {
		String insertRating = "INSERT INTO Rating(Rating,UserID,ISBN) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRating);
			// Users has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertRating,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, rating.getRating());
			insertStmt.setInt(2, rating.getUser().getUserID());
			insertStmt.setString(3, rating.getBook().getIsbn());

			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int ratingID = -1;
			if(resultKey.next()) {
				ratingID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			rating.setRatingID(ratingID);
			return rating;
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
	 * Get the Rating record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Rating instance.
	 */
	public Rating getRatingByRatingID(int ratingID) throws SQLException {
		String selectRating = "SELECT RatingID,Rating,UserID,ISBN FROM Rating WHERE RatingID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRating);
			selectStmt.setInt(1, ratingID);

			results = selectStmt.executeQuery();

			UsersDao usersDao = UsersDao.getInstance();
			Books booksDao = BooksDao.getInstance();
			if(results.next()) {
				int resultRatingID = results.getInt("RatingID");
				int rating = results.getInt("Rating");
				int userID = results.getInt("UserID");
				Users user = usersDao.getUserByUserID(userID);
				String isbn = results.getString("ISBN");
				Books book = booksDao.getBookByISBN(isbn);
				Rating ratingResult = new Rating(ratingID, rating,user,book);
				return ratingResult;
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
	 * Get the Rating records by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns an list of Rating instance.
	 */

	public List<Rating> getRatingByUserID(int userID)
			throws SQLException {
		List<Rating> ratings = new ArrayList<Rating>();
		String selectRatings =
			"SELECT Rating.RatingID AS RatingID, Rating, Rating.UserID AS UserID, "
			+ "Rating.ISBN AS ISBN " +
			"FROM Rating INNER JOIN Users ON Rating.UserID = Users.UserID "
			+ "INNER JOIN Books ON Rating.ISBN = Books.ISBN " +
			"WHERE Rating.UserID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRatings);
			selectStmt.setInt(1, userID);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			BooksDao booksDao = AddressDao.getInstance();

			while(results.next()) {
				int ratingID = results.getInt("RatingID");
				int rating = results.getInt("Rating");
				Users user = usersDao.getUserByUserID(userID);
				Books book = booksDao.getBookByISBN(results.getString("ISBN"));
				Rating ratingResult = new Rating(ratingID, rating, user, book);
				ratings.add(ratingResult);
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
		return ratings;
	}
	
	/**
	 * Delete the Rating instance.
	 * This runs a DELETE statement.
	 */
	public Rating delete(Rating rating) throws SQLException {
		String deleteRating = "DELETE FROM Rating WHERE RatingID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRating);
			deleteStmt.setInt(1, rating.getRatingID());
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
