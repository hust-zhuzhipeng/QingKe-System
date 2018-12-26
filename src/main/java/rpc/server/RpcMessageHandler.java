package rpc.server;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import rpc.protocol.MessageType;
import rpc.protocol.NettyMessage;
import rpc.protocol.NettyMessageFactory;
import rpc.protocol.RpcRequest;
import rpc.protocol.RpcResponse;
/**
 * msg处理
 * @author zzp
 * 继承aware接口 获取@RpcService注解的类
 */
@Component("rpcMessageHandler")
public class RpcMessageHandler{
	private static final Logger logger = LoggerFactory.getLogger(RpcMessageHandler.class);
	@Autowired
    private ExecutorService executor;
	@Autowired
	private NettyMessageFactory nettyMessageFactory;
	/*//接口名-实例
	private Map<String, Object> handlerMap = new HashMap<>();*/
	@Autowired
	private ServiceFind serviceFind;
	//处理业务
    public void submit(NettyMessage msg,Channel channel) {
        executor.submit(new Runnable(){
			@Override
			public void run() {
				handle(msg,channel);
			}
        	
        });
    }
    
    private void handle(NettyMessage msg,Channel channel){
    	MessageType type = msg.getType();	//msg的类型
    	//TODO
    	switch(type){
			case REQUEST: doRequest(msg,channel); break;
			case RESPONSE: doResponse(msg,channel);break;
			case CONNECT_REQUEST: ;break;
			case CONNECT_RESPONSE: ;break;
			case DI_REQUEST: ;break;
			case DI_RESPONSE: ;break;
			default: ;break;
    	}
    	
    }
	//手动加入服务
	public void addServier(String interfaceName, Object serviceBean){
		if (!serviceFind.containService(interfaceName)) {
            logger.info("Loading service: {}", interfaceName);
            serviceFind.addService(interfaceName, serviceBean);
        }
	}
	private void doRequest(NettyMessage msg,Channel channel){
		RpcRequest request = (RpcRequest)msg.getBody();
		NettyMessage responseMsg = nettyMessageFactory.newRpcResponse(msg.getSessionID(),request.getRequestId());
	    RpcResponse response = (RpcResponse)responseMsg.getBody();
		logger.info("doRequest");
		//服务名
        String interfaceName = request.getClassName();
        //服务实例
        Object serviceBean = serviceFind.getServiceBean(interfaceName);
        logger.info(serviceBean.toString());
        //服务类型
        Class<?> serviceClass = serviceBean.getClass();
        //方法名 参数类型和参数
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        // JDK reflect
        /*Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);*/

        // Cglib reflect
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        try {
			Object resultObject = serviceFastMethod.invoke(serviceBean, parameters);
			response.setResult(resultObject);
        } catch (InvocationTargetException e) {
			logger.error("invoke fail!",e);
			response.setError(e.toString());	
		}
        logger.info("send msg= "+responseMsg);
        channel.writeAndFlush(responseMsg);
	}
	private NettyMessage doResponse(NettyMessage msg,Channel channel){
		logger.info("doResponse");
		//TODO
		return null;
	}
	
	
}
