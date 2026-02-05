import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.etfbl.books.BookDTO;
import net.etfbl.books.BookEntity;
import net.etfbl.models.AuthorEntity;
import net.etfbl.models.PublisherEntity;

/*
 * 1. zadatak 2024-09-04 je fakitcki isti kao ovaj samo sto je potrebno na serverskoj strani
 * obraditi nevalidne podatke pa cu taj dio uraditi u ovom projektu
 */
public class Main {
	private final static String BASE_URL = "http://localhost:8080/2024-01-31_2/api/books";
	private static Gson gson = new Gson();

	public static void main(String[] args) {
		try {
			reviewBooks();
			reviewBooksByPublishersName("Vulkan");
			addBook(new BookEntity("1", "Count of The Monte Cristo", new AuthorEntity("Alexander", "Dumas"), 
					new PublisherEntity("Otvorena knjiga", "adresa 1")));
			addBook(new BookEntity("2", "Count of The Monte Cristo", new AuthorEntity("Alexander", "Dumas"), 
					new PublisherEntity("Vulkan", "adresa 2")));
			reviewBooks();
			reviewBooksWithTwoFields();
			reviewBookById("1");
			delete("1");
			reviewBooksByPublishersName("Vulkan");
			reviewBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void reviewBooks() throws Exception {
		InputStream is = new URL(BASE_URL).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		Type type = new TypeToken<List<BookEntity>>() {}.getType();
		List<BookEntity> books = gson.fromJson(sb.toString(), type);
		System.out.println("#BOOK REVIEWS#");
		books.forEach(System.out::println);
		br.close();
		is.close();
	}
	
	private static void reviewBooksWithTwoFields() throws Exception {
		InputStream is = new URL(BASE_URL + "/id-and-title").openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null) 
			sb.append(line);
		Type type = new TypeToken<List<BookDTO>>() {}.getType();
		List<BookDTO> books = gson.fromJson(sb.toString(), type);
		System.out.println("#BOOK DTO REVIEWS#");
		books.forEach(System.out::println);
		br.close();
		is.close();
	}
	
	private static void addBook(BookEntity book) throws Exception {
		URL url = new URL(BASE_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		JSONObject input = new JSONObject(book);
		OutputStream os = conn.getOutputStream();	//ovo sa os je izgleda upis u body zahtjeva koji se salje
		os.write(input.toString().getBytes());
		os.flush();
		int status = conn.getResponseCode();
		if(status > 300)
			throw new RuntimeException("Failed HTTP error code: " + status);
		os.close();
		conn.disconnect();
	}
	
	private static void reviewBookById(String id) throws Exception {
		InputStream is = new URL(BASE_URL + "/" + id).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = br.readLine()) != null)
			sb.append(line);
		System.out.println("#BOOK BY ID#");
		System.out.println(gson.fromJson(sb.toString(), BookEntity.class));
	}
	
	private static void reviewBooksByPublishersName(String publisherName) throws Exception {
		InputStream is = new URL(BASE_URL + "/publishers/" + publisherName).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null) 
			sb.append(line);
		Type type = new TypeToken<List<BookEntity>>() {}.getType();
		List<BookEntity> books = gson.fromJson(sb.toString(), type);
		System.out.println("#BY PUBLISHERS#");
		books.forEach(System.out::println);
	}
	
	private static void delete(String id) throws Exception {
		URL url = new URL(BASE_URL + "/" + id);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("DELETE");
		int status = conn.getResponseCode();	//Zahtjev se stvarno šalje u ovom momentu
		if (status >= 300) {
			throw new RuntimeException("HTTP error code: " + status);
		}
		conn.disconnect();
	}
}
