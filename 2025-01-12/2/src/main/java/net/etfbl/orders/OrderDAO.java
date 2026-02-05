package net.etfbl.orders;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDAO {
	private static List<OrderEntity> orders;
	
	static {
		orders = new LinkedList<OrderEntity>();
	}
	
	public OrderDAO() {
		
	}
	
	public List<OrderEntity> findAll() {
		return orders;
	}
	
	public void addOrder(OrderEntity newOrder) {
		orders.add(newOrder);
	}
	
	public List<OrderEntity> findAllOrderedBooksByUserId(String userId) {
		return orders
				.stream()
				.filter(o -> userId.equals(o.getUserId()))
				.collect(Collectors.toList());
	}
}
