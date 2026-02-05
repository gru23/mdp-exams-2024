package net.etfbl.books;

import net.etfbl.models.AuthorEntity;
import net.etfbl.models.PublisherEntity;

public class BookEntity {
	private String id;
	private String title;
	private AuthorEntity author;
	private PublisherEntity publisher;
	
	public BookEntity() {
		super();
	}

	public BookEntity(String id, String title, AuthorEntity author, PublisherEntity publisher) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
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

	public AuthorEntity getAuthor() {
		return author;
	}

	public void setAuthor(AuthorEntity author) {
		this.author = author;
	}

	public PublisherEntity getPublisher() {
		return publisher;
	}

	public void setPublisher(PublisherEntity publisher) {
		this.publisher = publisher;
	}

	@Override
	public String toString() {
		return "BookEntity [title=" + title + ", author=" + author + ", publisher=" + publisher + "]";
	}
}
