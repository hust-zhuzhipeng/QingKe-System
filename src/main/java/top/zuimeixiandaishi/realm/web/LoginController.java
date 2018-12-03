package top.zuimeixiandaishi.realm.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.xml.bind.v2.TODO;

import oss.core.operate.OSSOperationObjectImpl;
import top.zuimeixiandaishi.realm.domain.Role;
import top.zuimeixiandaishi.realm.domain.User;
import top.zuimeixiandaishi.realm.service.LoginService;
import top.zuimeixiandaishi.realm.service.RegisterService;
import top.zuimeixiandaishi.realm.service.UserService;
import util.MailUtil;

@Repository
@Controller
public class LoginController {
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	RegisterService mailRegisterService;
	/*
	 * 登录操作
	 * 登录成功时，会将用户user对象传入session
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String login(HttpServletRequest request){
		//已经被认证
		if(loginService.isAuthenticated()){
			return "already login!";
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(loginService.Authenticate(username, password)){
			return "login success!";
		}else{
			return "login false";
		}
	}
	/*
	 * 创建新账号
	 */
	@RequestMapping(value = "/login/creatUser", method = RequestMethod.POST)
	public @ResponseBody String creatUser(HttpServletRequest request){
		//已经被认证
		if(loginService.isAuthenticated()){
			return "already login!";
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRoleId(Role.USER);
		if(userService.createUser(user)){
			return "create success!";
		}else{
			return "create false!";
		}
	}
	/*
	 * 创建新账号
	 */
	@RequestMapping(value = "/login/updateUser", method = RequestMethod.POST)
	public @ResponseBody String updateUser(HttpServletRequest request){
		//已经被认证
		if(loginService.isAuthenticated()){
			return "already login!";
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRoleId(Role.ADMIN);
		if(userService.updateUser(user)){
			return "update success!";
		}else{
			return "update false!";
		}
	}
	@RequestMapping(value = "/guest", method = RequestMethod.GET)
	public @ResponseBody String guest() {
		System.out.println("进入/guest");
		return "welcom guest!";
	}
	
	@RequestMapping(value = "/mailregiste", method = RequestMethod.POST)
	public @ResponseBody String receivemail(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		mailRegisterService.rigister(user);
		return "already send the register mail";
	}
	@RequestMapping(value = "/activemail", method = RequestMethod.GET)
	public @ResponseBody String activemail(HttpServletRequest request) {
		String code = request.getParameter("code");
		mailRegisterService.active(code);
		return "register success!";
	}
}
