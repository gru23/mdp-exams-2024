import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.etfbl.enums.SecuritiesType;
import net.etfbl.securities.SecuritiesDTO;
import net.etfbl.securities.SecuritiesEntity;
import net.etfbl.transactions.TransactionEntity;

public class Main {
	private static final String BASE_URL = "http://localhost:8080/2024-10-02_1/api/securities";
	
	public static void main(String[] args) throws Exception {
		TransactionEntity t1 = new TransactionEntity(
                100,
                new BigDecimal("12.50"),
                "Kupovina akcija"
        );

        TransactionEntity t2 = new TransactionEntity(
                50,
                new BigDecimal("14.20"),
                "Dodatna kupovina"
        );
        
        List<TransactionEntity> transactions = new ArrayList<>();
        transactions.add(t1);
        transactions.add(t2);

        // Kreiranje SecuritiesEntity objekta
        SecuritiesEntity security = new SecuritiesEntity(
                "SEC-003",
                "Telekom Srbija",
                SecuritiesType.STOCK,
                transactions
        );
        
//		create(security);
//        addTransaction("SEC-001", t2);
		getAll();
		getById("SEC-001");
		getAllByOrder("ASC");
		getAllByOrder("DESC");
	}

	private static void create(SecuritiesEntity securities) throws Exception {
		URL url = new URL(BASE_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		JSONObject input = new JSONObject(securities);
		OutputStream os = conn.getOutputStream();
		os.write(input.toString().getBytes());
		os.flush();
		int status = conn.getResponseCode();
		if(status > 300)
			throw new RuntimeException("Failed HTTP error code: " + status);
		os.close();
		conn.disconnect();
	}
	
	private static void addTransaction(String securitiesId, TransactionEntity trans) throws Exception {
		URL url = new URL(BASE_URL + "/" + securitiesId + "/transactions");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		JSONObject input = new JSONObject(trans);
		OutputStream os = conn.getOutputStream();
		os.write(input.toString().getBytes());
		os.flush();
		int status = conn.getResponseCode();
		if(status > 300)
			throw new RuntimeException("Failed HTTP error code: " + status);
		os.close();
		conn.disconnect();
	}
	
	private static void getAll() throws Exception {
		InputStream is = new URL(BASE_URL).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null)
			sb.append(line);
		Type type = new TypeToken<List<SecuritiesEntity>>() {}.getType();
		List<SecuritiesEntity> trans = new Gson().fromJson(sb.toString(), type);
		System.out.println("#REVIEWS ALL#");
		trans.forEach(System.out::println);
		br.close();
		is.close();
	}
	
	private static void getById(String id) throws Exception {
		InputStream is = new URL(BASE_URL + "/" + id).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null)
			sb.append(line);
		SecuritiesEntity trans = new Gson().fromJson(sb.toString(), SecuritiesEntity.class);
		System.out.println("#REVIEW BY ID#");
		System.out.println(trans);
		br.close();
		is.close();
	}
	
	private static void getAllByOrder(String order) throws Exception {
		InputStream is = new URL(BASE_URL + "/sorted/" + order).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = br.readLine()) != null)
			sb.append(line);
		Type type = new TypeToken<List<SecuritiesDTO>>() {}.getType();
		List<SecuritiesDTO> trans = new Gson().fromJson(sb.toString(), type);
		System.out.println("#REVIEWS ALL BY ORDER#");
		trans.forEach(System.out::println);
		br.close();
		is.close();
	}
}
