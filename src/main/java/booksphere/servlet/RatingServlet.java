package booksphere.servlet;

import booksphere.dal.BooksDao;
import booksphere.dal.RatingDao;
import booksphere.dal.UsersDao;
import booksphere.model.Rating;
import booksphere.model.Users;
import booksphere.model.Books;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rating")
public class RatingServlet extends HttpServlet {

    private RatingDao ratingDao;
    private UsersDao usersDao;
    private BooksDao booksDao;

    @Override
    public void init() throws ServletException {
        ratingDao = RatingDao.getInstance();
        usersDao = UsersDao.getInstance();
        booksDao = BooksDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("create".equals(action)) {
                createRating(req, resp);
            } else if ("update".equals(action)) {
                updateRating(req, resp);
            }
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Error processing rating: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("delete".equals(action)) {
                deleteRating(req, resp);
            } else if ("get".equals(action)) {
                getRating(req, resp);
            }
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Error processing rating: " + e.getMessage(), e);
        }
    }

    private void createRating(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        String isbn = req.getParameter("isbn");
        int ratingValue = Integer.parseInt(req.getParameter("rating"));

        Users user = usersDao.getUserByUserID(userId);
        Books book = booksDao.getBookByISBN(isbn);
        Rating rating = new Rating(ratingValue, user, book);
        ratingDao.create(rating);
        resp.sendRedirect("ratings.jsp");
    }

    private void updateRating(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        int ratingId = Integer.parseInt(req.getParameter("ratingId"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        String isbn = req.getParameter("isbn");
        int newRatingValue = Integer.parseInt(req.getParameter("rating"));

        Rating rating = ratingDao.getRatingByRatingID(ratingId);
        if (rating == null) {
            resp.getWriter().print("Rating not found");
            return;
        }

        rating.setUser(new Users(userId));
        rating.setBook(new Books(isbn));
        rating.setRating(newRatingValue);
        ratingDao.update(rating);
        resp.sendRedirect("ratings.jsp");
    }

    private void deleteRating(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        int ratingId = Integer.parseInt(req.getParameter("ratingId"));

        Rating rating = new Rating(ratingId);
        ratingDao.delete(rating);
        resp.sendRedirect("ratings.jsp");
    }

    private void getRating(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ServletException {
        int ratingId = Integer.parseInt(req.getParameter("ratingId"));

        Rating rating = ratingDao.getRatingByRatingID(ratingId);
        req.setAttribute("rating", rating);
        req.getRequestDispatcher("/viewRating.jsp").forward(req, resp);
    }
}
