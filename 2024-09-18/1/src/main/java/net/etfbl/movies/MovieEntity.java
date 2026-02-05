package net.etfbl.movies;

public class MovieEntity {
	private Integer id;
	private String title;
	private String genres;
	private Integer year;
	
	public MovieEntity() {
		super();
	}

	public MovieEntity(Integer id, String title, String genres, Integer year) {
		super();
		this.id = id;
		this.title = title;
		this.genres = genres;
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "MovieEntity [title=" + title + ", genre=" + genres + ", year=" + year + "]";
	}	
}
