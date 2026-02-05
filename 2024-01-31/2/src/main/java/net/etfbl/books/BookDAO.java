package net.etfbl.books;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import net.etfbl.exceptions.NotFoundException;
import net.etfbl.models.AuthorEntity;
import net.etfbl.models.PublisherEntity;

public class BookDAO {
	private static final String FILE_PATH = System.getProperty("user.home") + "/books.bin";
	
	private Kryo kryo;
	
	public BookDAO() {
		this.kryo = new Kryo();
		kryo.register(BookEntity.class);
		kryo.register(AuthorEntity.class);
	    kryo.register(PublisherEntity.class);
        kryo.register(LinkedList.class);
	}
	
	public LinkedList<BookEntity> readAll() {	    
		LinkedList<BookEntity> books = new LinkedList<BookEntity>();
		try (Input input = new Input(new FileInputStream(FILE_PATH))) {
			books = kryo.readObject(input, LinkedList.class);
		} catch (FileNotFoundException e) {
			return books;
		}
		return books;
	}
	
	public void writeAll(LinkedList<BookEntity> books) {
		try(Output out = new Output(new FileOutputStream(FILE_PATH))) {
			kryo.writeObject(out, books);
			out.flush();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public BookEntity add(BookEntity book) {
		LinkedList<BookEntity> books = readAll();
		if(books == null)
			books = new LinkedList<BookEntity>();
		books.add(book);
		writeAll(books);
		return book;
	}

	public void delete(String id) throws NotFoundException {
		LinkedList<BookEntity> books = readAll();
		if(!books.removeIf(b -> id.equals(b.getId())))
			throw new net.etfbl.exceptions.NotFoundException();
		writeAll(books);
	}	
}
