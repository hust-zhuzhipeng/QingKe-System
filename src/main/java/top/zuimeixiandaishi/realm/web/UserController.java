package top.zuimeixiandaishi.realm.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.zuimeixiandaishi.realm.domain.User;

@Repository
@Controller
public class UserController {
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String login(HttpServletRequest request){
		//获取当前用户
		Subject subject=SecurityUtils.getSubject();
		//已经被认证
		if(subject.isAuthenticated()){
			return "already login!";
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User();
		user.setUsername(username); user.setPassword(password);
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(), user.getPassword());
		try{
			//为当前用户进行认证
			subject.login(token);
			request.setAttribute("user", user);
			return "login success!";
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "用户名或密码错误");
			return "login error!";
		}
	}
	@RequestMapping(value = "/guest", method = RequestMethod.GET)
	public @ResponseBody String guest() {
		System.out.println("进入/guest");
		return "welcom guest!";
	}
}
