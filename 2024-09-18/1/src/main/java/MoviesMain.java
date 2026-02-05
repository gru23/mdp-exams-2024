import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.etfbl.movies.MovieEntity;

public class MoviesMain {
	private static final String BASE_URL = "http://localhost:8080/2024-09-18_1/api/movies";
	private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "movies";
	
	private static Gson gson = new Gson();
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		List<String> genres;
		int id;
		int command = -1;
		while(0 != command) {
			genres = getGenres();
			printHeader(genres);
			System.out.print("Genre's id: ");
			id = scan.nextInt();
			String genre = genres.get(id - 1);
			List<MovieEntity> movies = printAllMoviesByGenre(genre);
			
			System.out.println("1 - Sačuvaj rezultat pretrage u fajl\t 2 - Nastavi pretragu\t 0 - Izlaz");
			command = scan.nextInt();
			if(command == 1) {
				writeSearch(movies);
			}
		}

	}
	
	private static List<String> getGenres() {
		try(InputStream is = new URL(BASE_URL + "/genres").openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));) {
			
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null)
				sb.append(line);
			Type type = new TypeToken<List<String>>() {}.getType();
			return gson.fromJson(sb.toString(), type);
		} catch (IOException e) {
			e.printStackTrace();
			return new LinkedList<String>();
		}
	}
	
	private static List<MovieEntity> printAllMoviesByGenre(String genre) {
		try(InputStream is = new URL(BASE_URL + "/genres/" + genre).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))
		) {	
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null)
				sb.append(line);
			Type type = new TypeToken<List<MovieEntity>>() {}.getType();
			List<MovieEntity> movies = gson.fromJson(sb.toString(), type);
			movies.forEach(System.out::println);
			return movies;
		} catch (IOException e) {
			return new LinkedList<MovieEntity>();
		}
	}
	
	private static void writeSearch(List<MovieEntity> movies) {
		String path = FILE_PATH + File.separator 
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss"))
				+ ".txt";
		try(PrintWriter pw = new PrintWriter(path)) {
			movies.forEach(m -> pw.println(m));
			System.out.println("PATH: " + path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private static void printHeader(List<String> genres) {
		System.out.println("############################################");
		for(int i = 0; i < genres.size(); i++) {
			System.out.print((i + 1) + " : " + genres.get(i) + "  ");
		}
		System.out.println("\n############################################");
	}
}
