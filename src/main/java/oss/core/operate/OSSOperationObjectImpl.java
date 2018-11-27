package oss.core.operate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectAcl;

import util.JsonOperate;
/**
 * oss对象操作封装实例
 * @author zzp
 * 同一类的OSS对象，应该通过同一个OSSOperationObject的实现类来完成！
 * 在单个文件较小，OSSOperationObjectImpl能完成的情况下，
 * 推荐使用该类！
 * 待完善：ossclient的异常处理还没有完成，难点。
 * *该类继承了 ApplicationContextAware 接口，用于当client宕机时替换
 */
@Service("oSSOperationObject")
public class OSSOperationObjectImpl implements OSSOperationObject,ApplicationContextAware{
	private ApplicationContext applicationContext;
	@Value("${oss_client}")
	private String ossClientName;
	@Autowired
	@Qualifier("${oss_client}")
	private OSSClient ossClient;
	private static Logger log = LoggerFactory.getLogger(OSSOperationObjectImpl.class);
	@Override
	public void putString(String bucketName, String OssId, String content) {
		log.info("向{}中插入文件{}",bucketName,OssId);
		try{
			ossClient.putObject(bucketName, OssId, new ByteArrayInputStream(content.getBytes()));
		}catch (ClientException ce) {
			log.error("【OSS客户端】异常!",ce);
		}finally {
		    if (ossClient != null) {
		        ossClient.shutdown();
		    }
		    //重启客户端
		    ossClient = (OSSClient)applicationContext.getBean(ossClientName);
		}
	}

	@Override
	public void putObject(String bucketName, String OssId, Object obj){
		String json = JsonOperate.ObjToJson(obj);
		putString(bucketName, OssId,json);
	}
	
	@Override
	public String getString(String bucketName, String OssId){
		OSSObject ossObject = ossClient.getObject(bucketName, OssId);
		byte[] bytes = null;
		try(InputStream inputStream = ossObject.getObjectContent()){
			bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("向{}中获取文件{}失败",bucketName,OssId);
		}finally{
			try {
				ossObject.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("向{}中获取String{}成功",bucketName,OssId);
		String str = new String(bytes);	
		return str;
	}
	/*
	 * 注意，T必须有空构造方法，注意json类的细节等问题
	 */
	@Override
	public <T> T getObject(String bucketName, String OssId, Class<T> c) {
		OSSObject ossObject = ossClient.getObject(bucketName, OssId);
		T res = null;
		try(InputStream is = ossObject.getObjectContent()){
			res = JsonOperate.JsonToObj(is, c);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("向{}中获取Object{}失败",bucketName,OssId);
		}finally{
			try {
				ossObject.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.info("向{}中获取Object{}成功",bucketName,OssId);
		return res;
	}
	@Override
	public boolean doseObjectExist(String bucketName, String OssId) {
		return ossClient.doesObjectExist(bucketName, OssId);
	}

	@Override
	public void setObjectAcl(String bucketName, String OssId, CannedAccessControlList acl) {
		log.info("向 {} 中设置ACL {}",bucketName,OssId);
		ossClient.setObjectAcl(bucketName,OssId,acl);
	}

	@Override
	public ObjectAcl getObjectAcl(String bucketName, String OssId) {
		return ossClient.getObjectAcl(bucketName, OssId);
	}

	@Override
	public void deleteObject(String bucketName, String OssId) {
		log.info("向 {} 中删除 {}",bucketName,OssId);
		ossClient.deleteObject(bucketName, OssId);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	

}
