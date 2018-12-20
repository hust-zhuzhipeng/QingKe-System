package rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rpc.client.RpcClient;
import rpc.protocol.NettyMessage;
import rpc.protocol.NettyMessageFactory;
import rpc.protocol.RpcRequest;
/**
 * 代理类
 * @author zzp
 */
public class MyProxy implements InvocationHandler{
	private static NettyMessageFactory nettyMessageFactory= new NettyMessageFactory((byte)1);
	private static final Logger logger = LoggerFactory.getLogger(MyProxy.class);
    
	private Class<?> interfaceClass;

    public Object bind(Class<?> cls) {
    	logger.info("bind class="+cls);
        this.interfaceClass = cls;
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[] {interfaceClass}, this);
    }

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
		NettyMessage msg = nettyMessageFactory.newRpcRequest();
		RpcRequest request = (RpcRequest) msg.getBody();
		request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        logger.info("生成代理msg "+msg);
		RpcClient.rpcClientHandler.sendMsg(msg);
		return "hello invoke test!";
	}
	
}
