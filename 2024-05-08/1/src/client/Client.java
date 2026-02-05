package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import blogs.Blog;
import blogs.Comment;

public class Client {
	private static final int TCP_PORT = 9000;
	private static Scanner scan = new Scanner(System.in);
	
	private static String username = "";
	//napraviti komentairanjse blogova
	public static void main(String[] args) {
		try {
			InetAddress addr = InetAddress.getByName("localhost");
			Socket socket = new Socket(addr, TCP_PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
//			Scanner scan = new Scanner(System.in);
			System.out.print("Username: ");
			username = scan.nextLine();
			out.println("USERNAME|" + username);
			String cmd = "";
			while(true) {
				printHeader();
				cmd = scan.nextLine();
				if("0".equals(cmd))
					break;
				switch(cmd) {
					case "1":
						out.println("CREATE|" + createBlog());
						in.readLine();
						System.out.println("Blog created");
						break;
					case "2":
						out.println("GET|");
						readBlogs(in);
						break;
					case "3":
						out.println("UPDATE|" + updateBlog());
						in.readLine();
						System.out.println("Blog updated");
						break;
					case "4":
						deleteBlog(out);
						String deleteResponse = in.readLine();
						boolean isDeleted = deleteResponse.split("\\|", 2)[1].equalsIgnoreCase("OK");
						if(isDeleted)
							System.out.println("Blog has been deleted");
						else
							System.out.println("Blog has not been deleted");
						break;
					case "5":
						System.out.print("Id of blog to comment: ");
						String blogId = scan.nextLine();
						out.println("COMMENT|" + blogId + "|" + comment());
						break;
					default:
						System.out.println("Invalid command");
				}
			}
			scan.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void printHeader() {
		System.out.println("############################################################");
		System.out.println("1 - create blog\t2 - show blogs\t3 - update blog\t4 - delete blog\t5 - comment\t0 - exit");
		System.out.println("############################################################");
	}
	
	private static String createBlog() {
		System.out.print("Title: ");
		String title = scan.nextLine();
		System.out.print("Text: ");
		String text = scan.nextLine();
		Blog newBlog = new Blog(username, title, text);
		return new Gson().toJson(newBlog);
	}
	
	private static void readBlogs(BufferedReader in) throws IOException {
		String line = in.readLine();
		String[] split = line.split("\\|", 2);
		Type type = new TypeToken<List<Blog>>() {}.getType();
		printBlogs(new Gson().fromJson(split[1], type));
	}
	
	private static String updateBlog() {
		System.out.print("Id: ");
		String id = scan.nextLine();
		System.out.print("Title: ");
		String title = scan.nextLine();
		System.out.print("Text: ");
		String text = scan.nextLine();
		Blog newBlog = new Blog(id, username, title, text);
		return new Gson().toJson(newBlog);
	}
	
	private static void deleteBlog(PrintWriter out) {
		System.out.print("Enter id of blog to be deleted: ");
		String id = scan.nextLine();
		out.println("DELETE|" + id);
	}
	
	private static String comment() {
		System.out.print("Comment: ");
		String commentString = scan.nextLine();
		Comment comment = new Comment(username, commentString);
		return new Gson().toJson(comment);
	}
	
	private static void printBlogs(List<Blog> blogs) { 
		System.out.println("BLOGS: ");
		blogs.forEach(System.out::println);
	}
}
