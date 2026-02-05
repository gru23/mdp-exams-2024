package rmi.server;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import exceptions.IllegalArgumentException;

public class PaymentServer implements PaymentServerInterface {
	private static final String PATH = "resources" + File.separator + "server_policyfile.txt";
	
	private static List<Integer> merchants = new LinkedList<Integer>();
	private static Map<Integer, Integer> buyers = new HashMap<Integer, Integer>();

	public static void main(String[] args) {
		merchants.add(1);
		merchants.add(2);
		
		buyers.put(10, 2250);
		buyers.put(11, 1250);
		
		System.setProperty("java.security.policy", PATH);
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		PaymentServer server = new PaymentServer();
		try {
			PaymentServerInterface stub = (PaymentServerInterface) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("PaymentRMI", stub);
			System.out.println("RMI server started");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Override
	public List<Integer> getMerchants(Integer buyersId) throws RemoteException, IllegalArgumentException {
		if(!doesBuyerExist(buyersId))
			throw new IllegalArgumentException("Buyer's ID does not exist!");
		return new LinkedList<Integer>(merchants);
	}

	// u tekstu je navedeno da se stalno provjerava da li postoje kupac i prodavac tako da 
	// ne treba raditi provjere 
	@Override
	public synchronized boolean payment(Integer buyersId, Integer merchantsId, Integer amount) throws RemoteException, IllegalArgumentException {
		if(!doesBuyerExist(buyersId))
			throw new IllegalArgumentException("Buyer's ID does not exist!");
		if(!doesMerchantExist(merchantsId))
			throw new IllegalArgumentException("Merchant's ID does not exist!");
		Integer finalAmount = amount * 10;
		Integer currentAmount = buyers.get(buyersId);
		if((currentAmount - finalAmount) < 0)
			return false;
		buyers.put(buyersId, currentAmount - finalAmount);
		return true;
	}

	@Override
	public boolean doesBuyerExist(Integer buyersId) throws RemoteException {
		return buyers.containsKey(buyersId);
	}
	
	@Override
	public boolean doesMerchantExist(Integer merchantsId) throws RemoteException {
		return merchants.contains(merchantsId);
	}

}
