package info;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MDPInfoServer {
	private static final String HOST = "224.0.0.11";
	private static final int PORT = 20000;
	
	private static int counter = 1;

	public static void main(String[] args) {
		try(MulticastSocket socket = new MulticastSocket()) {
			InetAddress address = InetAddress.getByName(HOST);
			socket.joinGroup(address);
			String message;
			byte[] buffer = new byte[256];
			while(true) {
				try {
					message = "Poruka-" + counter++;
					buffer = message.getBytes();
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
					socket.send(packet);
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
