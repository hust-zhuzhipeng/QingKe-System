package top.zuimeixiandaishi.service;

import java.util.List;

import top.zuimeixiandaishi.domain.Poem;

public interface PoemService {
	Poem getById(long id);
	
	List<Poem> getByTitle(String title);
	
	List<Poem> getByAuthorId(Long authorId);
	
	int countPoemSize();
	
	boolean insertPoem(Poem poem);
	
	boolean updatePoem(Poem poem);
}
