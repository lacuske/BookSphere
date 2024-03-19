package booksphere.model;

public class Review {
	protected int reviewID;
	protected Users user;
	protected Books book;
	protected String review;
	
	public Review(int reviewID, Users user, Books book, String review) {
		this.reviewID = reviewID;
		this.user = user;
		this.book = book;
		this.review = review;
	}

	public Review(int reviewID) {
		this.reviewID = reviewID;
	}

	//for create review as reviewID is surrogate key
	public Review(Users user, Books book, String review) {
		this.user = user;
		this.book = book;
		this.review = review;
	}

	public int getReviewID() {
		return reviewID;
	}

	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
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

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return "Review [reviewID=" + reviewID + ", user=" + user + ", book=" + book + ", review=" + review + "]";
	}
	
	
	
}
