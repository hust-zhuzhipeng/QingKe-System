package rpc.server;

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

import rpc.protocol.NettyMessage;
/**
 * msg处理
 * @author zzp
 * 继承aware接口 获取@RpcService注解的类
 */
@Component("rpcMessageHandler")
public class RpcMessageHandler implements ApplicationContextAware{
	private static final Logger logger = LoggerFactory.getLogger(RpcMessageHandler.class);
	@Autowired
    private ExecutorService executor;
	//接口名-实例
	private Map<String, Object> handlerMap = new HashMap<>();
	
	//处理业务
    public void submit(NettyMessage msg) {
        executor.submit(new Runnable(){
			@Override
			public void run() {
				handle(msg);
			}
        	
        });
    }
    
    public void handle(NettyMessage msg){
    	logger.info("RpcMessageHandler accept msg:"+msg);
    }
    //获取 @RpcService 修饰的服务接口，放入handlerMap中
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		logger.info("setApplicationContext");
    	Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                logger.info("Loading service: interface= "+interfaceName+" real class="+serviceBean.getClass());
                handlerMap.put(interfaceName, serviceBean);
            }
        }
	}
	//手动加入服务
	public void addServier(String interfaceName, Object serviceBean){
		if (!handlerMap.containsKey(interfaceName)) {
            logger.info("Loading service: {}", interfaceName);
            handlerMap.put(interfaceName, serviceBean);
        }
	}
}
