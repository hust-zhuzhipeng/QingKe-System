package rpc.test;

import rpc.client.RpcClient;
import rpc.client.RpcClientInitializer;
import rpc.protocol.MsgHeader;
import rpc.protocol.NettyMessage;
import rpc.protocol.RpcRequest;

public class ClientTest {
	public static void main(String[]args) throws InterruptedException{
		RpcClient c = new RpcClient("127.0.0.1",5917);
		c.start();
		NettyMessage msg = new NettyMessage();
		MsgHeader header = new MsgHeader();
		header.setCrcCode(8080);
		header.setPriority((byte)0);
		header.setSessionID(1001L);
		header.setType((byte)0);
		//msg.setBody("你好！ server！");
		msg.setHeader(header);
		RpcRequest r = new RpcRequest();
		r.setClassName("zzpClass1");
		r.setMethodName("zzpMethod1");
		msg.setBody(r);
		//RpcClientInitializer.h.sendMsg(msg);
		Thread.sleep(2000);
		System.out.println("send");
		c.rpcClientHandler.sendMsg(msg);
		Thread.sleep(2000);
		r.setClassName("zzpClass2");
		r.setMethodName("zzpMethod2");
		System.out.println("send");
		c.rpcClientHandler.sendMsg(msg);
	}
}
