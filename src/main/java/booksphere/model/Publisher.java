package booksphere.model;

public class Publisher {
    private int publisherID;
    private String publisherName;
    private String biography;
    
    public Publisher() {
    }
    
    public Publisher(String publisherName) {
        this.publisherName = publisherName;
    }
    
    public Publisher(int publisherID) {
        this.publisherID = publisherID;
    }
    
    public Publisher(String publisherName, String biography) {
        this.publisherName = publisherName;
        this.biography = biography;
    }
    
    public int getPublisherID() {
        return publisherID;
    }
    
    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }
    
    public String getPublisherName() {
        return publisherName;
    }
    
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    
    public String getBiography() {
        return biography;
    }
    
    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    // toString() method
    @Override
    public String toString() {
        return "Publisher{" +
                "publisherID=" + publisherID +
                ", publisherName='" + publisherName + '\'' +
                ", biography='" + biography + '\'' +
                '}';
    }
}
