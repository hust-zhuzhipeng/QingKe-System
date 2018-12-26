package rpc.registry;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 服务注册
 * @author zzp
 *	提供服务的注册
 *	/【ZK_REGISTRY_PATH】/【data】/【serviceName】/【address|temp】
 */
@Component
public class ServiceRegistry {
	private static final Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);
	private final ZooKeeperConfig zooKeeperConfig;
	private final CountDownLatch latch = new CountDownLatch(1);
	@Autowired
	public ServiceRegistry(ZooKeeperConfig zooKeeperConfig){
		this.zooKeeperConfig = zooKeeperConfig;
	}
	//注册服务
	public void register(String serviceName,String data) {
        if (data != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                AddRootNode(zk,serviceName); // Add root node if not exist
                createNode(zk, serviceName,data);
            }
        }
    }
	//注册服务
	public void register(List<String> serviceNames,String data) {
		if (data != null) {
            ZooKeeper zk = connectServer();
            for(String serviceName: serviceNames){
            	if (zk != null) {
                    AddRootNode(zk,serviceName); // Add root node if not exist
                    createNode(zk, serviceName,data);
                    logger.info("register "+serviceName+" on "+data);
                }
    		}
        }
    }
	//连接zooKeeper
	private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(zooKeeperConfig.getZK_registryAddress(), zooKeeperConfig.getZK_SESSION_TIMEOUT(), new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            logger.error("", e);
        }
        catch (InterruptedException ex){
            logger.error("", ex);
        }
        return zk;
    }
	//确保持久前缀节点存在
	synchronized private void AddRootNode(ZooKeeper zk,String serviceName){
        try {
            Stat s = zk.exists(zooKeeperConfig.getZK_REGISTRY_PATH(), false);
            if (s == null) {
                zk.create(zooKeeperConfig.getZK_REGISTRY_PATH(), new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            Stat ss = zk.exists(zooKeeperConfig.getZK_DATA_PATH(), false);
            if (ss == null) {
                zk.create(zooKeeperConfig.getZK_DATA_PATH(), new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } 
            String servicePath = zooKeeperConfig.getZK_DATA_PATH()+"/"+serviceName;
            Stat sss = zk.exists(servicePath, false);
            if(sss == null){
            	zk.create(servicePath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            logger.error(e.toString());
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
    }
    //在ZK_DATA_PATH下，创建serviceName永久节点下的address临时节点
    private void createNode(ZooKeeper zk, String serviceName,String data) {
        try {
            byte[] bytes = data.getBytes();
            String path = zooKeeperConfig.getZK_DATA_PATH()+"/"+serviceName+"/"+data;
            Stat stat = zk.exists(path,  false);
            if(stat==null){
            	zk.create(path, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            }
            logger.debug("create zookeeper node ({} => {})", path, data);
        } catch (KeeperException e) {
        	if(e instanceof ConnectionLossException){
        		//客户端并不知道服务器连接丢失的时候，有没有处理该请求。不保证请求执行成功。
        	}
            logger.error("", e);
        }//应用程序部分关闭 不保证请求执行成功
        catch (InterruptedException ex){
            logger.error("", ex);
        }
    }
}
