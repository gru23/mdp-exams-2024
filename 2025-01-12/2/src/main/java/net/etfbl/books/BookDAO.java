package net.etfbl.books;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookDAO {
private static Map<String, BookEntity> books;
	
	static {
		books = new HashMap<String, BookEntity>();
		BookEntity book = new BookEntity("The Count of Monte Cristo", "Alexander Dumas");
		books.put(book.getId(), book);
		book = new BookEntity("Crime and Punishment", "Fyodor Dostoevsky");
		books.put(book.getId(), book);
	}
	
	public List<BookEntity> findAll() {
		return List.copyOf(books.values());
	}
	
	public Optional<BookEntity> findById(String id) {
		return Optional.of(books.get(id));
	}
}
