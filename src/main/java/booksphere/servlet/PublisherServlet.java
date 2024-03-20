package booksphere.servlet;

import booksphere.dal.PublisherDao;
import booksphere.model.Publisher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/publisher")
public class PublisherServlet extends HttpServlet {
    protected PublisherDao publisherDao;

    @Override
    public void init() throws ServletException {
        publisherDao = PublisherDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            createPublisher(req, resp);
        } else if ("update".equals(action)) {
            updatePublisher(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            deletePublisher(req, resp);
        } else if ("get".equals(action)) {
            getPublisher(req, resp);
        }
    }

    private void createPublisher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String biography = req.getParameter("biography");

        try {
            Publisher publisher = new Publisher(name, biography);
            publisherDao.create(publisher);
            resp.sendRedirect("publishers.jsp"); // Redirect to a view showing all publishers
        } catch (SQLException e) {
            throw new ServletException("Error creating publisher", e);
        }
    }

    private void updatePublisher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int publisherId = Integer.parseInt(req.getParameter("publisherId"));
        String newName = req.getParameter("newName");
        String newBiography = req.getParameter("newBiography");

        try {
            Publisher publisher = publisherDao.getPublisherByPublisherID(publisherId);
            if (publisher == null) {
                resp.getWriter().print("Publisher not found");
                return;
            }
            publisher.setPublisherName(newName);
            publisher.setBiography(newBiography);
            publisherDao.update(publisher);
            resp.sendRedirect("publishers.jsp"); // Redirect to a view showing all publishers
        } catch (SQLException e) {
            throw new ServletException("Error updating publisher", e);
        }
    }

    private void deletePublisher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int publisherId = Integer.parseInt(req.getParameter("publisherId"));

        try {
            Publisher publisher = new Publisher();
            publisher.setPublisherID(publisherId);
            publisherDao.delete(publisher);
            resp.sendRedirect("publishers.jsp"); // Redirect to the list of publishers
        } catch (SQLException e) {
            throw new ServletException("Error deleting publisher", e);
        }
    }

    private void getPublisher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int publisherId = Integer.parseInt(req.getParameter("publisherId"));

        try {
            Publisher publisher = publisherDao.getPublisherByPublisherID(publisherId);
            req.setAttribute("publisher", publisher);
            req.getRequestDispatcher("/viewPublisher.jsp").forward(req, resp); // Forward to JSP page to display the publisher
        } catch (SQLException e) {
            throw new ServletException("Error retrieving publisher", e);
        }
    }
}
