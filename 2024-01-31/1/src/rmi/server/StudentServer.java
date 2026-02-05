package rmi.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import students.Student;

public class StudentServer implements StudentServerInterface {
	private static final String PATH = "resources";
	private static final String STUDENTS_FILE_PATH = "students/students.txt";
	
	@Override
	public boolean write(Student student, String subject, Integer grade) throws RemoteException {
		File folder = new File("students");
		if(!folder.exists())
			folder.mkdirs();
		
		try(PrintWriter out = new PrintWriter(new FileWriter(STUDENTS_FILE_PATH, true))) {
			out.append(student + "|" + subject + "|" + grade + "\n");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		System.setProperty("java.security.policy", PATH + File.separator + "server_policyfile.txt");
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		try {
			StudentServer server = new StudentServer();
			StudentServerInterface stub = (StudentServerInterface) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("Student", stub);
			System.out.println("RMI server started");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
