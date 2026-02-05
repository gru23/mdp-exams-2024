package net.etfbl.books;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import net.etfbl.exceptions.BadRequestException;
import net.etfbl.exceptions.NotFoundException;

public class BookService {
	private final BookDAO bookDAO;
	
	public BookService() {
		this.bookDAO = new BookDAO();
	}
	
	public LinkedList<BookEntity> getAll() {
		return bookDAO.readAll();
	}
	
	public LinkedList<BookDTO> getAllWithIdAndTitle() {
		return getAll()
				.stream()
				.map(b -> new BookDTO(b.getId(), b.getTitle()))
				.collect(Collectors.toCollection(LinkedList::new));
	}
	
	public Optional<BookEntity> getById(String id) {
		return getAll()
				.stream()
				.filter(b -> id.equals(b.getId()))
				.findFirst();
	}
	
	public LinkedList<BookEntity> getAllByPublisherName(String publisherName) {
		return getAll()
				.stream()
				.filter(b -> publisherName.equals(b.getPublisher().getName()))
				.collect(Collectors.toCollection(LinkedList::new));
	}
	
	public BookEntity add(BookEntity book) throws BadRequestException {
		if(!isValildRequest(book))
			throw new BadRequestException();
		return bookDAO.add(book);
	}
	
	public void delete(String id) throws NotFoundException {
		bookDAO.delete(id);
	}
	
	private boolean isValildRequest(BookEntity request) {
		return request.getId() != null 
				&& request.getTitle() != null 
				&& request.getPublisher() != null 
				&& request.getAuthor() != null;
	}
}
