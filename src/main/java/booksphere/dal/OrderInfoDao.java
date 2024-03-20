package booksphere.dal;

import booksphere.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderInfoDao {
    protected ConnectionManager connectionManager;

    private static OrderInfoDao instance = null;

    protected OrderInfoDao() {
        connectionManager = new ConnectionManager();
    }

    public static OrderInfoDao getInstance() {
        if (instance == null) {
            instance = new OrderInfoDao();
        }
        return instance;
    }

    public OrderInfo create(OrderInfo orderInfo) throws SQLException {
        String insertOrderInfo =
                "INSERT INTO OrderInfo(UserID, BookID, Status) " +
                        "VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertOrderInfo,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, orderInfo.getUser().getUserID());
            insertStmt.setLong(2, orderInfo.getBook().getBookID());
            insertStmt.setString(3, orderInfo.getStatus().name());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int orderID = -1;
            if (resultKey.next()) {
                orderID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            orderInfo.setOrderID(orderID);
            return orderInfo;
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
    
    public OrderInfo update(OrderInfo orderInfo) throws SQLException {
        String updateOrderInfo = "UPDATE OrderInfo SET Status=? WHERE OrderID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateOrderInfo);
            updateStmt.setString(1, orderInfo.getStatus().name());
            updateStmt.setInt(2, orderInfo.getOrderID());
            updateStmt.executeUpdate();
            return orderInfo;
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

    public void delete(OrderInfo orderInfo) throws SQLException {
        String deleteOrderInfo = "DELETE FROM OrderInfo WHERE OrderID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteOrderInfo);
            deleteStmt.setInt(1, orderInfo.getOrderID());
            deleteStmt.executeUpdate();
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
    
    public OrderInfo getOrderInfoByOrderID(int orderID) throws SQLException {
        String selectOrderInfo = "SELECT * FROM OrderInfo WHERE OrderID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectOrderInfo);
            selectStmt.setInt(1, orderID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int userID = results.getInt("UserID");
                long bookID = results.getLong("BookID");
                String status = results.getString("Status");
                UsersDao usersDao = UsersDao.getInstance();
                Users user = usersDao.getUserByUserID(userID);
                BooksDao booksDao = BooksDao.getInstance();
                Books book = booksDao.getBookByBookID(bookID);
                OrderInfo orderInfo = new OrderInfo(user, book, OrderInfo.OrderStatus.valueOf(status));
                orderInfo.setOrderID(orderID);
                return orderInfo;
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
    
    public List<OrderInfo> getOrdersByUser(Users user) throws SQLException {
        List<OrderInfo> orders = new ArrayList<>();
        String selectOrders =
            "SELECT * FROM OrderInfo WHERE UserID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectOrders);
            selectStmt.setInt(1, user.getUserID());
            results = selectStmt.executeQuery();
            while (results.next()) {
                int orderID = results.getInt("OrderID");
                int bookID = results.getInt("BookID");
                OrderInfo.OrderStatus status = OrderInfo.OrderStatus.valueOf(results.getString("Status"));
                BooksDao booksDao = BooksDao.getInstance();
                Books book = booksDao.getBookByBookID(bookID);
                OrderInfo order = new OrderInfo(user, book, status);
                order.setOrderID(orderID);
                orders.add(order);
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
        return orders;
    }
}
