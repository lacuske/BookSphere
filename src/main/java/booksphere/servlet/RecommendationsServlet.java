package booksphere.servlet;

import booksphere.dal.BooksDao;
import booksphere.dal.PublisherDao;
import booksphere.dal.RecommendationsDao;
import booksphere.dal.UsersDao;
import booksphere.model.Recommendations;
import booksphere.model.Users;
import booksphere.model.Books;
import booksphere.model.Publisher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/recommendations")
public class RecommendationsServlet extends HttpServlet {

    private RecommendationsDao recommendationsDao;
    private UsersDao usersDao;
    private BooksDao booksDao;
    private PublisherDao publisherDao;

    @Override
    public void init() throws ServletException {
        recommendationsDao = RecommendationsDao.getInstance();
        usersDao = UsersDao.getInstance();
        booksDao = BooksDao.getInstance();
        publisherDao = PublisherDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("create".equals(action)) {
                createRecommendation(req, resp);
            } else if ("update".equals(action)) {
                updateRecommendation(req, resp);
            }
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Error processing recommendation: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("delete".equals(action)) {
                deleteRecommendation(req, resp);
            } else if ("get".equals(action)) {
                getRecommendation(req, resp);
            }
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Error processing recommendation: " + e.getMessage(), e);
        }
    }

    private void createRecommendation(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        long bookId = Long.parseLong(req.getParameter("bookId"));
        int publisherId = Integer.parseInt(req.getParameter("publisherId"));
        boolean recommend = Boolean.parseBoolean(req.getParameter("recommend"));

        Users user = usersDao.getUserByUserID(userId);
        Books book = booksDao.getBookByBookID(bookId);
        Publisher publisher = publisherDao.getPublisherByPublisherID(publisherId);
        Recommendations recommendation = new Recommendations(user, book, publisher, recommend);
        recommendationsDao.create(recommendation);
        resp.sendRedirect("recommendations.jsp");
    }

    private void updateRecommendation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int recommendationId = Integer.parseInt(req.getParameter("recommendationId"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        long bookId = Integer.parseInt(req.getParameter("bookId"));
        int publisherId = Integer.parseInt(req.getParameter("publisherId"));
        boolean recommend = Boolean.parseBoolean(req.getParameter("recommend"));

        Users user = usersDao.getUserByUserID(userId);
        Books book = booksDao.getBookByBookID(bookId);
        Publisher publisher = publisherDao.getPublisherByPublisherID(publisherId);
        Recommendations recommendation = new Recommendations(user, book, publisher, recommend);
        recommendation.setRecommendationID(recommendationId);
        recommendationsDao.update(recommendation);
        resp.sendRedirect("recommendations.jsp");
    }

    private void deleteRecommendation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int recommendationId = Integer.parseInt(req.getParameter("recommendationId"));

        Recommendations recommendation = new Recommendations(recommendationId);
        recommendationsDao.delete(recommendation);
        resp.sendRedirect("recommendations.jsp");
    }

    private void getRecommendation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int recommendationId = Integer.parseInt(req.getParameter("recommendationId"));

        Recommendations recommendation = recommendationsDao.getRecommendationByID(recommendationId);
        req.setAttribute("recommendation", recommendation);
        req.getRequestDispatcher("/viewRecommendation.jsp").forward(req, resp);
    }
}
