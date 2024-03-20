package booksphere.dal;

import booksphere.model.Transactions;
import booksphere.model.CreditCards;
import booksphere.model.Books;
import booksphere.model.Users;
import booksphere.model.OrderInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionsDao {
    protected ConnectionManager connectionManager;

    private static TransactionsDao instance = null;

    protected TransactionsDao() {
        connectionManager = new ConnectionManager();
    }

    public static TransactionsDao getInstance() {
        if (instance == null) {
            instance = new TransactionsDao();
        }
        return instance;
    }

    public Transactions create(Transactions transaction) throws SQLException {
        String insertTransaction =
                "INSERT INTO Transactions(CreditCardID, BookID, TransactionDate, UserID, OrderID) " +
                        "VALUES(?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertTransaction,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setLong(1, transaction.getCardNumber().getCardNumber());
            insertStmt.setLong(2, transaction.getBook().getBookID());
            insertStmt.setTimestamp(3, transaction.getTransactionDate());
            insertStmt.setInt(4, transaction.getUser().getUserID());
            insertStmt.setInt(5, transaction.getOrderInfo().getOrderID());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int transactionID = -1;
            if (resultKey.next()) {
                transactionID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            transaction.setTransactionID(transactionID);
            return transaction;
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
    public Transactions update(Transactions transaction) throws SQLException {
        String updateTransaction = "UPDATE Transactions SET CreditCardID=?, BookID=?, TransactionDate=?, UserID=?, OrderID=? WHERE TransactionID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateTransaction);
            updateStmt.setLong(1, transaction.getCardNumber().getCardNumber());
            updateStmt.setLong(2, transaction.getBook().getBookID());
            updateStmt.setTimestamp(3, transaction.getTransactionDate());
            updateStmt.setInt(4, transaction.getUser().getUserID());
            updateStmt.setInt(5, transaction.getOrderInfo().getOrderID());
            updateStmt.setInt(6, transaction.getTransactionID());
            updateStmt.executeUpdate();

            return transaction;
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

    public Transactions delete(Transactions transaction) throws SQLException {
        String deleteTransaction = "DELETE FROM Transactions WHERE TransactionID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteTransaction);
            deleteStmt.setInt(1, transaction.getTransactionID());
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
    
    public List<Transactions> getTransactionsByUser(Users user) throws SQLException {
        List<Transactions> transactions = new ArrayList<>();
        String selectTransactions =
            "SELECT * FROM Transactions WHERE UserID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTransactions);
            selectStmt.setInt(1, user.getUserID());
            results = selectStmt.executeQuery();
            while (results.next()) {
                int transactionID = results.getInt("TransactionID");
                long cardNumber = results.getLong("CreditCard");
                int bookID = results.getInt("BookID");
                Timestamp transactionDate = results.getTimestamp("TransactionDate");
                int orderID = results.getInt("OrderID");
                CreditCardsDao creditCardsDao = CreditCardsDao.getInstance();
                CreditCards creditCard = creditCardsDao.getCreditCardByCardNumber(cardNumber);
                BooksDao booksDao = BooksDao.getInstance();
                Books book = booksDao.getBookByBookID(bookID);
                OrderInfoDao orderInfoDao = OrderInfoDao.getInstance();
                OrderInfo orderInfo = orderInfoDao.getOrderInfoByOrderID(orderID);
                Transactions transaction = new Transactions(creditCard, book, transactionDate, user, orderInfo);
                transaction.setTransactionID(transactionID);
                transactions.add(transaction);
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
        return transactions;
    }
    
    public List<Transactions> getTransactionsByBook(Books book) throws SQLException {
        List<Transactions> transactions = new ArrayList<>();
        String selectTransactions =
            "SELECT * FROM Transactions WHERE BookID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTransactions);
            selectStmt.setLong(1, book.getBookID());
            results = selectStmt.executeQuery();
            while (results.next()) {
                int transactionID = results.getInt("TransactionID");
                long cardNumber = results.getLong("CreditCard");
                Timestamp transactionDate = results.getTimestamp("TransactionDate");
                int userID = results.getInt("UserID");
                int orderID = results.getInt("OrderID");
                CreditCardsDao creditCardsDao = CreditCardsDao.getInstance();
                CreditCards creditCard = creditCardsDao.getCreditCardByCardNumber(cardNumber);
                UsersDao userDao = UsersDao.getInstance();
                Users user = userDao.getUserByUserID(userID);
                OrderInfoDao orderInfoDao = OrderInfoDao.getInstance();
                OrderInfo orderInfo = orderInfoDao.getOrderInfoByOrderID(orderID);
                Transactions transaction = new Transactions(creditCard, book, transactionDate, user, orderInfo);
                transaction.setTransactionID(transactionID);
                transactions.add(transaction);
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
        return transactions;
    }
    
    public Transactions getTransactionByID(int transactionID) throws SQLException {
        String selectTransaction =
            "SELECT TransactionID, CreditCardID, BookID, TransactionDate, UserID, OrderID " +
            "FROM Transactions " +
            "WHERE TransactionID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTransaction);
            selectStmt.setInt(1, transactionID);

            results = selectStmt.executeQuery();
            
            CreditCardsDao creditCardsDao = CreditCardsDao.getInstance();
            BooksDao booksDao = BooksDao.getInstance();
            UsersDao usersDao = UsersDao.getInstance();
            OrderInfoDao orderInfoDao = OrderInfoDao.getInstance();

            if (results.next()) {
                int resultTransactionID = results.getInt("TransactionID");
                long cardNumber = results.getLong("CreditCardID");
                long bookID = results.getLong("BookID");
                Timestamp transactionDate = results.getTimestamp("TransactionDate");
                int userID = results.getInt("UserID");
                int orderID = results.getInt("OrderID");

                CreditCards creditCard = creditCardsDao.getCreditCardByCardNumber(cardNumber);
                Books book = booksDao.getBookByBookID(bookID);
                Users user = usersDao.getUserByUserID(userID);
                OrderInfo orderInfo = orderInfoDao.getOrderInfoByOrderID(orderID);

                Transactions transaction = new Transactions(transactionID, creditCard, book, transactionDate, user, orderInfo);
                return transaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (results != null) {
                results.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

}
