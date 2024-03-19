package booksphere.model;

import java.sql.Timestamp;

public class Transactions {
    private int transactionID;
    protected CreditCards creditCard;
    protected Books book;
    private Timestamp transactionDate;
    protected Users user;
    protected OrderInfo orderInfo;
    
    public Transactions() {
    }
    
    public Transactions(CreditCards creditCard, Books book, Timestamp transactionDate, Users user, OrderInfo orderInfo) {
        this.creditCard = creditCard;
        this.book = book;
        this.transactionDate = transactionDate;
        this.user = user;
        this.orderInfo = orderInfo;
    }
    
    public int getTransactionID() {
        return transactionID;
    }
    
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    
    public CreditCards getCardNumber() {
        return creditCard;
    }
    
    public void setCardNumber(CreditCards creditCard) {
        this.creditCard = creditCard;
    }
    
    public Books getBook() {
        return book;
    }
    
    public void setBook(Books book) {
        this.book = book;
    }
    
    public Timestamp getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public Users getUser() {
        return user;
    }
    
    public void setUser(Users user) {
        this.user = user;
    }
    
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
    
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    
}
