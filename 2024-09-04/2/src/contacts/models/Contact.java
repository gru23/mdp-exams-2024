package contacts.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Contact {
	private String id;
	private String firstName;
	private String lastName;
	private String time;
	private List<String> phones;
	private List<String> emails;
	
	public Contact() {
		super();
	}

	public Contact(String firstName, String lastName, String phone, String email) {
		super();
		this.id = UUID.randomUUID().toString().substring(0, 5);
		this.firstName = firstName;
		this.lastName = lastName;
		this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
		this.phones = new LinkedList<String>();
		this.phones.add(phone);
		this.emails = new LinkedList<String>();
		this.emails.add(email);
	}
	
	public Contact(String id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", time=" + time
				+ ", phones=" + phones + ", emails=" + emails + "]";
	}
}
