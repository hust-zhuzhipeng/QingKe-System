package realm.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	private static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired
	private UserService userService;
	@Override
	public boolean isAuthenticated() {
		Subject subject=SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			log.info("already Authenticated:"+subject.getPrincipal());
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean Authenticate(String username, String password) {
		//获取当前用户
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(username, password);
		try{
			//为当前用户进行认证
			subject.login(token);
			//认证通过 加入session
			subject.getSession().setAttribute("userId", userService.getByUserName(username));
			log.info("login success:"+token.getPrincipal());
			return true;
		} catch (UnknownAccountException uae) {
			log.info("login error:There is no user with username of "+token.getPrincipal());
			return false;
		} catch (IncorrectCredentialsException ice) {
        	log.info("login error:"+"Password for account " + token.getPrincipal() + " was incorrect!");
        	return false;
		}catch(Exception e){
			e.printStackTrace();
			log.info("login error: 登录失败"+token.getPrincipal());
			return false;
		}
	}

}
