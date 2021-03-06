package rpc.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import rpc.protocol.MsgHeader;
import rpc.protocol.NettyMessage;
import rpc.server.RpcServer;
@Component
public class RpcClient {
	private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
	
	private SocketAddress socketAddress;
	//Netty NIO线程池
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
    public static RpcClientHandler rpcClientHandler;
    @Autowired
	public RpcClient(@Value("${RpcClient.ip}")String ip,@Value("${RpcClient.port}")int port) {
        this.socketAddress = new InetSocketAddress(ip, port);
    }
	
    @PostConstruct 
	public void start() throws InterruptedException{
		Bootstrap b = new Bootstrap();
		
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());

       ChannelFuture channelFuture = b.connect(socketAddress).sync();  
       rpcClientHandler = channelFuture.channel().pipeline().get(RpcClientHandler.class);
	}
}
