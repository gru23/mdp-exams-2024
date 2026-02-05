package services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import blogs.Blog;

public class BlogDAO {
	private static final String FOLDER_PATH = "blogs";
	private static final String FILE_PATH = FOLDER_PATH + File.separator + "blogs.json";
	
	private Gson gson;
	
	public BlogDAO() {
		this.gson = new Gson();
		
		File folder = new File(FOLDER_PATH);
		if(!folder.exists())
			folder.mkdirs();
		
	}
	
	public void writeAll(List<Blog> blogs) {
		try(PrintWriter writer = new PrintWriter(FILE_PATH);) {
			writer.write(gson.toJson(blogs));
		} catch(IOException e) {
			System.err.println("Problem with writing into json file!");
		}
	}
	
	public List<Blog> readAll() {
		try {
			String lines = Files.readString(Path.of(FILE_PATH));
			if (lines.isBlank()) return new LinkedList<>();
			
			Type type = new TypeToken<List<Blog>>() {}.getType();
			return gson.fromJson(lines, type);
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Problem with reading from json file!");
		}
		return new LinkedList<Blog>();
	}
}
