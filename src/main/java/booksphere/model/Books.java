package booksphere.model;

public class Books {
    private long bookID;
    private String isbn;
    private String title;
    private Author author;
    private int bookYear;
    private Publisher publisher;
    private BookType bookType;
    
    public Books() {
    }
    
    public Books(String isbn) {
    	this.isbn = isbn;
    }
    
    public Books(String isbn, String title, Author author, int bookYear, Publisher publisher, BookType bookType) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.bookYear = bookYear;
        this.publisher = publisher;
        this.bookType = bookType;
    }
    
    public Books(long bookId) {
		this.bookID = bookId;
	}

	public enum BookType {
        FICTION,
        NON_FICTION,
        MYSTERY,
        THRILLER,
        ROMANCE,
        SCIENCE_FICTION,
        FANTASY,
        HISTORICAL_FICTION,
        BIOGRAPHY,
        AUTOBIOGRAPHY,
        SELF_HELP,
        PHILOSOPHY,
        TRAVEL,
        MEMOIR,
        POETRY,
        DRAMA,
        HORROR,
        COMEDY,
        ADVENTURE,
        YOUNG_ADULT,
        CHILDREN_LITERATURE,
        SATIRE,
        GRAPHIC_NOVEL,
        ESSAY,
        COOKBOOK,
        SCIENCE,
        TECHNOLOGY,
        PSYCHOLOGY,
        SOCIOLOGY,
        ANTHROPOLOGY
    }
    public long getBookID() {
        return bookID;
    }
    
    public void setBookID(long bookID) {
        this.bookID = bookID;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Author getAuthor() {
        return author;
    }
    
    public void setAuthor(Author author) {
        this.author = author;
    }
    
    public int getBookYear() {
        return bookYear;
    }
    
    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }
    
    public Publisher getPublisher() {
        return publisher;
    }
    
    public void setPublisher(Publisher publisher) {
        this.publisher= publisher;
    }
    
    public BookType getBookType() {
        return bookType;
    }
    
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
    
}

