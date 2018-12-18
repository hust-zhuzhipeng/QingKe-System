package rpc.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import rpc.client.RpcClientInitializer;
import rpc.protocol.NettyMessage;
import rpc.protocol.RpcDecoder;
import rpc.protocol.RpcEncoder;
/**
 * rpc服务器
 * @author zzp
 *
 */
@Component("rpcServer")
public class RpcServer {
	private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
	private final String serverAddress;
	private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup  = null;
    @Autowired
    private RpcServerHandler rpcServerHandler;
   
    @Autowired
    public RpcServer(@Value("${RpcServer.Address}")String serverAddress){
    	this.serverAddress = serverAddress;
    }
    @PostConstruct //指定该方法在对象被创建后马上调用 相当于配置文件中的init-method属性
    public void startServer() throws Exception{
    	start();
    	logger.info("RpcServer start success");
    }
	//private ServiceRegistry serviceRegistry;
	public void start() throws Exception {
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
                                    .addLast(new RpcDecoder())
                                    .addLast(new RpcEncoder())
                                    .addLast(rpcServerHandler);
                        }
                    })
                    //tcp队列长度
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, false);

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
	//关闭服务器
    public void stop() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        logger.info("RpcServer stop success");        
    }
    public void send(NettyMessage msg){
    	
    }
}
