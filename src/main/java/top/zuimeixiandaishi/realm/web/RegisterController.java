package top.zuimeixiandaishi.realm.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.zuimeixiandaishi.realm.domain.Role;
import top.zuimeixiandaishi.realm.domain.State;
import top.zuimeixiandaishi.realm.domain.User;
import top.zuimeixiandaishi.realm.service.UserService;
/**
 * 用户注册服务
 * @author zzp
 *
 */
@Repository
@Controller
public class RegisterController {
	private static Logger log = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	UserService userService;
	/*
	 * 创建新账号
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String creatUser(@Valid User user,Errors errors){
		System.out.println(user.getUsername().length());
		if(errors.hasErrors()){
			return "parameters is unvalid!";
		}
		//检查username是否重复
		if(userService.getByUserName(user.getUsername())!=null){
			return "username was registered!";
		}
		user.setRoleId(Role.USER);
		user.setState(State.ACTIVE);
		return "create success!";
		/*if(userService.createUser(user)){
			return "create success!";
		}else{
			return "create false!";
		}*/
	}
}
