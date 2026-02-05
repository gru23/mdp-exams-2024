package services;

import java.util.List;

import blogs.Blog;
import blogs.Comment;

public class BlogService {
	private final BlogDAO blogDAO;
	
	public BlogService() {
		this.blogDAO = new BlogDAO();
	}

	public List<Blog> getAll() {
		return blogDAO.readAll();
	}
	
	public Blog getById(List<Blog> blogs, String id) {
		return blogs.stream()
				.filter(b -> id.equals(b.getId()))
				.findFirst()
				.get();
	}
	
	public List<Blog> add(Blog newBlog) {
		List<Blog> blogs = blogDAO.readAll();
		blogs.add(newBlog);
		blogDAO.writeAll(blogs);
		return blogs;
	}
	//nisam trebao koristiti set metodu jer izmjena reference oldBlog mijenja taj Blog iz liste na koji pokazuje...
	//pa sam mogao samo nju izmjeniti a ne praviti novi i setovati ga na mjesto starog
	public List<Blog> update(Blog newBlog) {
		List<Blog> blogs = blogDAO.readAll();
		Blog oldBlog = getById(blogs, newBlog.getId());
		newBlog.setTime(oldBlog.getTime());
		newBlog.setComments(oldBlog.getComments());
		newBlog.setId(oldBlog.getId());
		int index = blogs.indexOf(oldBlog);
		blogs.set(index, newBlog);
		blogDAO.writeAll(blogs);
		return blogs;
	}
	
	public List<Blog> comment(String blogId, Comment comment) {
		List<Blog> blogs = blogDAO.readAll();
		Blog blog = getById(blogs, blogId);
		blog.getComments().add(comment);
		blogDAO.writeAll(blogs);
		return blogs;
	}
	
	public void deleteById(String id) {
		List<Blog> blogs = blogDAO.readAll();
		blogs.removeIf(b -> id.equals(b.getId()));
		blogDAO.writeAll(blogs);
	}
}
