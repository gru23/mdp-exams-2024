package contacts.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import contacts.models.Contact;

public class ContactDAO {
	private static final String FILE_PATH = "contacts/contacts.json";
	
	private Gson gson;
	
	public ContactDAO() {
		this.gson = new Gson();
		File file = new File("contacts");
		if(!file.exists())
			file.mkdirs();
	}
	
	public List<Contact> readAll() {
		try {
			String lines = Files.readString(Path.of(FILE_PATH));
			if(lines.isBlank())
				return new LinkedList<Contact>();
			Type type = new TypeToken<List<Contact>>() {}.getType();
			return gson.fromJson(lines, type);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Problem with reading from json file!");
			return new LinkedList<Contact>();
		}
	}
	
	public void writeAll(List<Contact> contacts) {
		try(PrintWriter out = new PrintWriter(FILE_PATH)) {
			out.write(gson.toJson(contacts));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Problem with writing into json file!");
		}
	}
}
