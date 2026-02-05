package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vehicles.Vehicle;

public interface VehicleServerInterface extends Remote {
	List<Vehicle> findAll() throws RemoteException;
	Vehicle findById(String id) throws RemoteException;
	void add(Vehicle newVehicle) throws RemoteException;
}
