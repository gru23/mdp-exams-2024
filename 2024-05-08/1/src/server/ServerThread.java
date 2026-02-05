package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import blogs.Blog;
import blogs.Comment;
import services.BlogService;

public class ServerThread extends Thread {
	private final BlogService service;
//	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Gson gson;
	
	private String username;
	
	public ServerThread(Socket socket) {
		this.service = new BlogService();
		this.gson = new Gson();
//		this.socket = socket;
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
		try {
			username = in.readLine().split("\\|", 2)[1];
			System.out.println("Welcome " + username);
			while(true) {
				String line = in.readLine();
				String[] split = line.split("\\|", 2);
				String option = split[0];
				if("EXIT".equalsIgnoreCase(option))
					break;
				switch(option) {
					case "GET":
						String response = "GET|" + gson.toJson(service.getAll());
						out.println(response);
						System.out.println(username + " requested all blogs");
						break;
					case "CREATE":
						String blogs = addBlog(split[1]);
						out.println("CREATE|" + blogs);
						System.out.println(username + " created a blog");
						break;
					case "UPDATE":
						out.println("UPDATE|" + updateBlog(split[1]));
						System.out.println(username + " updated a blog");
						break;
					case "DELETE":
						String id = split[1];
						service.deleteById(id);
						out.println("DELETE|OK");
						System.out.println(username + " deleted a blog with id " + id);
						break;
					case "COMMENT":
						String[] updateSplit = split[1].split("\\|", 2);
						service.comment(updateSplit[0], gson.fromJson(updateSplit[1], Comment.class));
						break;
					default:
						out.println("Invalid command!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String addBlog(String json) {
		Blog newBlog = gson.fromJson(json, Blog.class);
		return gson.toJson(service.add(newBlog));
	}
	
	private String updateBlog(String json) {
		Blog blog = gson.fromJson(json, Blog.class);
		return gson.toJson(service.update(blog));
	}
}
