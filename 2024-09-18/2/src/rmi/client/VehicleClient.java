package rmi.client;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.server.VehicleServerInterface;

public class VehicleClient {
	private static final String PATH = "resources" + File.separator + "client_policyfile.txt";
	
	public VehicleServerInterface vehicleRMI;

	public VehicleClient() {
		System.setProperty("java.security.policy", PATH);
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			vehicleRMI = (VehicleServerInterface) registry.lookup("VehicleRMI");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
