package rmi.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import vehicles.Vehicle;

public class VehicleServer implements VehicleServerInterface {
	private static final String PATH = "resources";
	private static final String VEHICLES_PATH = "resources" + File.separator + "vehices.bin";
	private static List<Vehicle> vehicles;
	
	
	public VehicleServer() {
		loadFromFile();
	}

	public static void main(String[] args) {
		System.setProperty("java.security.policy", PATH + File.separator + "server_policyfile.txt");
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		VehicleServer server = new VehicleServer();
		try {
			VehicleServerInterface stub = (VehicleServerInterface) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("VehicleRMI", stub);
			System.out.println("RMI server started");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadFromFile() {
		File file = new File(VEHICLES_PATH);
		if(!file.exists()) {
			vehicles = new LinkedList<Vehicle>();
			return;
		}
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(VEHICLES_PATH))) {
			vehicles = (List<Vehicle>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			vehicles = new LinkedList<>();
		}
	}
	
	private synchronized void saveToFile() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(VEHICLES_PATH))) {
			oos.writeObject(vehicles);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Vehicle> findAll() throws RemoteException {
		return new LinkedList<Vehicle>(vehicles);
	}

	@Override
	public Vehicle findById(String id) throws RemoteException {
		Optional<Vehicle> optional = vehicles.stream()
			.filter(v -> id.equals(v.getId()))
			.findFirst();
		if(optional.isEmpty())
			return null;
		return optional.get();
	}

	@Override
	public synchronized void add(Vehicle newVehicle) throws RemoteException {
		Vehicle vehicle = findById(newVehicle.getId());
		if(vehicle != null) {
			vehicle.setManufacturer(newVehicle.getManufacturer());
			vehicle.setModel(newVehicle.getModel());
		}
		else
			vehicles.add(newVehicle);
		saveToFile();
	}
}
