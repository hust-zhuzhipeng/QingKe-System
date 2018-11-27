package top.zuimeixiandaishi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import oss.core.token.domain.Token;
import top.zuimeixiandaishi.domain.PoemShow;

public interface PoemShowService {
	//获取PoemShow
	List<PoemShow> getPoemShow(boolean isDesc, int offset, int limit);
	//添加PoemShow
	int addPoemShow(PoemShow poemShow);
	//更新PoemShow
	int updatePoemShow(PoemShow poemShow);
	//返回PoemShow数量
	int count();
	//返回对应PoemShow的Token
	Token getToken(boolean isDESC, int offset, int limit);
}
