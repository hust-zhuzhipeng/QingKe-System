package rpc.server;


import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.protocol.MsgHeader;
import rpc.protocol.NettyMessage;

public class RpcServerHandler extends SimpleChannelInboundHandler<NettyMessage> {
	//接口名-实例
    //private final Map<String, Object> handlerMap;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("channelActive");
		
	}
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelRegistered");
		super.channelRegistered(ctx);
	}
	
    public  RpcServerHandler() {
        //this.handlerMap = handlerMap;
    }
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
		System.out.println(msg);
	}

}
