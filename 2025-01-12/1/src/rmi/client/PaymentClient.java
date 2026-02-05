package rmi.client;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.server.PaymentServerInterface;

public class PaymentClient {
	private static final String PATH = "resources" + File.separator + "client_policyfile.txt";
	
	public PaymentServerInterface paymentRMI;
	
	public PaymentClient() {
		System.setProperty("java.security.policy", PATH);
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			paymentRMI = (PaymentServerInterface) registry.lookup("PaymentRMI");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
