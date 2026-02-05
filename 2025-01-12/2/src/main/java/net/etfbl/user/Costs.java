package net.etfbl.user;

import java.math.BigDecimal;

public class Costs {
	private BigDecimal ordersCosts;
	private BigDecimal reviewsCosts;
	private BigDecimal sentMessagesCosts;
	private BigDecimal total;
	
	public Costs() {
		super();
	}
	
	public Costs(BigDecimal ordersCosts, BigDecimal reviewsCosts, BigDecimal sentMessagesCosts) {
		super();
		this.ordersCosts = ordersCosts;
		this.reviewsCosts = reviewsCosts;
		this.sentMessagesCosts = sentMessagesCosts;
		this.total = ordersCosts.add(reviewsCosts).add(sentMessagesCosts);
	}
	
	public BigDecimal getOrdersCosts() {
		return ordersCosts;
	}
	public void setOrdersCosts(BigDecimal ordersCosts) {
		this.ordersCosts = ordersCosts;
	}
	public BigDecimal getReviewsCosts() {
		return reviewsCosts;
	}
	public void setReviewsCosts(BigDecimal reviewsCosts) {
		this.reviewsCosts = reviewsCosts;
	}
	public BigDecimal getSentMessagesCosts() {
		return sentMessagesCosts;
	}
	public void setSentMessagesCosts(BigDecimal sentMessagesCosts) {
		this.sentMessagesCosts = sentMessagesCosts;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Costs [ordersCosts=" + ordersCosts + ", reviewsCosts=" + reviewsCosts + ", sentMessagesCosts="
				+ sentMessagesCosts + ", total=" + total + "]";
	}
}
