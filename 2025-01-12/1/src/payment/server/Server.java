package payment.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static final int TCP_PORT = 9000;
	
	public static void main(String[] args) {
		try(ServerSocket ss = new ServerSocket(TCP_PORT)) {
			while(true) {
				Socket socket = ss.accept();
				new ServerThread(socket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
