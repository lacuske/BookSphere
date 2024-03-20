package booksphere.dal;

import booksphere.model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    protected ConnectionManager connectionManager;

    private static AuthorDao instance = null;

    protected AuthorDao() {
        connectionManager = new ConnectionManager();
    }

    public static AuthorDao getInstance() {
        if (instance == null) {
            instance = new AuthorDao();
        }
        return instance;
    }

    public Author create(Author author) throws SQLException {
        String insertAuthor =
                "INSERT INTO Author(AuthorName, Biography) " +
                        "VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertAuthor,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, author.getAuthorName());
            insertStmt.setString(2, author.getBiography());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int authorId = -1;
            if (resultKey.next()) {
                authorId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            author.setAuthorID(authorId);
            return author;
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

    public Author update(Author author, String newBiography) throws SQLException {
        String updateAuthor = "UPDATE Author SET Biography=? WHERE AuthorID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateAuthor);
            updateStmt.setString(1, newBiography);
            updateStmt.setInt(2, author.getAuthorID());
            updateStmt.executeUpdate();

            author.setBiography(newBiography);
            return author;
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

    public Author delete(Author author) throws SQLException {
        String deleteAuthor = "DELETE FROM Author WHERE AuthorID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteAuthor);
            deleteStmt.setInt(1, author.getAuthorID());
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

    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String selectAuthors =
                "SELECT * FROM Author;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAuthors);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int authorId = results.getInt("AuthorID");
                String authorName = results.getString("AuthorName");
                String biography = results.getString("Biography");
                Author author = new Author(authorName, biography);
                author.setAuthorID(authorId);
                authors.add(author);
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
        return authors;
    }

    public Author getAuthorById(int authorID) throws SQLException {
        String selectAuthor =
                "SELECT * FROM Author WHERE AuthorID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAuthor);
            selectStmt.setInt(1, authorID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                String authorName = results.getString("AuthorName");
                String biography = results.getString("Biography");
                Author author = new Author(authorName, biography);
                author.setAuthorID(authorID);
                return author;
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
