package net.etfbl.movies;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.etfbl.exceptions.NotFoundException;

public class MovieService {
	private final MovieDAO movieDAO;
	
	public MovieService() {
		this.movieDAO = new MovieDAO();
	}
	
	public List<MovieEntity> getAll() {
		return movieDAO.findAll();
	}
	
	public List<MovieEntity> getAllByGenre(String genre) {
		return movieDAO.findAll().stream()
				.filter(m -> doesGenresContains(m.getGenres(), genre))
				.collect(Collectors.toList());
	}
	
	public MovieEntity getByTitle(String title) throws NotFoundException {
		return movieDAO.findAll().stream()
	               .filter(m -> title.equalsIgnoreCase(m.getTitle()))
	               .findFirst()
	               .orElseThrow(() -> new NotFoundException("Not found movie with title " + title));
		//moj stari kod
//		Optional<MovieEntity> optionalMovie = movieDAO.findAll().stream()
//												.filter(m -> title.equalsIgnoreCase(m.getTitle()))
//												.findFirst();
//		if(optionalMovie.isEmpty())
//			throw new NotFoundException("Not found movie with title " + title);
//		return optionalMovie.get();
	}
	
	public List<String> getAllGenres() {
		return movieDAO.findAll().stream()
					.flatMap(m -> getGenresList(m.getGenres()).stream())
					.distinct()
					.collect(Collectors.toList());
	}
	
	public MovieEntity create(MovieEntity newMovie) {
		// mozda pozvati metodu za dodavne zanrova u GenreDAO
		return movieDAO.create(newMovie);
	}
	
	public void deleteById(Integer id) throws NotFoundException {
		Optional<MovieEntity> optional = movieDAO.findById(id);
		if(optional.isEmpty())
			throw new NotFoundException("Not found movie with id " + id);
		movieDAO.deleteById(id);
	}
	
	private boolean doesGenresContains(String allGenres, String genre) {
		String[] split = allGenres.split("\\|");
		for(String g : split) {
			if(genre.equalsIgnoreCase(g))
				return true;
		}
		return false;
	}
	
	private List<String> getGenresList(String genresString) {
		List<String> genres =
			    new LinkedList<String>(Arrays.asList(genresString.split("\\|")));
		return genres.stream()
					.distinct()
					.collect(Collectors.toList());		
	}
}
