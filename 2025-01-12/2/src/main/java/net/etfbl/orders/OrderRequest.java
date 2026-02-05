package net.etfbl.orders;

import javax.validation.constraints.NotNull;

public class OrderRequest {
	@NotNull
	private String userId;
	@NotNull
	private String bookId;
	
	public OrderRequest() {
		super();
	}
	
	public OrderRequest(String userId, String bookId) {
		super();
		this.userId = userId;
		this.bookId = bookId;
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
}
