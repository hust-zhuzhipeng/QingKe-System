package rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.protocol.NettyMessage;
@Component("rpcServerHandler")
@Scope("prototype")//原型模式
public class RpcServerHandler extends SimpleChannelInboundHandler<NettyMessage> {
	private static final Logger logger = LoggerFactory.getLogger(SimpleChannelInboundHandler.class);
	@Autowired
	RpcMessageHandler rpcMessageHandler;
	//接口名-实例
    public  RpcServerHandler() {
        //this.handlerMap = handlerMap;
    }
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
		logger.info("RpcServerHandler accept msg:"+msg);
		//交由业务处理类单独处理并反馈
		rpcMessageHandler.submit(msg);
	}
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("server caught exception", cause);
        ctx.close();
    }
}
