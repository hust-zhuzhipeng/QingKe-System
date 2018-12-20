package rpc.proxy;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import rpc.server.RpcMessageHandler;
import rpc.server.RpcService;

/**
 * 找到所有的 @RpcRemoteInvoke 接口
 * @author zzp
 *
 */
@Component
public class RpcServiceFactory implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor{
	private static final Logger logger = LoggerFactory.getLogger(RpcServiceFactory.class);
	
	//接口名-接口类
	private Map<String, Class<?>> annotationMap = new HashMap<>();
	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		logger.info("setApplicationContext");
		this.applicationContext = ctx;
		// 取得对应Annotation映射，BeanName -- 实例
		Map<String, Object> configMap = applicationContext.getBeansWithAnnotation(RpcRemoteInvokeConfig.class);
		// 加载每个需要远程调用的接口
        for(String w : configMap.keySet()){
        	Object configBean = configMap.get(w);
        	Class<?> configBeanClazz = configBean.getClass();
        	logger.info("getConfig: name="+w+" type="+configBeanClazz.getName());
        	updateInterface(configBeanClazz);
        }
	}
	
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		for(Map.Entry<String, Class<?>> w: annotationMap.entrySet()){
			String beanName = w.getKey();
			Class<?> cls = w.getValue();
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
	        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
	        definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
	        definition.setBeanClass(MyProxyFactory.class);
	        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
	        // 注册bean名,一般为类名首字母小写
	        logger.info("注册客户端代理  beanName="+beanName+" "+definition);
	        registry.registerBeanDefinition(beanName, definition);
		}
	}
	//将configBean中的需要代理的接口提取出来
	private void updateInterface(Class<?> configBeanClazz){
		Field[] fields = configBeanClazz.getDeclaredFields();
		for(Field field : fields){
			RpcRemoteInvoke r = field.getAnnotation(RpcRemoteInvoke.class);
			if(field.getType().isInterface() && r != null){
				//用于注入spring的唯一id
				String beanName = field.getName();
				if(!r.beanName().isEmpty()){
					beanName = r.beanName();
				}
				Class<?> clazz = field.getType();
				logger.info("加载客户端  beanName="+beanName+" clazz="+clazz.toString());
				annotationMap.put(beanName, clazz);
			}
		}
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
	}	

}
