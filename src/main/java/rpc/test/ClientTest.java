package rpc.test;

import rpc.client.RpcClient;
import rpc.client.RpcClientInitializer;
import rpc.protocol.MessageType;
import rpc.protocol.MsgHeader;
import rpc.protocol.NettyMessage;
import rpc.protocol.NettyMessageFactory;
import rpc.protocol.RpcRequest;

public class ClientTest {
	public static void main(String[]args) throws InterruptedException{
		RpcClient c = new RpcClient("127.0.0.1",5917);
		c.start();
		NettyMessageFactory factory = new NettyMessageFactory((byte)1);
		NettyMessage msg = factory.newRpcRequest();
		
		
		//msg.setBody("你好！ server！");
		RpcRequest r = (RpcRequest)msg.getBody();
		r.setClassName("rpc.test.HelloService");
		r.setMethodName("hello");
		r.setParameterTypes(new Class<?>[0]);
		r.setParameters(new Object[0]);
		msg.setBody(r);
		//RpcClientInitializer.h.sendMsg(msg);
		Thread.sleep(2000);
		System.out.println("send");
		c.rpcClientHandler.sendMsg(msg);
		Thread.sleep(2000);
		r.setClassName("rpc.test.HelloService");
		r.setMethodName("echo");
		r.setParameterTypes(new Class<?>[]{String.class});
		r.setParameters(new Object[]{"client"});
		c.rpcClientHandler.sendMsg(msg);
	}
}
