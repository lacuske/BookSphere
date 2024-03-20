package booksphere.model;

public class Recommendations {
    private int recommendationID;
    protected Users user;
    protected Books book;
    protected Publisher publisher;
    private boolean recommend;
    
    public Recommendations() {
    }
    
    public Recommendations(int recommendationID) {
    	this.recommendationID = recommendationID;
    }
    
    public Recommendations(Users user, Books book, Publisher publisher, boolean recommend) {
        this.user = user;
        this.book = book;
        this.publisher = publisher;
        this.recommend = recommend;
    }
    
    public Recommendations(int recommendationID, Users user, Books book, Publisher publisher, boolean recommend) {
        this.user = user;
        this.book = book;
        this.publisher = publisher;
        this.recommend = recommend;
        this.recommendationID = recommendationID;
    }
    
    public int getRecommendationID() {
        return recommendationID;
    }
    
    public void setRecommendationID(int recommendationID) {
        this.recommendationID = recommendationID;
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
    
    public void setBookID(Books book) {
        this.book = book;
    }
    
    public Publisher getPublisher() {
        return publisher;
    }
    
    public void setPublisherID(Publisher publisher) {
        this.publisher = publisher;
    }
    
    public boolean isRecommend() {
        return recommend;
    }
    
    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
    
}
