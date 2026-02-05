package net.etfbl.movies;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MovieDAO {
//	private static final String PATH = "movies" + File.separator + "movies.json";
	private static final String PATH = System.getProperty("user.home") + File.separator + "movies" + File.separator + "movies.json";
	
	private static List<MovieEntity> movies;
	private static int nextId;
	
	public MovieDAO() {
		if(movies == null)
			readMovies();
	}
	
	public List<MovieEntity> findAll() {
		return new LinkedList<MovieEntity>(movies);
	}
	
	public Optional<MovieEntity> findById(Integer id) {
		return movies.stream()
					.filter(m -> id.equals(m.getId()))
					.findFirst();
	}
	
	public synchronized MovieEntity create(MovieEntity request) {
		MovieEntity newMovie = new MovieEntity(nextId++, request.getTitle(), request.getGenres(), request.getYear());
		movies.add(newMovie);
		return newMovie;
	}
	
	public synchronized void deleteById(Integer id) {
		movies.removeIf(m -> id.equals(m.getId()));
	}
	
	private synchronized void readMovies() {
		try {
			String content = Files.readString(Path.of(PATH));
			Type type = new TypeToken<List<MovieEntity>>() {}.getType();
			movies = new Gson().fromJson(content, type);
			for(int i = 0; i < movies.size(); i++)
				movies.get(i).setId(i + 1);
			nextId = movies.size() + 1;
		} catch (IOException e) {
			movies = new LinkedList<MovieEntity>();
			e.printStackTrace();
		}
	}
}
