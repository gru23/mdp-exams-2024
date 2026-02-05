package payment.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;

import rmi.client.PaymentClient;

public class ServerThread extends Thread {
	private static final String TRANSACTIONS_FOLDER = "transactions";
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private PaymentClient paymentRMI;
	
	public ServerThread(Socket socket) {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			paymentRMI = new PaymentClient();
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			Integer merchantId = in.readInt();
			Integer buyersId = in.readInt();
			System.out.println(merchantId + " connected with " + buyersId);
			
			Integer n = in.readInt();
			
			boolean isPayed = paymentRMI.paymentRMI.payment(buyersId, merchantId, n);
			if(isPayed) {
				out.writeObject("Succsseful transaction!");
				out.flush();
				writeFile(buyersId, n);
			}
			else {
				out.writeObject("Not enough money on account!");
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Transaction interrupted: " + e.getMessage());
		}
		System.out.println("Transaction ended");
	}
	
	private void writeFile(Integer buyersId, Integer amount) {
		File file = new File(TRANSACTIONS_FOLDER);
		if(!file.exists())
			file.mkdirs();
		TransactionEntity transaction = new TransactionEntity(buyersId, amount);
		String json = new Gson().toJson(transaction);
		String fileName = TRANSACTIONS_FOLDER + File.separator + buyersId + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy_HH.mm")) + ".json";
		try(PrintWriter writer = new PrintWriter(new File(fileName))) {
			writer.write(json);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
