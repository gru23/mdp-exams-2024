package net.etfbl.books;

import java.util.UUID;

public class BookEntity {
	private String id;
	private String title;
	private String author;
	
	public BookEntity() {
		super();
	}

	public BookEntity(String title, String author) {
		super();
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.author = author;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "BookEntity [id=" + id + ", title=" + title + ", author=" + author + "]";
	}
}
