package rmi.client;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.server.ContactServerInterface;

public class ContactClient {
	private static final String PATH = "resources";
	
	public ContactServerInterface contactRMI;
	
	public ContactClient() {
		System.setProperty("java.security.policy", PATH + File.separator + "client_policyfile.txt");
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			this.contactRMI = (ContactServerInterface) registry.lookup("Contact");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
