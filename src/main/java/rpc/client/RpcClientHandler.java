package rpc.client;

import java.net.SocketAddress;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.protocol.MsgHeader;
import rpc.protocol.NettyMessage;

public class RpcClientHandler extends SimpleChannelInboundHandler<NettyMessage>{
	private volatile Channel channel;
    private SocketAddress remotePeer;
    
    
    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemotePeer() {
        return remotePeer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("channelActive");
        super.channelActive(ctx);
        this.remotePeer = this.channel.remoteAddress();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        System.out.println("channelRegistered"+channel.toString());
    }
	public void sendMsg(NettyMessage msg) {
		System.out.println("发送msg："+msg);
        channel.writeAndFlush(msg);
        channel.flush();
    }
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
		System.out.println("client receive msg:"+msg);
	}

}
