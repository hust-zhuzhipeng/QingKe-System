package rpc.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerTest {
	public static void main(String[]args){
		ApplicationContext ap = 
				new ClassPathXmlApplicationContext(
						new String[]{"classpath:/spring/spring-rpc.xml"
								});
	}
}
