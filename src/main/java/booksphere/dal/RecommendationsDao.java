package booksphere.dal;

import booksphere.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecommendationsDao {
    protected ConnectionManager connectionManager;

    private static RecommendationsDao instance = null;

    protected RecommendationsDao() {
        connectionManager = new ConnectionManager();
    }

    public static RecommendationsDao getInstance() {
        if (instance == null) {
            instance = new RecommendationsDao();
        }
        return instance;
    }

    public Recommendations create(Recommendations recommendation) throws SQLException {
        String insertRecommendation =
                "INSERT INTO Recommendations(UserID, BookID, PublisherID, Recommend) " +
                        "VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRecommendation,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, recommendation.getUser().getUserID());
            insertStmt.setLong(2, recommendation.getBook().getBookID());
            insertStmt.setInt(3, recommendation.getPublisher().getPublisherID());
            insertStmt.setBoolean(4, recommendation.isRecommend());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int recommendationID = -1;
            if (resultKey.next()) {
                recommendationID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            recommendation.setRecommendationID(recommendationID);
            return recommendation;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (resultKey != null) {
                resultKey.close();
            }
        }
    }
    
    public Recommendations update(Recommendations recommendation) throws SQLException {
        String updateRecommendation = "UPDATE Recommendations SET UserID=?, BookID=?, PublisherID=?, Recommend=? WHERE RecommendationID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateRecommendation);
            updateStmt.setInt(1, recommendation.getUser().getUserID());
            updateStmt.setLong(2, recommendation.getBook().getBookID());
            updateStmt.setInt(3, recommendation.getPublisher().getPublisherID());
            updateStmt.setBoolean(4, recommendation.isRecommend());
            updateStmt.setInt(5, recommendation.getRecommendationID());
            updateStmt.executeUpdate();

            return recommendation;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    public Recommendations delete(Recommendations recommendation) throws SQLException {
        String deleteRecommendation = "DELETE FROM Recommendations WHERE RecommendationID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteRecommendation);
            deleteStmt.setInt(1, recommendation.getRecommendationID());
            deleteStmt.executeUpdate();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
    
    public Recommendations getRecommendationByID(int recommendationID) throws SQLException {
        String selectRecommendation = "SELECT * FROM Recommendations WHERE RecommendationID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecommendation);
            selectStmt.setInt(1, recommendationID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int userID = results.getInt("UserID");
                long bookID = results.getLong("BookID");
                int publisherID = results.getInt("PublisherID");
                boolean recommend = results.getBoolean("Recommend");
                UsersDao userDao = UsersDao.getInstance();
                Users user = userDao.getUserByUserID(userID);
                BooksDao booksDao = BooksDao.getInstance();
                Books book = booksDao.getBookByBookID(bookID);
                PublisherDao publisherDao = PublisherDao.getInstance();
                Publisher publisher = publisherDao.getPublisherByPublisherID(publisherID);
                Recommendations recommendation = new Recommendations(user, book, publisher, recommend);
                recommendation.setRecommendationID(recommendationID);
                return recommendation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return null;
    }
    
    public List<Recommendations> getRecommendationsByUserID(int userID) throws SQLException {
        List<Recommendations> recommendationsList = new ArrayList<>();
        String selectRecommendations = "SELECT * FROM Recommendations WHERE UserID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecommendations);
            selectStmt.setInt(1, userID);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int recommendationID = results.getInt("RecommendationID");
                long bookID = results.getLong("BookID");
                int publisherID = results.getInt("PublisherID");
                boolean recommend = results.getBoolean("Recommend");

                // Retrieve book and publisher objects using their IDs
                BooksDao booksDao = BooksDao.getInstance();
                Books book = booksDao.getBookByBookID(bookID);
                PublisherDao publisherDao = PublisherDao.getInstance();
                Publisher publisher = publisherDao.getPublisherByPublisherID(publisherID);

                // Create and add recommendation object to the list
                Recommendations recommendation = new Recommendations();
                recommendation.setRecommendationID(recommendationID);
                recommendation.setUser(new Users(userID)); // Assuming a constructor with only userID exists
                recommendation.setBookID(book);
                recommendation.setPublisherID(publisher);
                recommendation.setRecommend(recommend);
                recommendationsList.add(recommendation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return recommendationsList;
    }


}
