package rpc.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

public class MyProxyFactory<T> implements FactoryBean<T>{
	private static final Logger logger = LoggerFactory.getLogger(MyProxyFactory.class);
	private Class<T> interfaceClass;

	@Override
	public T getObject() throws Exception {
		return (T) new MyProxy().bind(interfaceClass);
	}

	@Override
	public Class<?> getObjectType() {
		return interfaceClass;
	}

	@Override
	public boolean isSingleton() {
		 return true;
	}
	
	public Class<T> getInterfaceClass() {
        return interfaceClass;
    }
    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
