package net.etfbl.user;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import net.etfbl.enums.CostTypes;

public class UserEntity {
	private String id;
	private String name;
	private BigDecimal amount;
	
	private int orders;
	private int reviews;
	private int sentMessages;
	
	public UserEntity(String name) {
		super();
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.amount = new BigDecimal(String.valueOf(new Random().nextInt(10) + 1));
		this.orders = 0;
		this.reviews = 0;
		this.sentMessages = 0;
	}
	
	public UserEntity(String name, String id) {
		super();
		this.id = id;
		this.name = name;
		this.amount = new BigDecimal(String.valueOf(new Random().nextInt(10) + 1));
		this.orders = 0;
		this.reviews = 0;
		this.sentMessages = 0;
	}
	
	public Costs getCosts() {
		return new Costs(
				CostTypes.ORDER.getCost().multiply(BigDecimal.valueOf(orders)), 
				CostTypes.BOOKS_REVIEW.getCost().multiply(BigDecimal.valueOf(reviews)), 
				CostTypes.FIXED.getCost().multiply(BigDecimal.valueOf(sentMessages))
		);
	}
	
	public void subtractAmount(BigDecimal value) {
		this.amount = amount.subtract(value);
	}
	
	public void incremenetReviews() {
		this.reviews++;
	}
	
	public void incremenetOrders() {
		this.orders++;
	}
	
	public void incremenetMessages() {
		this.sentMessages++;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public int getReviews() {
		return reviews;
	}

	public void setReviews(int reviews) {
		this.reviews = reviews;
	}

	public int getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(int sentMessages) {
		this.sentMessages = sentMessages;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", name=" + name + ", amount=" + amount + ", orders=" + orders + ", reviews="
				+ reviews + ", sentMessages=" + sentMessages + "]";
	}
}
