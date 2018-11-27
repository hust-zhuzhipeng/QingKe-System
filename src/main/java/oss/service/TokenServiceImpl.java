package oss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oss.core.token.OSSTokenManager;
import oss.core.token.domain.PolicyEnum;
import oss.core.token.domain.Token;
import oss.persistent.NameService;
@Service("tokenService")
public class TokenServiceImpl implements  TokenService{
	@Autowired
	OSSTokenManager oSSTokenManager;
	@Autowired
	NameService nameService;
	@Override
	public Token getPoemShowToken(boolean isDesc, int offset, int limit) {
		//policy模式
		PolicyEnum p = PolicyEnum.READ_POEMSHOW;
		return oSSTokenManager.getReadToken(p, isDesc, offset, limit);
	}

	@Override
	public Token getPoemToken(String title, String authorName, int authorIndex) {
		String fileName = nameService.getOSSPoemName(title,authorName,authorIndex);
		PolicyEnum p = PolicyEnum.READ_POEM;
		return oSSTokenManager.getReadToken(p, fileName);
	}

	@Override
	public Token getAuthorToken(String authorName, int authorIndex) {
		String fileName = nameService.getOSSAuthorName(authorName, authorIndex);
		PolicyEnum p = PolicyEnum.READ_AUTHOR;
		return oSSTokenManager.getReadToken(p, fileName);
	}

}
