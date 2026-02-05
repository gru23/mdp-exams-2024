package blogs;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Blog {
	private String id;
	private String author;
	private String title;
	private String time;
	private String text;
	private List<Comment> comments;
	
	public Blog() {
		super();
	}
	
	public Blog(String author, String title, String text) {
		super();
		this.id = UUID.randomUUID().toString().substring(0, 5);
		this.author = author;
		this.title = title;
		this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
		this.text = text;
		this.comments = new LinkedList<Comment>();
	}
	
	public Blog(String id, String author, String title, String text) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.time = LocalTime.now().toString();
		this.text = text;
		this.comments = new LinkedList<Comment>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", author=" + author + ", title=" + title + ", time=" + time + ", text=" + text
				+ ", comments=" + comments + "]";
	}

}
