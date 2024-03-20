package booksphere.servlet;

import booksphere.dal.AuthorDao;
import booksphere.model.Author;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/author")
public class AuthorServlet extends HttpServlet {
    protected AuthorDao authorDao;
    
    @Override
    public void init() throws ServletException {
        authorDao = AuthorDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("create".equals(action)) {
            createAuthor(req, resp);
        } else if ("update".equals(action)) {
            updateAuthor(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("delete".equals(action)) {
            deleteAuthor(req, resp);
        } else if ("get".equals(action)) {
            getAuthor(req, resp);
        }
    }
    
    private void createAuthor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authorName = req.getParameter("authorName");
        String biography = req.getParameter("biography");
        
        try {
            Author author = new Author(authorName, biography);
            authorDao.create(author);
            resp.sendRedirect("author?action=get&authorID=" + author.getAuthorID()); // Redirect to view the created author
        } catch (SQLException e) {
            throw new ServletException("Error creating author", e);
        }
    }
    
    private void updateAuthor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int authorID = Integer.parseInt(req.getParameter("authorID"));
        String newBiography = req.getParameter("biography");
        
        try {
            Author author = authorDao.getAuthorById(authorID);
            if (author == null) {
                resp.getWriter().print("Author not found");
                return;
            }
            authorDao.update(author, newBiography);
            resp.sendRedirect("author?action=get&authorID=" + author.getAuthorID()); // Redirect to view the updated author
        } catch (SQLException e) {
            throw new ServletException("Error updating author", e);
        }
    }
    
    private void deleteAuthor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int authorID = Integer.parseInt(req.getParameter("authorID"));
        
        try {
            Author author = new Author();
            author.setAuthorID(authorID);
            authorDao.delete(author);
            resp.sendRedirect("listAuthors.jsp"); // Redirect to the list of authors
        } catch (SQLException e) {
            throw new ServletException("Error deleting author", e);
        }
    }
    
    private void getAuthor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int authorID = Integer.parseInt(req.getParameter("authorID"));
        
        try {
            Author author = authorDao.getAuthorById(authorID);
            req.setAttribute("author", author);
            req.getRequestDispatcher("/viewAuthor.jsp").forward(req, resp); // Forward to a JSP to display the author
        } catch (SQLException e) {
            throw new ServletException("Error retrieving author", e);
        }
    }
}
