package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import vehicles.DataWrapper;
import vehicles.Vehicle;

public class Client {
	private static final int TCP_PORT = 9000;
	
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			InetAddress address = InetAddress.getByName("localhost");
			try(Socket socket = new Socket(address, TCP_PORT)) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				String cmd = "";
				while(!"0".equals(cmd)) {
					printHeader();
					System.out.print("Choose an option: ");
					cmd = scan.nextLine();
					if("0".equals(cmd))
						break;
					switch(cmd) {
						case "1":
							out.reset();
							out.writeObject(new DataWrapper("CREATE", createVehicle()));
							out.flush();
							System.out.println("Vehicle created");
							break;
						case "2":
							out.reset();
							out.writeObject(new DataWrapper("GET_ALL", new LinkedList<Vehicle>()));
							out.flush();
							List<Vehicle> vehicles = (List<Vehicle>) in.readObject();
							vehicles.forEach(System.out::println);
							break;
						case "3":
							out.reset();
							out.writeObject(new DataWrapper("GET", getById()));
							out.flush();
							Vehicle searched = (Vehicle) in.readObject();
							System.out.println(searched);
							break;
						default:
							System.out.println("Invalid command");
							break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private static Vehicle createVehicle() {
		System.out.print("Chassis number: ");
		String id = scan.nextLine();
		System.out.print("Manufacturer: ");
		String manufacturer = scan.nextLine();
		System.out.println("Model: ");
		String model = scan.nextLine();
		return new Vehicle(id, manufacturer, model);
	}

	private static String getById() {
		System.out.print("Chassis number: ");
		String id = scan.nextLine();
		return id;
	}
	
	private static void printHeader() {
		System.out.println("#########################################");
		System.out.println("1 - ADD VEHICLE\t2 - SHOW ALL\t3 - SHOW BY ID\t0 - QUIT");
		System.out.println("#########################################");
	}
}
