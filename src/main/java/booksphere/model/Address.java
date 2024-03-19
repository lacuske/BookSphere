
package booksphere.model;

public class Address {
	protected int addressID;
	protected String street1;
	protected String street2;
	protected String city;
	protected String state;
	protected String zip;
	protected boolean billingAddress;
	protected boolean mailingAddress;
	protected Users user;
	
	public Address(int addressID, String street1, String street2, String city, String state, String zip,
			boolean billingAddress, boolean mailingAddress, Users user) {
		this.addressID = addressID;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.billingAddress = billingAddress;
		this.mailingAddress = mailingAddress;
		this.user = user;
	}

	public Address(int addressID) {
		this.addressID = addressID;
	}
	
	

	public Address(String street1, String street2, String city, String state, String zip, boolean billingAddress,
			boolean mailingAddress, Users user) {
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.billingAddress = billingAddress;
		this.mailingAddress = mailingAddress;
		this.user = user;
	}

	/** Getters and setters. */
	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public boolean isBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(boolean billingAddress) {
		this.billingAddress = billingAddress;
	}

	public boolean isMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(boolean mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Address [addressID=" + addressID + ", street1=" + street1 + ", street2=" + street2 + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", billingAddress=" + billingAddress + ", mailingAddress="
				+ mailingAddress + ", user=" + user + "]";
	}
	
	
}
