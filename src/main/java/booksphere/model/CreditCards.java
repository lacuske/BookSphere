
package booksphere.model;
import java.util.Date;

public class CreditCards {
	protected long cardNumber;
	protected Date expirationDate;
	protected Users user;
	protected Address address;
	
	public CreditCards(long cardNumber, Date expirationDate, Users user, Address address) {
		super();
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		this.user = user;
		this.address = address;
	}

	public CreditCards(long cardNumber) {
		super();
		this.cardNumber = cardNumber;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CreditCards [cardNumber=" + cardNumber + ", expirationDate=" + expirationDate + ", user=" + user
				+ ", address=" + address + "]";
	}
	
	
}
