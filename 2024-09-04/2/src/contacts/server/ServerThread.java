package contacts.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import contacts.models.Contact;
import exceptions.DuplicateException;
import exceptions.InvalidFormatException;
import exceptions.NotFoundException;

public class ServerThread extends Thread {
	private final ContactService service;
	
	private Gson gson;
	
	private PrintWriter out;
	private BufferedReader in;
	
	public ServerThread(Socket socket) {
		this.service = new ContactService();
		this.gson = new Gson();
		try {
			this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}
	
	@Override
	public void run() {
		String cmd = "";
		String line = "";
		String[] split;
		while(true) {
			try {
				line = in.readLine();
				split = line.split("\\|", 2);
				cmd = split[0];
				switch(cmd) {
					case "GET":
						out.println(gson.toJson(service.getAll()));
						break;
					case "CREATE":
						try {
							service.add(gson.fromJson(split[1], Contact.class));
							out.println("OK");
						} catch(DuplicateException | InvalidFormatException e) {
							out.println("ERROR|" + e.getMessage());
						}
						break;
					case "DELETE":
					try {
						service.deleteById(split[1]);
						out.println("OK");
					} catch (NotFoundException e) {
						e.printStackTrace();
						out.println("ERROR|NOT FOUND");
					}
						break;
					case "UPDATE":
					try {
						service.update(gson.fromJson(split[1], Contact.class));
						out.println("OK");
					} catch (NotFoundException e) {
						e.printStackTrace();
						out.println("ERROR|NOT FOUND");
					}
						break;
					case "MAIL":
						String[] splitMail = split[1].split("\\|", 2);
					try {
						service.addEmail(splitMail[0], splitMail[1]);
						out.println("OK");
					} catch (NotFoundException | DuplicateException | InvalidFormatException e) {
						out.println("ERROR|" + e.getMessage());
					} 
						break;
					default:
					    out.println("ERROR|UNKNOWN COMMAND");
					    break;
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
