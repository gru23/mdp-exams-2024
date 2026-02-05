package students;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import exceptions.DuplicateException;
import exceptions.NotFoundException;
import rmi.client.StudentClient;

public class StudentService {
	private final StudentDAO studentDAO;
	
	private StudentClient studentRMI;
	
	public StudentService() {
		this.studentDAO = new StudentDAO();
		this.studentRMI = new StudentClient();
		
	}
	
	public List<Student> getAll() {
//		return studentDAO.findAll();
		return new LinkedList<Student>(studentDAO.findAll());
	}
	
	public List<Student> add(Student newStudent) throws DuplicateException {
		System.out.println(newStudent);
		Optional<Student> optional = studentDAO.findByIndex(newStudent.getIndex());
		if(optional.isPresent())
			throw new DuplicateException("Index already exists");
		return studentDAO.add(newStudent);
	}
	
	public List<Student> update(String index, Student student) throws NotFoundException {
		Optional<Student> optional = studentDAO.findByIndex(index);
		if(optional.isEmpty())
			throw new NotFoundException("Student does not exist with index " + index);
		student.setSubjects(optional.get().getSubjects());
		return studentDAO.update(index, student);
	}
	
	public void deleteByIndex(String index) throws NotFoundException {
		boolean isDeleted = studentDAO.delete(index);
		if(!isDeleted)
			throw new NotFoundException("There is no student with index " + index);
	}
	
	public Student addGrade(String index, String subject, Integer grade) throws NotFoundException {
		Optional<Student> optional = studentDAO.findByIndex(index);
		if(optional.isEmpty())
			throw new NotFoundException("Student not found");
		optional.get().getSubjects().put(subject, grade);
		update(index, optional.get());
		try {
			studentRMI.studentRMI.write(optional.get(), subject, grade);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return optional.get();
	}
}
