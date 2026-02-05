package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContactServerInterface extends Remote {

	boolean isEmailValid(String email) throws RemoteException;
}
