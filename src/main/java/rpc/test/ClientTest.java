package rpc.test;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rpc.client.RpcClient;
import rpc.client.RpcClientInitializer;
import rpc.protocol.MessageType;
import rpc.protocol.MsgHeader;
import rpc.protocol.NettyMessage;
import rpc.protocol.NettyMessageFactory;
import rpc.protocol.RpcRequest;

public class ClientTest {
	static ApplicationContext a;
	public static void main(String[]args) throws InterruptedException{
		CountDownLatch c = new CountDownLatch(1);
		startAp(c);
		c.await();
		HelloService proxy = (HelloService) a.getBean("helloService");
		System.out.println(proxy.hello());
	}
	public static void startAp(CountDownLatch c){
		new Thread(new Runnable(){

			@Override
			public void run() {
				ApplicationContext ap = 
						new ClassPathXmlApplicationContext(
								new String[]{"classpath:/spring/spring-rpc-client.xml"
										});
				a = ap;
				System.out.println("ApplicationContext start success!");
				c.countDown();
			}
			
		}).start();;
	}
}
