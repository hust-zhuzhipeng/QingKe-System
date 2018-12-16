package rpc.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import rpc.protocol.RpcDecoder;
import rpc.protocol.RpcEncoder;

public class RpcServer {
	private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
	private String serverAddress;
	private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup  = null;
    public static void main(String[]args) throws Exception{
    	RpcServer server = new RpcServer("127.0.0.1:5917");
    	server.start();
    }
    public RpcServer(String serverAddress){
    	this.serverAddress = serverAddress;
    }
    
	//private ServiceRegistry serviceRegistry;
	public void start() throws Exception {
		Map<String,Class> attachMap = new HashMap<>();
		attachMap.put("attach", Object.class);
		Map<Byte,Class> bodyMap = new HashMap<>();
		bodyMap.put(new Byte((byte)0), String.class);
        if (bossGroup == null && workerGroup == null) {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup(1);
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                            //LengthFieldBasedFrameDecoder博客 https://blog.csdn.net/thinking_fioa/article/details/80573483
                                    .addLast(new LengthFieldBasedFrameDecoder(65536, 4, 4, 0, 0))
                                    .addLast(new RpcDecoder(attachMap,bodyMap))
                                    .addLast(new RpcEncoder())
                                    .addLast(new RpcServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String[] array = serverAddress.split(":");
            String host = array[0];
            int port = Integer.parseInt(array[1]);
            logger.info("host="+host+"\tport="+port);
            ChannelFuture future = bootstrap.bind(host, port).sync();
            logger.info("Server started on port {}", port);

            /*if (serviceRegistry != null) {
                serviceRegistry.register(serverAddress);
            }*/

            future.channel().closeFuture().sync();
        }
    }
}
