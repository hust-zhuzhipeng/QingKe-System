package rpc.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * rpc远程调用注解
 * @author zzp
 * 在RpcRemoteInvokeConfig修饰的注解类中，被该注解修饰的接口字段将被代理
 */
@Target({ElementType.FIELD})		//适用范围为字段
@Retention(RetentionPolicy.RUNTIME)	//生命周期 运行时仍存在
public @interface RpcRemoteInvoke{
	String beanName() default "";
}
