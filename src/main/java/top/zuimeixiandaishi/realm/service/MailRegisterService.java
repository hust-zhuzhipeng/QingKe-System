package top.zuimeixiandaishi.realm.service;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import top.zuimeixiandaishi.realm.domain.User;
import util.MailUtil;
/**
 * 邮箱注册服务
 * @author 99759
 *
 */
@Service("mailRegisterService")
public class MailRegisterService extends AbstractRegisterService{
	@Autowired
	private MailUtil mailUtil;
	
	@Override
	void valid(User user) throws RegisterException {
		if(user.getEmail()==null){
			throw new RegisterException(RegisterExceptionEnum.Mail_Register_Exception);
		}
	}

	@Override
	void sendVerificationCode(User user,String code) throws RegisterException {
		try{
			mailUtil.sendRegisterMail(user.getEmail(), code);
		}catch(Exception e){
			throw new RegisterException(RegisterExceptionEnum.Mail_Register_Exception);
		}
	}

	@Override
	boolean createUser(User user) {
		System.out.println("存入数据库");
		return true;
	}
	//测试
	public static void main(String[]args){
		ApplicationContext ap = 
				new ClassPathXmlApplicationContext(
						new String[]{"classpath:/spring/spring-ehcache.xml"
								,"classpath:/spring/spring-aliyun.xml"
								,"classpath:/spring/spring-dao.xml"
								,"classpath:/spring/spring-service.xml"
								,"classpath:/spring/spring-util.xml"});
		MailRegisterService s = (MailRegisterService)ap.getBean("mailRegisterService");
		User a = new User();
		a.setId(1);
		s.rigister(a);
		System.out.println("wait active");
		Scanner sca = new Scanner(System.in);
		String code = sca.nextLine();
		sca.close();
		s.active(code);
		
	}
}
