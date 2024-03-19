package booksphere.model;

public class Recommendation {
    private int recommendationID;
    private int userID;
    private long bookID;
    private int publisherID;
    private boolean recommend;
    
    public Recommendation() {
    }
    
    public Recommendation(int userID, long bookID, int publisherID, boolean recommend) {
        this.userID = userID;
        this.bookID = bookID;
        this.publisherID = publisherID;
        this.recommend = recommend;
    }
    
    public int getRecommendationID() {
        return recommendationID;
    }
    
    public void setRecommendationID(int recommendationID) {
        this.recommendationID = recommendationID;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public long getBookID() {
        return bookID;
    }
    
    public void setBookID(long bookID) {
        this.bookID = bookID;
    }
    
    public int getPublisherID() {
        return publisherID;
    }
    
    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }
    
    public boolean isRecommend() {
        return recommend;
    }
    
    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
    
    @Override
    public String toString() {
        return "Recommendation{" +
                "recommendationID=" + recommendationID +
                ", userID=" + userID +
                ", bookID=" + bookID +
                ", publisherID=" + publisherID +
                ", recommend=" + recommend +
                '}';
    }
}
