package booksphere.dal;

import booksphere.model.*;
import booksphere.model.Books.BookType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BooksDao {
    protected ConnectionManager connectionManager;

    private static BooksDao instance = null;

    protected BooksDao() {
        connectionManager = new ConnectionManager();
    }

    public static BooksDao getInstance() {
        if (instance == null) {
            instance = new BooksDao();
        }
        return instance;
    }

    public Books create(Books book) throws SQLException {
        String insertBook =
                "INSERT INTO Books(ISBN, Title, AuthorID, BookYear, PublisherID, BookType) " +
                        "VALUES(?, ?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertBook,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, book.getIsbn());
            insertStmt.setString(2, book.getTitle());
            insertStmt.setInt(3, book.getAuthor().getAuthorID());
            insertStmt.setInt(4, book.getBookYear());
            insertStmt.setInt(5, book.getPublisher().getPublisherID());
            insertStmt.setString(6, book.getBookType().name());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            long bookID = -1;
            if (resultKey.next()) {
                bookID = resultKey.getLong(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            book.setBookID(bookID);
            return book;
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

    public Books update(Books book) throws SQLException {
        String updateBook = "UPDATE Books SET ISBN=?, Title=?, AuthorID=?, BookYear=?, PublisherID=?, BookType=? WHERE BookID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateBook);
            updateStmt.setString(1, book.getIsbn());
            updateStmt.setString(2, book.getTitle());
            updateStmt.setInt(3, book.getAuthor().getAuthorID());
            updateStmt.setInt(4, book.getBookYear());
            updateStmt.setInt(5, book.getPublisher().getPublisherID());
            updateStmt.setString(6, book.getBookType().toString());
            updateStmt.setLong(7, book.getBookID());
            updateStmt.executeUpdate();

            return book;
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

    public Books delete(Books book) throws SQLException {
        String deleteBook = "DELETE FROM Books WHERE BookID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteBook);
            deleteStmt.setLong(1, book.getBookID());
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
    
    public Books getBookByBookID(long bookID) throws SQLException {
        String selectBook = "SELECT * FROM Books WHERE BookID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectBook);
            selectStmt.setLong(1, bookID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                String isbn = results.getString("ISBN");
                String title = results.getString("Title");
                int bookYear = results.getInt("BookYear");
                BookType bookType = BookType.valueOf(results.getString("BookType"));
                int authorID = results.getInt("Author");
                int pulisherID = results.getInt("Publisher");
                AuthorDao authorDao = AuthorDao.getInstance();
                Author author = authorDao.getAuthorById(authorID);
                PublisherDao publisherDao = PublisherDao.getInstance();
                Publisher publisher = publisherDao.getPublisherByPublisherID(pulisherID);
                Books book = new Books(isbn, title, author, bookYear, publisher, bookType);
                book.setBookID(bookID);
                return book;
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
    public Books getBookByISBN(String isbn) throws SQLException {
        String selectBook = "SELECT * FROM Books WHERE ISBN=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectBook);
            selectStmt.setString(1, isbn);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long bookID = results.getLong("BookID");
                String title = results.getString("Title");
                int bookYear = results.getInt("BookYear");
                BookType bookType = BookType.valueOf(results.getString("BookType"));
                int authorID = results.getInt("Author");
                int publisherID = results.getInt("Publisher");
                AuthorDao authorDao = AuthorDao.getInstance();
                Author author = authorDao.getAuthorById(authorID);
                PublisherDao publisherDao = PublisherDao.getInstance();
                Publisher publisher = publisherDao.getPublisherByPublisherID(publisherID);
                Books book = new Books(isbn, title, author, bookYear, publisher, bookType);
                book.setBookID(bookID);
                return book;
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