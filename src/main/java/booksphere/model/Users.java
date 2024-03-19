
package booksphere.model;

public class Users {
	protected int userID;
	protected String location;
	protected int age;
	protected String firstName;
	protected String lastName;
	protected String password;
	
	public Users(int userID, String location, int age, String firstName, String lastName, String password) {
		this.userID = userID;
		this.location = location;
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public Users(int userID) {
		super();
		this.userID = userID;
	}

	
	public Users(String location, int age, String firstName, String lastName, String password) {
		super();
		this.location = location;
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	/** Getters and setters. */
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Users [userID=" + userID + ", location=" + location + ", age=" + age + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password + "]";
	}
	
}

