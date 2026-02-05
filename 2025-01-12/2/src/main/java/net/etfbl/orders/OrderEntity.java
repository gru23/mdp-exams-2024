package net.etfbl.orders;

import java.time.LocalDate;

public class OrderEntity {
	private String userId;
	private String bookId;
	private String date;
	
	public OrderEntity() {
		super();
	}
	
	public OrderEntity(String userId, String bookId) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.date = LocalDate.now().toString();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "OrderEntity [userId=" + userId + ", bookId=" + bookId + ", date=" + date + "]";
	}
}
