package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import students.DataWrapper;
import students.Student;


public class Client {
	private static final int TCP_PORT = 9000;
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			InetAddress address = InetAddress.getByName("localhost");
			try(Socket socket = new Socket(address, TCP_PORT);) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				String cmd = "";
				while(true) {
					printHeader();
					cmd = scan.nextLine();
					if("0".equals(cmd))
						break;
					switch(cmd) {
					case "1":
						out.writeObject(new DataWrapper("CREATE", create()));;
						out.flush();
						DataWrapper createResponse = (DataWrapper) in.readObject();
						if(((String) createResponse.getData()).startsWith("OK"))
							System.out.println("Contact created!");
						else if(((String) createResponse.getData()).startsWith("ERROR")) {
							String[] createSplit = ((String) createResponse.getData()).split("\\|", 2);
							System.out.println(createSplit[1]);
						}
						break;
					case "2":
						out.writeObject(new DataWrapper("GET", null));
						out.flush();
						List<Student> contacts = getAll(in);
						contacts.forEach(System.out::println);
						break;
					case "3":
						Student student = update();
						out.writeObject(new DataWrapper("UPDATE", student));
						out.flush();
						DataWrapper updateResponse = (DataWrapper) in.readObject();
						if(((String) updateResponse.getData()).startsWith("OK"))
							System.out.println("Contact updated!");
						else if(((String) updateResponse.getData()).startsWith("ERROR")) 
							System.out.println(((String) updateResponse.getData()).split("\\|", 2)[1]);
						break;
					case "4":
						out.writeObject(new DataWrapper("DELETE", delete()));
						out.flush();
						DataWrapper deleteResponse = (DataWrapper) in.readObject();
						String[] deleteResponseSplit = ((String) deleteResponse.getData()).split("\\|", 2);
						if(((String) deleteResponse.getData()).startsWith("OK"))
							System.out.println("Contact has been deleted!");
						else if("ERROR".equals(deleteResponseSplit[0]))
							System.out.println(deleteResponseSplit[1]);
						break;
					case "5":
						out.writeObject(new DataWrapper("GRADE", addGrade()));
						out.flush();
						DataWrapper gradeResponse = (DataWrapper) in.readObject();
						String[] gradeResponseSplit = ((String) gradeResponse.getData()).split("\\|", 2);
						if(((String) gradeResponse.getData()).startsWith("OK"))
							System.out.println("Grade has been added!");
						else if("ERROR".equals(gradeResponseSplit[0]))
							System.out.println(gradeResponseSplit[1]);
						break;
					default:
						System.out.println("Invalid command!");
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private static Student create() {
		System.out.print("Index: ");
		String index = scan.nextLine();
		System.out.print("First name: ");
		String firstName = scan.nextLine();
		System.out.print("Last name: ");
		String lastName = scan.nextLine();
		return new Student(index, firstName, lastName);
	}
	
	private static List<Student> getAll(ObjectInputStream in) {
		try {
			DataWrapper data = (DataWrapper) in.readObject();
			return (List<Student>) data.getData();
		} catch (Exception e) {
			e.printStackTrace();
			return new LinkedList<Student>();
		}
	}
	
	private static Student update() {
		System.out.print("Student's index to update: ");
		String index = scan.nextLine();
		System.out.print("First name: ");
		String firstName = scan.nextLine();
		System.out.print("Last name: ");
		String lastName = scan.nextLine();
		return new Student(index, firstName, lastName);
	}
	
	private static String delete() {
		System.out.print("Enter id of student to delete: ");
		String id = scan.nextLine();
		return id;
	}
	
	private static String addGrade() {
		System.out.print("Student's index to update: ");
		String index = scan.nextLine();
		System.out.print("Subject: ");
		String subject = scan.nextLine();
		System.out.print("Grade: ");
		String grade = scan.nextLine();
		return index + "|" + subject + "|" + grade;
	}
	
	private static void printHeader() {
		System.out.println("\n############################################################");
		System.out.println("1 - create student\t2 - show students\t3 - update student\t4 - delete student\t5 - add grade\t0 - exit");
		System.out.println("############################################################\n");
	}
}
