package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import exceptions.DuplicateException;
import exceptions.NotFoundException;
import students.DataWrapper;
import students.Student;
import students.StudentService;

public class ServerThread extends Thread {
	private final StudentService service;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ServerThread(Socket socket) {
		this.service = new StudentService();
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}
	
	@Override
	public void run() {
		DataWrapper request;
		String cmd;
		while(true) {
			try {
				request = (DataWrapper) in.readObject();
				cmd = request.getType();
				switch(cmd) {
					case "GET":
						out.reset();
						out.writeObject(new DataWrapper("GET", service.getAll()));
						out.flush();
						break;
					case "CREATE":
					try {
						System.out.println(request.getData());
						List<Student> studenti = service.add((Student) request.getData());
						studenti.forEach(System.out::println);
						out.reset();
						out.writeObject(new DataWrapper("CREATE", "OK"));
						out.flush();
					} catch (DuplicateException e) {
						out.reset();
						out.writeObject(new DataWrapper("CREATE", "ERROR|" + e.getMessage()));
						out.flush();
					}
						break;
					case "UPDATE":
						Student updateStudent = (Student) request.getData();
					try {
						service.update(updateStudent.getIndex(), updateStudent);
						out.reset();
						out.writeObject(new DataWrapper("UPDATE", "OK"));
						out.flush();
					} catch (NotFoundException e) {
						out.writeObject(new DataWrapper("UPDATE", "ERROR|" + e.getMessage()));
						out.flush();
					}
						break;
					case "DELETE":
					try {
						service.deleteByIndex((String) request.getData());
						out.reset();
						out.writeObject(new DataWrapper("DELETE", "OK"));
						out.flush();
					} catch (NotFoundException e) {
						out.reset();
						out.writeObject(new DataWrapper("DELETE", "ERROR|" + e.getMessage()));
						out.flush();
					}
						break;
					case "GRADE":
						String[] gradeSplit = ((String) request.getData()).split("\\|", 3);
					try {
						service.addGrade(gradeSplit[0], gradeSplit[1], Integer.valueOf(gradeSplit[2]));
						out.reset();
						out.writeObject(new DataWrapper("GRADE", "OK"));
						out.flush();
					} catch (NotFoundException e) {
						out.reset();
						out.writeObject(new DataWrapper("GRADE", "ERROR|" + e.getMessage()));
						out.flush();
					}
						break;
					default:
						out.writeObject(new DataWrapper("ERROR", "Invalid command"));
						out.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
