package oss.core.operate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.StorageClass;

/**
 * ！！还未完善
 * bucket的操作权限
 * @author zzp
 * 该类的权限很危险，只应该zzp拥有！
 */
@Service("oSSOperationBucket")
public class OSSOperationBucket{
	@Autowired
	private OSSClient ossClient;
	private static Logger log = LoggerFactory.getLogger(OSSOperationBucket.class);
	/**
	 * 创建空间
	 * @param bucketName Bucket name
	 * @param acl	存储权限 Private、PublicRead、PublicReadWrite
	 * @param scl	访问类型 标准存储类型（Standard）低频访问存储类型（Infrequent Access）归档存储类型（Archive）
	 * @return
	 */
	public Bucket createBucket(String bucketName,CannedAccessControlList acl,StorageClass scl){
		CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
		createBucketRequest.setCannedACL(acl);
		createBucketRequest.setStorageClass(scl);
		log.info("创建Bucket{}",bucketName);
		return ossClient.createBucket(createBucketRequest);
	}
	/**
	 * 列举空间
	 * @return
	 */
	public List<Bucket> buckets(){
		return ossClient.listBuckets();
	}
	/**
	 * 判断空间是否存在
	 * @param bucketName
	 * @return
	 */
	boolean doseBucketExists(String bucketName){
		return ossClient.doesBucketExist(bucketName);
	}
	public OSSClient getOssClient() {
		return ossClient;
	}
	public void setOssClient(OSSClient ossClient) {
		this.ossClient = ossClient;
	}

}
