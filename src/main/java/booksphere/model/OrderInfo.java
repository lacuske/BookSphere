package booksphere.model;

public class OrderInfo {
    private int orderID;
    protected Users user;
    protected Books book;
    private OrderStatus status;
    
    public OrderInfo() {
    }
    
    public OrderInfo(Users user, Books book, OrderStatus status) {
        this.user = user;
        this.book = book;
        this.status = status;
    }
    
    public int getOrderID() {
        return orderID;
    }
    
    public void setOrderID(int orderID) {
        this.orderID = orderID;
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
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
}

enum OrderStatus {
    ACTIVE,
    FINISHED,
    FAILED,
    PENDING
}
