package buffer.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MDPBufferServerReceiver extends Thread {
	private static final String HOST = "224.0.0.11";
	private static final int PORT = 20000;
	
	@Override
	public void run() {
		try(MulticastSocket socket = new MulticastSocket(PORT)) {
			byte[] buffer = new byte[256];
			InetAddress address = InetAddress.getByName(HOST);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.joinGroup(address);
			while(true) {
				socket.receive(packet);
				String message = new String(packet.getData(), 0, packet.getLength());
				MDPBufferServer.addMessage(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
