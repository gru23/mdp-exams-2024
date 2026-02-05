package net.etfbl.user;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import net.etfbl.enums.CostTypes;

public class UserService {
	private final UserDAO userDAO;
	
	public UserService() {
		this.userDAO = new UserDAO();
	}
	
	public List<UserEntity> getAll() {
		return userDAO.findAll();
	}
	
	public UserEntity getById(String id) throws NotFoundException {
		Optional<UserEntity> user = userDAO.findById(id);
		if(user.isEmpty())
			throw new NotFoundException("User not found.");
		return user.get();
	}
	
	public void addCosts(CostTypes costType, String userId) {
		UserEntity user = getById(userId);
		if(CostTypes.ORDER == costType) {
			user.subtractAmount(CostTypes.ORDER.getCost());
			user.incremenetOrders();
		}
		else if(CostTypes.BOOKS_REVIEW == costType) {
			user.subtractAmount(CostTypes.BOOKS_REVIEW.getCost());
			user.incremenetReviews();
		}
		user.subtractAmount(CostTypes.FIXED.getCost());
		user.incremenetMessages();
	}
	
	public Costs getCosts(String id) {
		return userDAO.findById(id).get().getCosts();
	}
}
