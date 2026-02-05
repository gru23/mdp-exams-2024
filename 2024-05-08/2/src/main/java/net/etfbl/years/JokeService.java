package net.etfbl.years;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import net.etfbl.exceptions.JokeAPIException;

public class JokeService {
	private static final String JOKE_API = "https://official-joke-api.appspot.com/";
	private static final String RANDOM_JOKE_ENDPOINT = "random_joke";
	private final JokeDAO jokeDAO;
	
	private static Gson gson = new Gson(); 
	
	public JokeService() {
		this.jokeDAO = new JokeDAO();
		
	}
	
	public void startApp(int N) throws JokeAPIException {
			for(int i = 0; i < N; i++) {
				getRandomJoke();
			}
			System.out.println("Jokes fetched. THE END");
			System.out.println("PATH: " + getFilePath());
	}
	
	/**
	 * U ovom slucaju se ucitava JSON jer to ovaj API vraca dok je u zadatku trebao biti plain text. U slucaju sa plain
	 * text-om, iz ove metode treba izbaciti dio sa GSON-om.
	 * @return
	 */
	private JokeEntity getRandomJoke() throws JokeAPIException {
		try (
			InputStream is = new URL(JOKE_API + RANDOM_JOKE_ENDPOINT).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))
					) {
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line = br.readLine()) != null)
				sb.append(line);
			JokeEntity joke = gson.fromJson(sb.toString(), JokeEntity.class);
			System.out.println(joke);
			jokeDAO.writeJoke(joke);
			return joke;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new JokeAPIException("Problems with fetching joke from official-joke-api.appspot.com");
		}
	}
	
	public JokeEntity getJokeById(String id) throws JokeAPIException {
		try (
			InputStream is = new URL(JOKE_API + "jokes/" + id).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))
					) {
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line = br.readLine()) != null)
				sb.append(line);
			JokeEntity joke = gson.fromJson(sb.toString(), JokeEntity.class);
			if(joke.getId() == null)
				throw new JokeAPIException("Joke does not exist with this id!");
			return joke;
		} catch (Exception e) {
			e.printStackTrace();
			throw new JokeAPIException("Problems with fetching joke from official-joke-api.appspot.com");
		}
	}
	
	public String getFilePath() {
		return JokeDAO.FULL_PATH;//System.getProperty("user.dir") + File.separator + JokeDAO.FULL_PATH;
	}
	
	public List<String> getAllIds() {
		return jokeDAO.readJokes();
	}
	
	public List<String> getAllIdsDescending() {
		return jokeDAO.readJokes()
				.stream()
				.sorted((id1, id2) -> Integer.parseInt(id2) - Integer.parseInt(id1))
				.collect(Collectors.toList());
				
	}
	
	public List<String> getAllIdsGreaterThen(String id) {
		return jokeDAO.readJokes()
				.stream()
				.filter(idFile -> Integer.parseInt(idFile) > Integer.parseInt(id))
				.collect(Collectors.toList());				
	}
}
