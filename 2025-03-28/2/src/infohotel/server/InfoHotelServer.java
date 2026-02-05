package infohotel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InfoHotelServer {
	private static final int TCP_PORT = 9000;
	
	public static void main(String[] args) {
		try(ServerSocket ss = new ServerSocket(TCP_PORT)) {
			System.out.println("Server started");
			while(true) {
				Socket socket = ss.accept();
				new InfoHotelServerThread(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
