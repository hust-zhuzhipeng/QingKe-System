package rpc.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import rpc.protocol.RpcDecoder;
import rpc.protocol.RpcEncoder;

public class RpcClientInitializer extends ChannelInitializer<SocketChannel>{
	//所有远程连接的业务处理handler
    public static RpcClientHandler h = new RpcClientHandler();
	@Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new RpcEncoder());
        cp.addLast(new LengthFieldBasedFrameDecoder(65536, 4, 4, 0, 0));
        cp.addLast(new RpcDecoder());
        cp.addLast(h);
    }
}
