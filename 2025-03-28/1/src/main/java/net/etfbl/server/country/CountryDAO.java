package net.etfbl.server.country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.etfbl.models.Country;

public class CountryDAO {
	private static final String URL = "https://restcountries.com/v3.1/all?fields=name&fields=currencies&fields=region";
	
	private static Map<String, Country> countries;
	private static List<String> regions;
	
	public CountryDAO() {
		initialize();
	}
	
	public Map<String, Country> getCountries() {
		if(countries == null) 
			initialize();
		return countries;
	}
	
	public List<String> getRegions() {
		if(regions == null) 
			initialize();
		return regions;
	}
	
	private void initialize() {
		if(countries != null) 
			return;
		countries = new HashMap<String, Country>();
		regions = new LinkedList<String>();
		try(InputStream in = new URL(URL).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")))
		) {	
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null) 
				sb.append(line);
			Type type = new TypeToken<List<Country>>() {}.getType();
			List<Country> countriesList = new Gson().fromJson(sb.toString(), type);
			countriesList.forEach(c -> countries.put(c.getName().getCommon(), c));
			regions = countriesList.stream()
				.map(c -> c.getRegion())
				.distinct()
				.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
