package rpc.test;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerTest {
	static ApplicationContext a;
	public static void main(String[]args) throws InterruptedException{
		CountDownLatch c = new CountDownLatch(1);
		startAp(c);
		c.await();
				
	}
	public static void startAp(CountDownLatch c){
		new Thread(new Runnable(){

			@Override
			public void run() {
				ApplicationContext ap = 
						new ClassPathXmlApplicationContext(
								new String[]{"classpath:/spring/spring-rpc.xml"
										});
				a = ap;
				System.out.println("ApplicationContext start success!");
				c.countDown();
			}
			
		}).start();;
	}
}
