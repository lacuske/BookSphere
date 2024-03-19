package booksphere.model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionID;
    private long cardNumber;
    private int bookID;
    private Timestamp transactionDate;
    private int userID;
    private int orderID;
    
    public Transaction() {
    }
    
    public Transaction(long cardNumber, int bookID, Timestamp transactionDate, int userID, int orderID) {
        this.cardNumber = cardNumber;
        this.bookID = bookID;
        this.transactionDate = transactionDate;
        this.userID = userID;
        this.orderID = orderID;
    }
    
    public int getTransactionID() {
        return transactionID;
    }
    
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    
    public long getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public int getBookID() {
        return bookID;
    }
    
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
    
    public Timestamp getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public int getOrderID() {
        return orderID;
    }
    
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", cardNumber=" + cardNumber +
                ", bookID=" + bookID +
                ", transactionDate=" + transactionDate +
                ", userID=" + userID +
                ", orderID=" + orderID +
                '}';
    }
}
