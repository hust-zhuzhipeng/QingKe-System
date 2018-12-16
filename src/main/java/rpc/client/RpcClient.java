package rpc.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import rpc.protocol.MsgHeader;
import rpc.protocol.NettyMessage;
import rpc.server.RpcServer;

public class RpcClient {
	private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
	private SocketAddress socketAddress;
	//Netty NIO线程池
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
    
	public RpcClient(String ip,int port) {
        this.socketAddress = new InetSocketAddress(ip, port);
    }
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
		msg.setBody("123");
		//RpcClientInitializer.h.sendMsg(msg);
		Thread.sleep(2000);
		System.out.println("send");
		RpcClientInitializer.h.sendMsg(msg);
		Thread.sleep(2000);
		msg.setBody("zzp");
		System.out.println("send");
		RpcClientInitializer.h.sendMsg(msg);
	}
	
	public void start() throws InterruptedException{
		Bootstrap b = new Bootstrap();
		
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());

       b.connect(socketAddress).sync();
        
	}
}
