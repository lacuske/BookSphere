package booksphere.servlet;

import booksphere.dal.BooksDao;
import booksphere.dal.OrderInfoDao;
import booksphere.dal.UsersDao;
import booksphere.model.Books;
import booksphere.model.OrderInfo;
import booksphere.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/orderinfo")
public class OrderInfoServlet extends HttpServlet {
    protected OrderInfoDao orderInfoDao;
    protected BooksDao booksDao;
    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        orderInfoDao = OrderInfoDao.getInstance();
        booksDao = BooksDao.getInstance();
        usersDao = UsersDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("create".equals(action)) {
            createOrder(req, resp);
        } else if ("update".equals(action)) {
            updateOrder(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("delete".equals(action)) {
            deleteOrder(req, resp);
        } else if ("get".equals(action)) {
            getOrder(req, resp);
        }
    }
    
    private void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Extract parameters and perform necessary conversion
        int userID = Integer.parseInt(req.getParameter("userID"));
        long bookID = Long.parseLong(req.getParameter("bookID"));
        OrderInfo.OrderStatus status = OrderInfo.OrderStatus.valueOf(req.getParameter("status"));
        
        try {
            // Retrieve associated objects
            Users user = usersDao.getUserByUserID(userID);
            Books book = booksDao.getBookByBookID(bookID);
            OrderInfo orderInfo = new OrderInfo(user, book, status);
            orderInfoDao.create(orderInfo);
            resp.sendRedirect("listOrders.jsp"); // Redirect or request dispatch to view orders
        } catch (SQLException e) {
            throw new ServletException("Error creating order", e);
        }
    }
    
    private void updateOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderID = Integer.parseInt(req.getParameter("orderID"));
        OrderInfo.OrderStatus newStatus = OrderInfo.OrderStatus.valueOf(req.getParameter("newStatus"));
        
        try {
            OrderInfo orderInfo = orderInfoDao.getOrderInfoByOrderID(orderID);
            if (orderInfo == null) {
                resp.getWriter().print("Order not found");
                return;
            }
            orderInfo.setStatus(newStatus);
            orderInfoDao.update(orderInfo);
            resp.sendRedirect("listOrders.jsp"); // Redirect or request dispatch to updated order view
        } catch (SQLException e) {
            throw new ServletException("Error updating order", e);
        }
    }
    
    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderID = Integer.parseInt(req.getParameter("orderID"));
        
        try {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderID(orderID);
            orderInfoDao.delete(orderInfo);
            resp.sendRedirect("listOrders.jsp"); // Redirect to the list of orders
        } catch (SQLException e) {
            throw new ServletException("Error deleting order", e);
        }
    }
    
    private void getOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderID = Integer.parseInt(req.getParameter("orderID"));
        
        try {
            OrderInfo orderInfo = orderInfoDao.getOrderInfoByOrderID(orderID);
            req.setAttribute("orderInfo", orderInfo);
            req.getRequestDispatcher("/viewOrder.jsp").forward(req, resp); // Forward to JSP page to display the order
        } catch (SQLException e) {
            throw new ServletException("Error retrieving order", e);
        }
    }
}
