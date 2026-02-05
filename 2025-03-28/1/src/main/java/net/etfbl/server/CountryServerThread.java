package net.etfbl.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;

import com.google.gson.Gson;

import net.etfbl.models.Country;

public class CountryServerThread extends Thread {
	public static final String HOST = "224.0.0.11";
	public static final int PORT = 20000;
	
	private final List<Country> countries;
	
	private volatile boolean running = true;

	public CountryServerThread(List<Country> countries) {
		this.countries = countries;
	}
	
	public void stopRunning() {
		running = false;
	}
	
	@Override
	public void run() {
		try(MulticastSocket socket = new MulticastSocket()) {
			InetAddress address = InetAddress.getByName(HOST);
			socket.joinGroup(address);
			Gson gson = new Gson();
			String countriesString = gson.toJson(countries);
			byte[] buffer = countriesString.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
			while(running) {				
				socket.send(packet);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
