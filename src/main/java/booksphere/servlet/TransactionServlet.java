package booksphere.servlet;

import booksphere.dal.TransactionsDao;
import booksphere.model.Transactions;
import booksphere.model.CreditCards;
import booksphere.model.Books;
import booksphere.model.Users;
import booksphere.model.OrderInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    protected TransactionsDao transactionsDao;

    @Override
    public void init() throws ServletException {
        transactionsDao = TransactionsDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            createTransaction(req, resp);
        } else if ("update".equals(action)) {
            updateTransaction(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            deleteTransaction(req, resp);
        } else if ("get".equals(action)) {
            getTransaction(req, resp);
        }
    }

    private void createTransaction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long cardNumber = Long.parseLong(req.getParameter("cardNumber"));
        long bookId = Long.parseLong(req.getParameter("bookId"));
        Timestamp transactionDate = Timestamp.valueOf(req.getParameter("transactionDate"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        try {
            CreditCards creditCard = new CreditCards(cardNumber);
            Books book = new Books(bookId);
            Users user = new Users(userId);
            OrderInfo orderInfo = new OrderInfo(orderId);
            Transactions transaction = new Transactions(creditCard, book, transactionDate, user, orderInfo);
            transactionsDao.create(transaction);
            resp.sendRedirect("transactions.jsp"); // Redirects to a page that lists transactions
        } catch (SQLException e) {
            throw new ServletException("Error creating transaction", e);
        }
    }

    private void updateTransaction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int transactionId = Integer.parseInt(req.getParameter("transactionId"));
        long cardNumber = Long.parseLong(req.getParameter("cardNumber"));
        long bookId = Long.parseLong(req.getParameter("bookId"));
        Timestamp transactionDate = Timestamp.valueOf(req.getParameter("transactionDate"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        try {
            Transactions transaction = transactionsDao.getTransactionByID(transactionId);
            if (transaction == null) {
                resp.getWriter().print("Transaction not found");
                return;
            }

            transaction.setCardNumber(new CreditCards(cardNumber));
            transaction.setBook(new Books(bookId));
            transaction.setTransactionDate(transactionDate);
            transaction.setUser(new Users(userId));
            transaction.setOrderInfo(new OrderInfo(orderId));
            
            transactionsDao.update(transaction);
            resp.sendRedirect("transactions.jsp"); // Redirect to the transactions listing page.
        } catch (SQLException e) {
            throw new ServletException("Error updating transaction", e);
        }
    }

    private void deleteTransaction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int transactionId = Integer.parseInt(req.getParameter("transactionId"));

        try {
            Transactions transaction = new Transactions(transactionId);
            transactionsDao.delete(transaction);
            resp.sendRedirect("transactions.jsp"); // Redirects to a page that lists transactions
        } catch (SQLException e) {
            throw new ServletException("Error deleting transaction", e);
        }
    }

    private void getTransaction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int transactionId = Integer.parseInt(req.getParameter("transactionId"));

        try {
            Transactions transaction = transactionsDao.getTransactionByID(transactionId);
            req.setAttribute("transaction", transaction);
            req.getRequestDispatcher("/viewTransaction.jsp").forward(req, resp); // Forwards to a page to view transaction details
        } catch (SQLException e) {
            throw new ServletException("Error retrieving transaction", e);
        }
    }
}
