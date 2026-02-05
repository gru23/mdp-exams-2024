package turistinfo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import hotels.DataWrapper;
import hotels.Hotel;

public class TuristInfoClient {
	private static final int TCP_PORT = 9000;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		try {
			InetAddress address = InetAddress.getByName("localhost");
			try(Socket socket = new Socket(address, TCP_PORT)) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				String cmd = "";
				while(!"0".equals(cmd)) {
					printHeader();
					cmd = scan.nextLine();
					switch(cmd) {
						case "1":
							out.reset();
							out.writeObject(new DataWrapper("CATEGORY", Integer.valueOf(3)));
							out.flush();
							List<Hotel> hotelsThree = (List<Hotel>) in.readObject();
							hotelsThree.forEach(System.out::println);
							break;
						case "2":
							out.reset();
							out.writeObject(new DataWrapper("CATEGORY", Integer.valueOf(4)));
							out.flush();
							List<Hotel> hotelsFour = (List<Hotel>) in.readObject();
							hotelsFour.forEach(System.out::println);
							break;
						case "3":
							out.reset();
							out.writeObject(new DataWrapper("CITY", "Banja Luka"));
							out.flush();
							List<Hotel> hotelsBL = (List<Hotel>) in.readObject();
							hotelsBL.forEach(System.out::println);
							break;
						case "4":
							out.reset();
							out.writeObject(new DataWrapper("GET", null));
							out.flush();
							List<Hotel> hotels = (List<Hotel>) in.readObject();
							hotels.stream()
								.sorted((h1, h2) -> h1.getName().compareToIgnoreCase(h2.getName()))
								.forEach(System.out::println);
							break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			scan.close();
		}
		scan.close();
	}
	
	private static void printHeader() {
		System.out.println("##############################################");
		System.out.println("1 - THREE STARS\t2 - FOUR STARTS\t3 - HOTELS IN BL\t4 - SORTED HOTELS\t0 - QUIT");
		System.out.println("##############################################");
	}
}
