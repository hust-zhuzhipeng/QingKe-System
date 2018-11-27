package oss.service;

import oss.core.token.domain.Token;

/*
 * 权限服务
 */
public interface TokenService {
	//提供PoemShow的Token
	Token getPoemShowToken(boolean isDesc,int offset,int limit);
	//提供诗歌Token
	Token getPoemToken(String title,String authorName,int authorIndex);
	//提供诗人Token
	Token getAuthorToken(String authorName,int authorIndex);
}
