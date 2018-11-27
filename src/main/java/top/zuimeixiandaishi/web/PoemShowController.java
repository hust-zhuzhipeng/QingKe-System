package top.zuimeixiandaishi.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import oss.core.exception.ResultEnum;
import oss.core.token.TokenNotFoundException;
import oss.core.token.domain.Token;
import oss.service.TokenService;
import top.zuimeixiandaishi.domain.PoemShow;
import top.zuimeixiandaishi.service.PoemShowService;

@Repository
@RequestMapping("/api/guest/poemshow")
public class PoemShowController {
	@Autowired
	PoemShowService poemShowService;
	@Autowired
	TokenService tokenService;
	
	/**
	 * @Response的代替方案，可以包含响应相关的元数据，如头部信息和状态码
	 * 本身也包含了Response的含义
	 * @throws TokenNotFoundException 
	 */
	@RequestMapping(value = "/gettoken")
	public ResponseEntity<Token> getToken(HttpServletRequest request,HttpServletResponse httpServletResponse){
		int offset = Integer.parseInt(request.getParameter("offset"));
		int limit = 1;
		boolean isDesc = true;
		Token token = tokenService.getPoemShowToken(isDesc, offset, limit);
		System.out.println("11");
		if(token!=null){throw new TokenNotFoundException(ResultEnum.TOKEN_UNFOUND);}
		HttpStatus status = token!=null? HttpStatus.OK:HttpStatus.NOT_FOUND;
		return new ResponseEntity<Token>(token,status);
	}
	
	/**
	 * 按顺序获取指定是poemshow
	 * isdesc:orderId是否从大到小
	 */
	@RequestMapping(value = "/test")
	public @ResponseBody Object test(HttpServletRequest request,HttpServletResponse httpServletResponse){
		int offset = Integer.parseInt(request.getParameter("offset"));
		int limit = 1;
		boolean isDesc = true;
		Token token = tokenService.getPoemShowToken(isDesc, offset, limit);
		
		return token;
	}
	@RequestMapping(value = "/test2")
	public @ResponseBody Object test2(HttpServletRequest request,HttpServletResponse httpServletResponse){
		int offset = Integer.parseInt(request.getParameter("offset"));
		int limit = 1;
		poemShowService.getPoemShow(true, offset, limit);
		return "hello";
	}
	/**
	 * 按顺序获取指定是poemshow
	 * isdesc:orderId是否从大到小
	 */
	@RequestMapping(value = "/getpoemshow", method = RequestMethod.POST)
	public @ResponseBody Object getAllPoemShow(HttpServletRequest request,HttpServletResponse httpServletResponse){
		boolean isDESC = Boolean.parseBoolean(request.getParameter("isdesc"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		List<PoemShow> poemShows= poemShowService.getPoemShow(isDESC, offset, limit);
		return poemShows;
	}	
}
