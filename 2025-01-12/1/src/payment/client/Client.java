package payment.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import rmi.client.PaymentClient;

public class Client {
	private static final int TCP_PORT = 9000;
	private static List<Integer> buyersIds;
	
	private static PaymentClient paymentRMI = new PaymentClient();

	public static void main(String[] args) {
		buyersIds = new LinkedList<Integer>();
		buyersIds.add(10);
		buyersIds.add(11);
		Integer buyersId = buyersIds.get(new Random().nextInt(buyersIds.size()));
		try {
			InetAddress address = InetAddress.getByName("localhost");
			try(Socket socket = new Socket(address, TCP_PORT)) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Scanner scan = new Scanner(System.in);
				
				List<Integer> merchants = paymentRMI.paymentRMI.getMerchants(buyersId);
				printMerchants(merchants);
				
				System.out.println("Enter merchant id: ");
				Integer merchantId = scan.nextInt();
				out.writeInt(merchantId);
				out.flush();
				
				out.writeInt(buyersId);
				out.flush();
				System.out.println(merchantId + " connected with " + buyersId);
				
				System.out.print("n = ");
				Integer n = scan.nextInt();
				out.writeInt(n);
				out.flush();
				
				String response = (String) in.readObject();
				System.out.println(response);
				System.out.println("Connection closed!");
				
				scan.close();
				in.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printMerchants(List<Integer> merchants) {
		System.out.print("Merchants:");
		merchants.forEach(m -> System.out.print(m + " "));
	}
}
