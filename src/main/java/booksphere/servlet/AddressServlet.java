package booksphere.servlet;

import booksphere.dal.AddressDao;
import booksphere.model.Address;
import booksphere.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/address")
public class AddressServlet extends HttpServlet {
    
    protected AddressDao addressDao;
    
    @Override
    public void init() throws ServletException {
        addressDao = AddressDao.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle creating a new address or updating an existing one
        String action = req.getParameter("action");
        
        if ("create".equals(action)) {
            // Create new address
            createAddress(req, resp);
        } 
        //there is no update function in addressdao
//        else if ("update".equals(action)) {
//            // Update existing address
//            updateAddress(req, resp);
//        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle reading an address
        int addressId = Integer.parseInt(req.getParameter("addressId"));
        try {
            Address address = addressDao.getAddressByAddressID(addressId);
            req.setAttribute("address", address);
            req.getRequestDispatcher("/showAddress.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error reading address", e);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle deleting an address
        int addressId = Integer.parseInt(req.getParameter("addressId"));
        try {
            Address address = new Address(addressId);
            addressDao.delete(address);
            resp.getWriter().print("Address deleted successfully");
        } catch (SQLException e) {
            throw new ServletException("Error deleting address", e);
        }
    }
    
    private void createAddress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("userId"));
            Users user = new Users(userId); // Simplified, assuming user exists

            Address address = new Address(
                    req.getParameter("street1"),
                    req.getParameter("street2"),
                    req.getParameter("city"),
                    req.getParameter("state"),
                    req.getParameter("zip"),
                    Boolean.parseBoolean(req.getParameter("billingAddress")),
                    Boolean.parseBoolean(req.getParameter("mailingAddress")),
                    user);
            addressDao.create(address);
            resp.sendRedirect("/someSuccessPage.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error creating address", e);
        }
    }
    
    //there is no update function in addressDao
//    private void updateAddress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        int addressId = Integer.parseInt(req.getParameter("addressId"));
//        
//        try {
//            Address address = addressDao.getAddressByAddressID(addressId);
//            if (address == null) {
//                resp.sendRedirect("/someErrorPage.jsp");
//                return;
//            }
//            address.setStreet1(req.getParameter("street1"));
//            address.setStreet2(req.getParameter("street2"));
//            address.setCity(req.getParameter("city"));
//            address.setState(req.getParameter("state"));
//            address.setZip(req.getParameter("zip"));
//            address.setBillingAddress(Boolean.parseBoolean(req.getParameter("billingAddress")));
//            address.setMailingAddress(Boolean.parseBoolean(req.getParameter("mailingAddress")));
//            
//            addressDao.update(address);
//            resp.sendRedirect("/someSuccessPage.jsp");
//        } catch (SQLException e) {
//            throw new ServletException("Error updating address", e);
//        }
//    }

    // Note: The doDelete method cannot be called directly from a browser without using JavaScript to make a DELETE request.
    // It's typically easier to use a doPost method for deletion in web forms.
}
