package buffer.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class MDPBufferClient {
	private static final int TCP_PORT = 9000;

	public static void main(String[] args) {
		try {
			InetAddress address = InetAddress.getByName("localhost");
			try(Socket socket = new Socket(address, TCP_PORT)) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.flush();
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				
				Scanner scan = new Scanner(System.in);
				String cmd = "";
				while(!"q".equalsIgnoreCase(cmd)) {
					printHeader();
					System.out.print("Command: ");
					cmd = scan.nextLine();
					switch(cmd) {
						case "a":
							out.writeObject("TIME");
							out.flush();
							String time = (String) in.readObject();
							System.out.println(time);
							break;
						case "b":
							out.writeObject("PING");
							out.flush();
							String ping = (String) in.readObject();
							System.out.println(ping);
							break;
						case "c":
							System.out.print("n = ");
							int amount = scan.nextInt();
							out.writeObject("INFO");
							out.flush();
							out.writeInt(amount);
							out.flush();
							String infoResponse = (String) in.readObject();
							if("OK".equals(infoResponse)) {
								List<String> infos = (List<String>) in.readObject();
								infos.forEach(System.out::println);
							} else {
								String error = (String) in.readObject();
								System.out.println(error);
							}
							break;
						case "q":
							break;
						default:
							System.out.println("Invalid command!");
							break;
					}
				}
				scan.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private static void printHeader() {
		System.out.println("#############################################################");
		System.out.println("a. TIME\tb. PING\tc.INFO\tq. QUIT");
		System.out.println("#############################################################");
	}
}
