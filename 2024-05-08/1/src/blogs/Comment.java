package blogs;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Comment {
	private String comment;
	private String user;
	private String time;
	
	public Comment(String user, String comment) {
		this.user = user;
		this.comment = comment;
		this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Comment [comment=" + comment + ", user=" + user + ", time=" + time + "]";
	}
}
