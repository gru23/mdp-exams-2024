package rmi.server;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ContactServer implements ContactServerInterface {
	private static final String PATH = "resources";

	@Override
	public boolean isEmailValid(String email) throws RemoteException {
		return email.contains("@");
	}

	public static void main(String[] args) {
		System.setProperty("java.security.policy", PATH + File.separator + "server_policyfile.txt");
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		try {
			ContactServer server = new ContactServer();
			ContactServerInterface stub = (ContactServerInterface) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Contact", stub);
			System.out.println("RMI server started");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
