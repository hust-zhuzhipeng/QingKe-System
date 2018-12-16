package rpc.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
		Map<String,Class> attachMap = new HashMap<>();
		attachMap.put("attach", Object.class);
		Map<Byte,Class> bodyMap = new HashMap<>();
		bodyMap.put((byte)0, String.class);
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new RpcEncoder());
        cp.addLast(new LengthFieldBasedFrameDecoder(65536, 4, 4, 0, 0));
        cp.addLast(new RpcDecoder(attachMap,bodyMap));
        cp.addLast(h);
    }
}
