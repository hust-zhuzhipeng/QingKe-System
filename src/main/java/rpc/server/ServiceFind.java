package rpc.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 服务发现
 * @author zzp
 * 发现被@RpcService修饰的服务
 */
@Component
public class ServiceFind implements ApplicationContextAware{
	private static final Logger logger = LoggerFactory.getLogger(ServiceFind.class);
	//接口名-实例
	private final Map<String, Object> handlerMap = new HashMap<>();

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		logger.info("setApplicationContext");
    	Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                logger.info("Loading service: interface= "+interfaceName+" real-class="+serviceBean.getClass());
                handlerMap.put(interfaceName, serviceBean);
            }
        }
	}
	public boolean containService(String service){
		return handlerMap.containsKey(service);
	}
	public void addService(String service,Object bean){
		handlerMap.put(service, bean);
	}
	public Object getServiceBean(String service){
		return handlerMap.get(service);
	}
	public List<String> getServiceName(){
		List<String> serviceNames = new ArrayList<>(handlerMap.size());
		for(String w : handlerMap.keySet()){
			serviceNames.add(w);
		}
		return serviceNames;
	}
}
