package net.etfbl.books;

import java.util.List;

import javax.ws.rs.NotFoundException;

import net.etfbl.enums.CostTypes;
import net.etfbl.user.UserDAO;
import net.etfbl.user.UserService;

public class BookService {
	private final UserService userService;
	private final BookDAO bookDAO;

	public BookService() {
		this.userService = new UserService();
		this.bookDAO = new BookDAO();
	}

	public List<BookEntity> getAll(String userId) throws NotFoundException {
		userService.addCosts(CostTypes.BOOKS_REVIEW, userId);
		new UserDAO().findAll().stream().forEach(System.out::println);
		return bookDAO.findAll();
	}
}
