package booksphere.servlet;

import booksphere.dal.CreditCardsDao;
import booksphere.model.CreditCards;
import booksphere.model.Users;
import booksphere.model.Address;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/creditcards")
public class CreditCardsServlet extends HttpServlet {
    protected CreditCardsDao creditCardsDao;
    
    @Override
    public void init() throws ServletException {
        creditCardsDao = CreditCardsDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("create".equals(action)) {
            createCreditCard(req, resp);
        } else if ("update".equals(action)) {
            updateCreditCard(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("delete".equals(action)) {
            deleteCreditCard(req, resp);
        } else if ("get".equals(action)) {
            getCreditCard(req, resp);
        }
    }
    
    private void createCreditCard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Assuming you have a user and an address. In real scenario, you should get these from request or session.
        long cardNumber = Long.parseLong(req.getParameter("cardNumber"));
        Date expirationDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            expirationDate = sdf.parse(req.getParameter("expirationDate"));
        } catch (ParseException e) {
            throw new ServletException("Error parsing expiration date", e);
        }
        int userId = Integer.parseInt(req.getParameter("userId"));
        int addressId = Integer.parseInt(req.getParameter("addressId"));
        Users user = new Users(userId); // Simplified
        Address address = new Address(addressId); // Simplified
        
        try {
            CreditCards creditCard = new CreditCards(cardNumber, expirationDate, user, address);
            creditCardsDao.create(creditCard);
            resp.sendRedirect("showCreditCard.jsp"); // Redirect to a view showing the created credit card
        } catch (SQLException e) {
            throw new ServletException("Error creating credit card", e);
        }
    }
    
    private void updateCreditCard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long cardNumber = Long.parseLong(req.getParameter("cardNumber"));
        Date newExpiration;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            newExpiration = sdf.parse(req.getParameter("newExpirationDate"));
        } catch (ParseException e) {
            throw new ServletException("Error parsing expiration date", e);
        }
        
        try {
            CreditCards creditCard = creditCardsDao.getCreditCardByCardNumber(cardNumber);
            if (creditCard == null) {
                resp.getWriter().print("Credit card not found");
                return;
            }
            creditCardsDao.updateExpiration(creditCard, newExpiration);
            resp.sendRedirect("showCreditCard.jsp"); // Redirect to a view showing the updated credit card
        } catch (SQLException e) {
            throw new ServletException("Error updating credit card", e);
        }
    }
    
    private void deleteCreditCard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long cardNumber = Long.parseLong(req.getParameter("cardNumber"));
        
        try {
            CreditCards creditCard = new CreditCards(cardNumber);
            creditCardsDao.delete(creditCard);
            resp.sendRedirect("creditCardsList.jsp"); // Redirect to the list of credit cards
        } catch (SQLException e) {
            throw new ServletException("Error deleting credit card", e);
        }
    }
    
    private void getCreditCard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long cardNumber = Long.parseLong(req.getParameter("cardNumber"));
        
        try {
            CreditCards creditCard = creditCardsDao.getCreditCardByCardNumber(cardNumber);
            req.setAttribute("creditCard", creditCard);
            req.getRequestDispatcher("/viewCreditCard.jsp").forward(req, resp); // Forward to a JSP to display the credit card
        } catch (SQLException e) {
            throw new ServletException("Error retrieving credit card", e);
        }
    }
}
