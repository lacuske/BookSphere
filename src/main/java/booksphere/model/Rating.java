package booksphere.model;

public class Rating {
	protected int ratingID;
	protected int rating;
	protected Users user;
	protected Books book;
	
	public Rating(int ratingID, int rating, Users user, Books book) {
		this.ratingID = ratingID;
		this.rating = rating;
		this.user = user;
		this.book = book;
	}

	public Rating(int rating, Users user, Books book) {
		this.rating = rating;
		this.user = user;
		this.book = book;
	}

	public Rating(int ratingID) {
		super();
		this.ratingID = ratingID;
	}

	public int getRatingID() {
		return ratingID;
	}

	public void setRatingID(int ratingID) {
		this.ratingID = ratingID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Books getBook() {
		return book;
	}

	public void setBook(Books book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Rating [ratingID=" + ratingID + ", rating=" + rating + ", user=" + user + ", book=" + book + "]";
	}
	
	
	
	
}
