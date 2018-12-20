package rpc.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;
/**
 * 远程调用配置注解
 * @author zzp
 * spring会发现被该注解修饰的配置类
 */
@Target({ElementType.TYPE})	//表明该注解的适用范围
@Retention(RetentionPolicy.RUNTIME)	//注解的生命周期 在运行时仍存在
@Component
public @interface RpcRemoteInvokeConfig {
	
}
