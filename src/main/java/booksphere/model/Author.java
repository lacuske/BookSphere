package booksphere.model;

public class Author {
    private int authorID;
    private String authorName;
    private String biography;
    
    public Author() {
    }
    
    public Author(String authorName) {
        this.authorName = authorName;
    }
    
    public Author(String authorName, String biography) {
        this.authorName = authorName;
        this.biography = biography;
    }
    
    public int getAuthorID() {
        return authorID;
    }
    
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
        return "Author{" +
                "authorID=" + authorID +
                ", authorName='" + authorName + '\'' +
                ", biography='" + biography + '\'' +
                '}';
    }
}
