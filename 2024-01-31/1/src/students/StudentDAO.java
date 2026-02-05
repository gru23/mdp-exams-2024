package students;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import exceptions.NotFoundException;

public class StudentDAO {
	public static List<Student> students = new LinkedList<Student>();
	
	static {
		Student marko = new Student("11130/19", "Marko", "Markovic");
		Student pero = new Student("11131/19", "Pero", "Peric");
		students.add(marko);
		students.add(pero);
	}
	
	public List<Student> findAll() {
		return students;
	}
	
	public Optional<Student> findByIndex(String index) {
		return students.stream()
				.filter(s -> index.equals(s.getIndex()))
				.findFirst();
	}
	
	public List<Student> add(Student newStudent) {
		students.add(newStudent);
		return students;
	}
	
	public List<Student> update(String index, Student student) throws NotFoundException {
		Student updated = findByIndex(index).get();
		updated.setIndex(student.getIndex());
		updated.setFirstName(student.getFirstName());
		updated.setLastName(student.getLastName());
		updated.setSubjects(student.getSubjects());
		return students;
	}
	
	public boolean delete(String index) {
		return students.removeIf(s -> index.equals(s.getIndex()));
	}
}
