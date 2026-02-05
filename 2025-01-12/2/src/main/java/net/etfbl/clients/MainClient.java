package net.etfbl.clients;

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

import net.etfbl.books.BookEntity;
import net.etfbl.orders.OrderEntity;
import net.etfbl.orders.OrderRequest;
import net.etfbl.user.Costs;
import net.etfbl.user.UserEntity;

public class MainClient {
	private final static String BASE_URL = "http://localhost:8080/2025-01-12/api";
	
	private static Gson gson = new Gson();

	public static void main(String[] args) throws Exception {
		printAllUsers();
		bookReview("1");
		printAllUsers();
		reviewOrderedBooks("1");
		order("1", "1");
		reviewCosts("1");
		printAllUsers();
		reviewOrderedBooks("1");
		order("1", "2");
		reviewCosts("1");
		reviewOrderedBooks("1");
		printAllUsers();
	}

	private static void printAllUsers() throws Exception {
		InputStream is = new URL(BASE_URL + "/users").openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		Type type = new TypeToken<List<UserEntity>>() {}.getType();
		List<UserEntity> users = gson.fromJson(sb.toString(), type);
		System.out.println("#ALL USERS#");
		users.forEach(System.out::println);
		System.out.println(users.get(0).getReviews());
	}
	
	private static void bookReview(String userId) throws Exception {
		InputStream is = new URL(BASE_URL + "/books/" + userId).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		Type type = new TypeToken<List<BookEntity>>() {}.getType();
		List<BookEntity> books = gson.fromJson(sb.toString(), type);
		System.out.println("#BOOK REVIEW#");
		books.forEach(System.out::println);
	}
	
	private static void reviewCosts(String userId) throws Exception {
		InputStream is = new URL(BASE_URL + "/users/" + userId + "/costs").openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		String line = "";
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine()) != null)
			sb.append(line);
		Costs costs = gson.fromJson(sb.toString(), Costs.class);
		System.out.println("#COSTS#");
		System.out.println(costs);
	}
	
	private static void reviewOrderedBooks(String userId) throws Exception {
		InputStream is = new URL(BASE_URL + "/users/" + userId + "/orders").openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null)
			sb.append(line);
		Type type = new TypeToken<List<OrderEntity>>() {}.getType();
		List<OrderEntity> orders = gson.fromJson(sb.toString(), type);
		System.out.println("#ORDERS REVIEW#");
		orders.forEach(System.out::println);	//ovdje je u stvari trebalo ispisati nazive knjiga a ne posudjivanja...
	}
	
	private static void order(String userId, String bookId) throws Exception {
		OrderRequest order = new OrderRequest(userId, bookId);
		URL url = new URL(BASE_URL + "/users/orders");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		JSONObject input = new JSONObject(order);
		OutputStream os = conn.getOutputStream();
		os.write(input.toString().getBytes());
		os.flush();
//		if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
//			throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
		int status = conn.getResponseCode();
		if (status >= 300) {
		    throw new RuntimeException("Failed: HTTP error code: " + status);
		}
		os.close();
		conn.disconnect();
	}
}
