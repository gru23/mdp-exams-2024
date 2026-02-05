package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import exceptions.IllegalArgumentException;

public interface PaymentServerInterface extends Remote {
	List<Integer> getMerchants(Integer buyersId) throws RemoteException, IllegalArgumentException;
	boolean payment(Integer buyersId, Integer merchantsId, Integer amount) throws RemoteException, IllegalArgumentException;
	boolean doesBuyerExist(Integer buyersId) throws RemoteException;
	boolean doesMerchantExist(Integer merchantId) throws RemoteException;
}
