package contacts.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import contacts.models.Contact;

public class Client {
	private static final int TCP_PORT = 9000;
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			InetAddress addr = InetAddress.getByName("localhost");
			try (Socket socket = new Socket(addr, TCP_PORT)) {
				//Socket socket = new Socket(addr, TCP_PORT);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				String cmd = "";
				while(true) {
					printHeader();
					cmd = scan.nextLine();
					if("0".equals(cmd))
						break;
					switch(cmd) {
						case"1":
							out.println("CREATE|" + create());
							String createResponse = in.readLine();
							if(createResponse.startsWith("OK"))
								System.out.println("Contact created!");
							else if(createResponse.startsWith("ERROR")) {
								String[] createSplit = createResponse.split("\\|", 2);
								System.out.println(createSplit[1]);
							}
							break;
						case"2":
							out.println("GET|");
							List<Contact> contacts = getAll(in);
							contacts.forEach(System.out::println);
							break;
						case"3":
							out.println("UPDATE|" + update());
							String updateResponse = in.readLine();
							if(updateResponse.startsWith("OK"))
								System.out.println("Contact updated!");
							else if(updateResponse.startsWith("ERROR"))
								System.out.println(updateResponse.split("\\|", 2)[1]);
							break;
						case"4":
							out.println("DELETE|" + delete());
							String deleteResponse = in.readLine();
							if("OK".equals(deleteResponse))
								System.out.println("Contact has been deleted!");
							else if("NOT FOUND".equals(deleteResponse.split("\\|", 2)[1]))
								System.out.println("Contact not found!");
							break;
						case"5":
							out.println("MAIL|" + mail());
							String line = in.readLine();
							if(line.startsWith("OK"))
								System.out.println("E-mail added!");
							else if(line.startsWith("ERROR"))
								System.out.println(line.split("\\|", 2)[1]);
							break;
						default:
							System.out.println("Invalid command!");
							break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void printHeader() {
		System.out.println("\n############################################################");
		System.out.println("1 - create contact\t2 - show contacts\t3 - update contact\t4 - delete contact\t5 - add e-mail\t0 - exit");
		System.out.println("############################################################\n");
	}
	
	private static String create() {
		System.out.print("First name: ");
		String firstName = scan.nextLine();
		System.out.print("Last name: ");
		String lastName = scan.nextLine();
		System.out.print("Phone: ");
		String phone = scan.nextLine();
		System.out.print("E-mail: ");
		String mail = scan.nextLine();
		Contact contact = new Contact(firstName, lastName, phone, mail);
		return new Gson().toJson(contact);
	}
	
	private static List<Contact> getAll(BufferedReader in) {
		try {
			String line = in.readLine();
			Type type = new TypeToken<List<Contact>>() {}.getType();
			return new Gson().fromJson(line, type);
		} catch (IOException e) {
			e.printStackTrace();
			return new LinkedList<Contact>();
		}
	}
	
	private static String update() {
		System.out.print("Contact's id to update: ");
		String id = scan.nextLine();
		System.out.print("First name: ");
		String firstName = scan.nextLine();
		System.out.print("Last name: ");
		String lastName = scan.nextLine();
		Contact contact = new Contact(id, firstName, lastName);
		return new Gson().toJson(contact);
	}
	
	private static String mail() {
		System.out.print("Contact's id to add e-mail address: ");
		String id = scan.nextLine();
		System.out.print("E-mail: ");
		String email = scan.nextLine();
		return id + "|" + email;
	}
	
	private static String delete() {
		System.out.print("Enter id of contact to delete: ");
		String id = scan.nextLine();
		return id;
	}
}
