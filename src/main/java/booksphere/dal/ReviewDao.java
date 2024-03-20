package booksphere.dal;

import booksphere.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static ReviewDao instance = null;
	protected ReviewDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewDao getInstance() {
		if(instance == null) {
			instance = new ReviewDao();
		}
		return instance;
	}
	
	/**
	 * Save the Review instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Review create(Review review) throws SQLException {
		String insertReview = "INSERT INTO Review(UserID,BookID,Review) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview);
			// Users has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertReview,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, review.getUser().getUserID());
			insertStmt.setLong(2, review.getBook().getBookID());
			insertStmt.setString(3, review.getReview());

			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int reviewID = -1;
			if(resultKey.next()) {
				reviewID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewID(reviewID);
			return review;
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
	 * Get the Review record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Review instance.
	 */
	public Review getReviewByReviewID(int reviewID) throws SQLException {
		String selectReview = "SELECT ReviewID,UserID,BookID,Review FROM Review WHERE ReviewID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewID);

			results = selectStmt.executeQuery();

			UsersDao usersDao = UsersDao.getInstance();
			BooksDao booksDao = BooksDao.getInstance();
			if(results.next()) {
				int resultReviewID = results.getInt("ReviewID");
				int userID = results.getInt("UserID");
				Users user = usersDao.getUserByUserID(userID);
				long bookID = results.getLong("BookID");
				Books book = booksDao.getBookByBookID(bookID);
				String reviews = results.getString("Review");
				Review reviewResult = new Review(resultReviewID, user,book, reviews);
				return reviewResult;
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
	 * Get the Review records by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns an list of Review instance.
	 */

	public List<Review> getReviewByUserID(int userID)
			throws SQLException {
		List<Review> reviews = new ArrayList<Review>();
		String selectReviews =
			"SELECT Review.ReviewID AS ReviewID, Review.UserID AS UserID, "
			+ "Review.BookID AS BookID, Review " +
			"FROM Review INNER JOIN Users ON Review.UserID = Review.UserID "
			+ "INNER JOIN Books ON Review.BookID = Books.BookID " +
			"WHERE Review.UserID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setInt(1, userID);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			BooksDao booksDao = BooksDao.getInstance();

			while(results.next()) {
				int reviewID = results.getInt("ReviewID");
				Users user = usersDao.getUserByUserID(userID);
				Books book = booksDao.getBookByBookID(results.getLong("BookID"));
				String reviewColumn = results.getString("Review");
				Review reviewResult = new Review(reviewID, user, book, reviewColumn);
				reviews.add(reviewResult);
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
		return reviews;
	}
	
	/**
	 * Delete the Review instance.
	 * This runs a DELETE statement.
	 */
	public Review delete(Review review) throws SQLException {
		String deleteReview = "DELETE FROM Review WHERE ReviewID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewID());
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
