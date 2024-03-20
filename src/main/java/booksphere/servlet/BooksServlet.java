package booksphere.servlet;

import booksphere.dal.BooksDao;
import booksphere.dal.AuthorDao;
import booksphere.dal.PublisherDao;
import booksphere.model.Books;
import booksphere.model.Author;
import booksphere.model.Publisher;
import booksphere.model.Books.BookType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/books")
public class BooksServlet extends HttpServlet {
    protected BooksDao booksDao;
    protected AuthorDao authorDao;
    protected PublisherDao publisherDao;

    @Override
    public void init() throws ServletException {
        booksDao = BooksDao.getInstance();
        authorDao = AuthorDao.getInstance();
        publisherDao = PublisherDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("create".equals(action)) {
            createBook(req, resp);
        } else if ("update".equals(action)) {
            updateBook(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("delete".equals(action)) {
            deleteBook(req, resp);
        } else if ("get".equals(action)) {
            getBook(req, resp);
        }
    }
    
    private void createBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String isbn = req.getParameter("isbn");
            String title = req.getParameter("title");
            int authorId = Integer.parseInt(req.getParameter("authorId"));
            int bookYear = Integer.parseInt(req.getParameter("bookYear"));
            int publisherId = Integer.parseInt(req.getParameter("publisherId"));
            BookType bookType = BookType.valueOf(req.getParameter("bookType"));
            Author author = authorDao.getAuthorById(authorId);
            Publisher publisher = publisherDao.getPublisherByPublisherID(publisherId);

            Books book = new Books(isbn, title, author, bookYear, publisher, bookType);
            booksDao.create(book);
            resp.sendRedirect("viewBooks.jsp"); // Redirect to a page to view the book list
        } catch (SQLException e) {
            throw new ServletException("Error creating book", e);
        }
    }
    
    private void updateBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long bookId = Long.parseLong(req.getParameter("bookId"));
        try {
            Books book = booksDao.getBookByBookID(bookId);
            if (book == null) {
                resp.getWriter().print("Book not found");
                return;
            }
            book.setIsbn(req.getParameter("isbn"));
            book.setTitle(req.getParameter("title"));
            book.setBookYear(Integer.parseInt(req.getParameter("bookYear")));
            book.setBookType(BookType.valueOf(req.getParameter("bookType")));
            int authorId = Integer.parseInt(req.getParameter("authorId"));
            int publisherId = Integer.parseInt(req.getParameter("publisherId"));
            book.setAuthor(authorDao.getAuthorById(authorId));
            book.setPublisher(publisherDao.getPublisherByPublisherID(publisherId));
            
            booksDao.update(book);
            resp.sendRedirect("viewBooks.jsp"); // Redirect to a page to view the book list
        } catch (SQLException e) {
            throw new ServletException("Error updating book", e);
        }
    }
    
    private void deleteBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long bookId = Long.parseLong(req.getParameter("bookId"));
        try {
            Books book = new Books();
            book.setBookID(bookId);
            booksDao.delete(book);
            resp.sendRedirect("viewBooks.jsp"); // Redirect to a page to view the book list
        } catch (SQLException e) {
            throw new ServletException("Error deleting book", e);
        }
    }
    
    private void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long bookId = Long.parseLong(req.getParameter("bookId"));
        try {
            Books book = booksDao.getBookByBookID(bookId);
            req.setAttribute("book", book);
            req.getRequestDispatcher("/viewBookDetails.jsp").forward(req, resp); // Forward to a JSP to display the book
        } catch (SQLException e) {
            throw new ServletException("Error retrieving book", e);
        }
    }
}
