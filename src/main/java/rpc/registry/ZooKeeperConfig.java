package rpc.registry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * zooKeeper配置中心
 * @author zzp
 *
 */
@Component
public class ZooKeeperConfig {
	@Value("${ZK_SESSION_TIMEOUT}")
	private int ZK_SESSION_TIMEOUT;
	@Value("${ZK_REGISTRY_PATH}")
	private String ZK_REGISTRY_PATH;
	@Value("${ZK_DATA_PATH}")
	private String ZK_DATA_PATH;
	//zooKeeper的注册通信地址
	@Value("${ZK_registryAddress}")
	private String ZK_registryAddress;
	public int getZK_SESSION_TIMEOUT() {
		return ZK_SESSION_TIMEOUT;
	}
	public void setZK_SESSION_TIMEOUT(int zK_SESSION_TIMEOUT) {
		ZK_SESSION_TIMEOUT = zK_SESSION_TIMEOUT;
	}
	public String getZK_REGISTRY_PATH() {
		return ZK_REGISTRY_PATH;
	}
	public void setZK_REGISTRY_PATH(String zK_REGISTRY_PATH) {
		ZK_REGISTRY_PATH = zK_REGISTRY_PATH;
	}
	public String getZK_DATA_PATH() {
		return ZK_DATA_PATH;
	}
	public void setZK_DATA_PATH(String zK_DATA_PATH) {
		ZK_DATA_PATH = zK_DATA_PATH;
	}
	public String getZK_registryAddress() {
		return ZK_registryAddress;
	}
	public void setZK_registryAddress(String zK_registryAddress) {
		ZK_registryAddress = zK_registryAddress;
	}
	
	
}
