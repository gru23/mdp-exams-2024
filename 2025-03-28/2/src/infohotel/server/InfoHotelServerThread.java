package infohotel.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import hotels.DataWrapper;
import infohotel.InfoHotelService;

public class InfoHotelServerThread extends Thread {
	private final InfoHotelService service;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public InfoHotelServerThread(Socket socket) {
		this.service = new InfoHotelService();
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		start();
	}
	
	@Override
	public void run() {
		DataWrapper request;
		boolean running = true;
		String cmd = "";
		while(running) {
			try {
				request = (DataWrapper) in.readObject();
				cmd = (String) request.getType();
				switch(cmd) {
					case "GET":
						out.reset();
						out.writeObject(service.getAll());
						out.flush();
						break;
					case "CATEGORY":
						Integer category = (Integer) request.getData();
						out.reset();
						out.writeObject(service.getAllByCategory(category));
						out.flush();
						break;
					case "CITY":
						String city = (String) request.getData();
						out.reset();
						out.writeObject(service.getAllByCity(city));
						out.flush();
						break;
					default:
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				running = false;
			}
		}
	}
}
