import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.etfbl.years.JokeEntity;

public class Main {
	private static final String BASE_URL = "http://localhost:8080/2024-05-08_2/api/jokes";

	public static void main(String[] args) throws Exception {
		if(args.length < 1) {
			System.err.println("Must set command line arguments!");
			return;
		}
		int N = Integer.parseInt(args[0]);
		if(N > 50) {
			System.err.println("N < 50");
			return;
		}
			
		try {
			startAPIService(N);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Scanner scan = new Scanner(System.in);
		String cmd = "";
		while(!"0".equalsIgnoreCase(cmd)) {
			System.out.println();
			System.out.println("##############################################################################");
			System.out.println("1 - IDs\t2 - DESCENDING IDs\t3 - GREATER THEN\t4 - JOKE BY RANDOM ID\t0 - QUIT");
			System.out.println("##############################################################################");
			cmd = scan.nextLine();
			switch (cmd) {
			case "1":
				showAllIDs();
				break;
			case "2":
				showAllIDsDescending();
				break;
			case "3":
				
				System.out.print("Enter id: ");
				String input = scan.nextLine();
				showAllIDsGreater(input);
				break;
			case "4":
				showRandom();
				break;
			case "0":
				break;
			default:
				System.out.println("Invalid input");
				break;
			}
			System.out.println();
		}
		scan.close();
	}

	private static void startAPIService(int N) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(BASE_URL + "/start/" + N).openConnection();
		conn.setRequestMethod("POST");
		
		int status = conn.getResponseCode();
		if(status > 300)
			System.out.println("Greska! Status = " + status);
	}
	
	private static void showAllIDs() throws Exception {
		InputStream os = new URL(BASE_URL).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(os, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null) 
			sb.append(line);
		Type type = new TypeToken<List<String>>() {}.getType();
		List<String> ids = new Gson().fromJson(sb.toString(), type);
		for(String id : ids)
			System.out.print(id + " ");
	}
	
	private static void showAllIDsDescending() throws Exception {
		InputStream os = new URL(BASE_URL + "/descending").openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(os, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null) 
			sb.append(line);
		Type type = new TypeToken<List<String>>() {}.getType();
		List<String> ids = new Gson().fromJson(sb.toString(), type);
		for(String id : ids)
			System.out.print(id + " ");
	}
	
	private static void showAllIDsGreater(String idQuery) throws Exception {
		InputStream os = new URL(BASE_URL + "/greater?id=" + idQuery).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(os, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null) 
			sb.append(line);
		Type type = new TypeToken<List<String>>() {}.getType();
		List<String> ids = new Gson().fromJson(sb.toString(), type);
		for(String id : ids)
			System.out.print(id + " ");
	}
	
	private static void showRandom() throws Exception {
		String randomId = String.valueOf(new Random().nextInt(300));
		InputStream os = new URL(BASE_URL + "/" + randomId).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(os, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null) 
			sb.append(line);
		JokeEntity joke = new Gson().fromJson(sb.toString(), JokeEntity.class);
		System.out.println(joke);
	}
}
