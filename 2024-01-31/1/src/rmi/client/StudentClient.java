package rmi.client;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.server.StudentServerInterface;

public class StudentClient {
	private static final String PATH = "resources";
	
	public StudentServerInterface studentRMI;
	
	public StudentClient() {
		System.setProperty("java.security.policy", PATH + File.separator + "client_policyfile.txt");
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			this.studentRMI = (StudentServerInterface) registry.lookup("Student");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
