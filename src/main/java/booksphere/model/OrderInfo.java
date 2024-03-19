package booksphere.model;

public class OrderInfo {
    private int orderID;
    private int userID;
    private long bookID;
    private OrderStatus status;
    
    public OrderInfo() {
    }
    
    public OrderInfo(int userID, long bookID, OrderStatus status) {
        this.userID = userID;
        this.bookID = bookID;
        this.status = status;
    }
    
    public int getOrderID() {
        return orderID;
    }
    
    public void setOrderID(int orderID) {
        this.orderID = orderID;
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
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderID=" + orderID +
                ", userID=" + userID +
                ", bookID=" + bookID +
                ", status=" + status +
                '}';
    }
}

enum OrderStatus {
    ACTIVE,
    FINISHED,
    FAILED,
    PENDING
}
