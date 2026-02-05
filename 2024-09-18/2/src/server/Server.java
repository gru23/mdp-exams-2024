package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int TCP_PORT = 9000;
	
	public static void main(String[] args) {
		try(ServerSocket ss = new ServerSocket(TCP_PORT)) {
			System.out.println("Server started");
			while(true) {
				Socket socket = ss.accept();
				new ServerThread(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
