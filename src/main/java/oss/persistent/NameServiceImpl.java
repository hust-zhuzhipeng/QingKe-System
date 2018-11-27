package oss.persistent;

import org.springframework.stereotype.Service;

@Service("nameService")
public class NameServiceImpl implements NameService{
	@Override
	public String getOSSAuthorName(String authorName, int authorIndex) {
		return authorName+authorIndex;
	}

	@Override
	public String getOSSPoemName(String title, String authorName, int authorIndex) {
		return title+authorName+authorIndex;
	}

}
