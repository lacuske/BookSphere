package booksphere.servlet;

import booksphere.dal.ReviewDao;
import booksphere.model.Review;
import booksphere.model.Books;
import booksphere.model.Users;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    protected ReviewDao reviewDao;

    @Override
    public void init() throws ServletException {
        reviewDao = ReviewDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            createReview(req, resp);
        } else if ("update".equals(action)) {
        	// no update action for review
            // updateReview(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            deleteReview(req, resp);
        } else if ("get".equals(action)) {
            getReview(req, resp);
        }
    }

    private void createReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        long bookId = Long.parseLong(req.getParameter("bookId"));
        String reviewContent = req.getParameter("review");

        try {
            Users user = new Users(userId);
            Books book = new Books(bookId);
            Review review = new Review(user, book, reviewContent);
            reviewDao.create(review);
            resp.sendRedirect("reviews.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error creating review", e);
        }
    }

//    private void updateReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        int reviewId = Integer.parseInt(req.getParameter("reviewId"));
//        String reviewContent = req.getParameter("review");
//
//        try {
//            Review review = reviewDao.getReviewByReviewID(reviewId);
//            if (review == null) {
//                resp.getWriter().print("Review not found");
//                return;
//            }
//            review.setReview(reviewContent);
//            reviewDao.update(review);
//            resp.sendRedirect("reviews.jsp");
//        } catch (SQLException e) {
//            throw new ServletException("Error updating review", e);
//        }
//    }

    private void deleteReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int reviewId = Integer.parseInt(req.getParameter("reviewId"));

        try {
            Review review = new Review(reviewId);
            reviewDao.delete(review);
            resp.sendRedirect("reviews.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error deleting review", e);
        }
    }

    private void getReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int reviewId = Integer.parseInt(req.getParameter("reviewId"));

        try {
            Review review = reviewDao.getReviewByReviewID(reviewId);
            req.setAttribute("review", review);
            req.getRequestDispatcher("/viewReview.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving review", e);
        }
    }
}
