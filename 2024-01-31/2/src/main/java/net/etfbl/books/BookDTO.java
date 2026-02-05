package net.etfbl.books;

public class BookDTO {
	private String id;
	private String title;
	
	public BookDTO() {
		super();
	}
	
	public BookDTO(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "BookDTO [id=" + id + ", title=" + title + "]";
	}	
	
	
}
