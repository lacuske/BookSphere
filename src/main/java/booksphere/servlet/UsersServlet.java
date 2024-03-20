package booksphere.servlet;

import booksphere.dal.UsersDao;
import booksphere.model.Users;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            createUser(req, resp);
        } else if ("update".equals(action)) {
            updateUser(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            deleteUser(req, resp);
        } else if ("get".equals(action)) {
            getUser(req, resp);
        }
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String location = req.getParameter("location");
        int age = Integer.parseInt(req.getParameter("age"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");

        try {
            Users user = new Users(location, age, firstName, lastName, password);
            user = usersDao.create(user);
            resp.sendRedirect("users.jsp"); // Redirect to a page that lists users
        } catch (SQLException e) {
            throw new ServletException("Error creating user", e);
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        String newLocation = req.getParameter("location");
        int newAge = Integer.parseInt(req.getParameter("age"));
        String newFirstName = req.getParameter("firstName");
        String newLastName = req.getParameter("lastName");
        String newPassword = req.getParameter("password");

        try {
            Users user = usersDao.getUserByUserID(userId);
            if (user == null) {
                resp.getWriter().print("User not found");
                return;
            }

            user.setLocation(newLocation);
            user.setAge(newAge);
            user.setFirstName(newFirstName);
            user.setLastName(newLastName);
            user.setPassword(newPassword);
            usersDao.update(user);
            resp.sendRedirect("users.jsp"); // Redirect to the users listing page
        } catch (SQLException e) {
            throw new ServletException("Error updating user", e);
        }
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));

        try {
            Users user = new Users(userId);
            usersDao.delete(user);
            resp.sendRedirect("users.jsp"); // Redirects to a page that lists users
        } catch (SQLException e) {
            throw new ServletException("Error deleting user", e);
        }
    }

    private void getUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));

        try {
            Users user = usersDao.getUserByUserID(userId);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/viewUser.jsp").forward(req, resp); // Forwards to a page to view user details
        } catch (SQLException e) {
            throw new ServletException("Error retrieving user", e);
        }
    }
}
