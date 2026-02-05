package students;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student implements Serializable {
	private static final long serialVersionUID = 8320799921151997019L;
	
	private String index;
	private String firstName;
	private String lastName;
	private Map<String, Integer> subjects;
	
	public Student() {
		super();
	}

	public Student(String index, String firstName, String lastName) {
		super();
		this.index = index;
		this.firstName = firstName;
		this.lastName = lastName;
		this.subjects = new HashMap<String, Integer>();
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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

	public Map<String, Integer> getSubjects() {
		return subjects;
	}

	public void setSubjects(Map<String, Integer> subjects) {
		this.subjects = subjects;
	}

	@Override
	public String toString() {
		return "Student [index=" + index + ", firstName=" + firstName + ", lastName=" + lastName + ", subjects="
				+ subjects + "]";
	}	
}
