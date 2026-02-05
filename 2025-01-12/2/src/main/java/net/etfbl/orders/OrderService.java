package net.etfbl.orders;

import java.util.List;

import net.etfbl.enums.CostTypes;
import net.etfbl.user.UserService;

public class OrderService {
	private final UserService userService;
	private final OrderDAO orderDAO;
	
	public OrderService() {
		this.userService = new UserService();
		this.orderDAO = new OrderDAO();
	}
	
	public void order(OrderRequest request) {
		userService.addCosts(CostTypes.ORDER, request.getUserId());
		OrderEntity order = new OrderEntity(request.getUserId(), request.getBookId());
		orderDAO.addOrder(order);
	}
	
	public List<OrderEntity> getAllOrderedBooksByUserId(String userId) {
		return orderDAO.findAllOrderedBooksByUserId(userId);
	}
}
