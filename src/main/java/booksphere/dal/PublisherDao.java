package booksphere.dal;

import booksphere.model.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PublisherDao {
    protected ConnectionManager connectionManager;

    private static PublisherDao instance = null;

    protected PublisherDao() {
        connectionManager = new ConnectionManager();
    }

    public static PublisherDao getInstance() {
        if (instance == null) {
            instance = new PublisherDao();
        }
        return instance;
    }

    public Publisher create(Publisher publisher) throws SQLException {
        String insertPublisher =
                "INSERT INTO Publisher(PublisherName, Biography) " +
                        "VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertPublisher,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, publisher.getPublisherName());
            insertStmt.setString(2, publisher.getBiography());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int publisherId = -1;
            if (resultKey.next()) {
                publisherId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            publisher.setPublisherID(publisherId);
            return publisher;
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

    public Publisher update(Publisher publisher) throws SQLException {
        String updatePublisher = "UPDATE Publisher SET PublisherName=?, Biography=? WHERE PublisherID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updatePublisher);
            updateStmt.setString(1, publisher.getPublisherName());
            updateStmt.setString(2, publisher.getBiography());
            updateStmt.setInt(3, publisher.getPublisherID());
            updateStmt.executeUpdate();

            return publisher;
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

    public Publisher delete(Publisher publisher) throws SQLException {
        String deletePublisher = "DELETE FROM Publisher WHERE PublisherID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletePublisher);
            deleteStmt.setInt(1, publisher.getPublisherID());
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
    
    public Publisher getPublisherByPublisherID(int publisherID) throws SQLException {
        String selectPublisher =
                "SELECT * FROM Publisher WHERE PublisherID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectPublisher);
            selectStmt.setInt(1, publisherID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                String publisherName = results.getString("PublisherName");
                String biography = results.getString("Biography");
                Publisher publisher = new Publisher(publisherName, biography);
                publisher.setPublisherID(publisherID);
                return publisher;
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

    public Publisher getPublisherByPublisherName(String publisherName) throws SQLException {
        String selectPublisher =
                "SELECT * FROM Publisher WHERE PublisherName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectPublisher);
            selectStmt.setString(1, publisherName);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int publisherID = results.getInt("PublisherID");
                String biography = results.getString("Biography");
                Publisher publisher = new Publisher(publisherName, biography);
                publisher.setPublisherID(publisherID);
                return publisher;
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

}
