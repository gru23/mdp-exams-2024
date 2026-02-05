package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import students.Student;

public interface StudentServerInterface extends Remote {
	
	boolean write(Student student, String subject, Integer grade) throws RemoteException;
}
