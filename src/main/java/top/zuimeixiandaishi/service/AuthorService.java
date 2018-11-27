package top.zuimeixiandaishi.service;

import java.util.List;

import top.zuimeixiandaishi.domain.Author;

public interface AuthorService {
	
	List<Author> getByName(String name);
	
	int countAuthorSize();
	
	boolean insertAuthor(Author author);
	
	boolean updateAuthor(Author author);
}
