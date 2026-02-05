package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import rmi.client.VehicleClient;
import vehicles.DataWrapper;
import vehicles.Vehicle;

public class ServerThread extends Thread {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private VehicleClient vehicleClient;
	
	public ServerThread(Socket socket) {
		vehicleClient = new VehicleClient();
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}
	
	@Override
	public void run() {
		DataWrapper request;
		String cmd = "";
		while(true) {
			try {
				request = (DataWrapper) in.readObject();
				cmd = request.getType();
				switch(cmd) {
					case "GET_ALL":
						out.reset();
						out.writeObject(vehicleClient.vehicleRMI.findAll());
						out.flush();
						break;
					case "GET":
						out.reset();
						out.writeObject(vehicleClient.vehicleRMI.findById((String) request.getData()));
						out.flush();
						break;
					case "CREATE":
						vehicleClient.vehicleRMI.add((Vehicle) request.getData());
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
