package oss.core.operate;

import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectAcl;

/**
 *   Oss对象传输接口，为方便不同的数据格式，如json,String等，
 * 特设置接口
 *   该对象的权限应该赋予管理员,
 *   另外，删除，添加等操作前应该充分检查文件是否已经存在
 * @author 99759
 *
 */

public interface OSSOperationObject {
	/**
	 * 上传字符串，默认编码UTF-8
	 */
	void putString(String bucketName,String OssId,String content);
	/**
	 * 上传对象，这里对象的表达方式取决于实现类，不做限制，自己把握~
	 */
	void putObject(String bucketName,String OssId,Object obj);
	
	/**
	 * 下载为字符串
	 */
	String getString(String bucketName,String OssId);
	/**
	 * 下载文件，转为T对象
	 */
	<T> Object getObject(String bucketName,String OssId,Class<T> cla);
	/**
	 * 判断文件是否存在
	 */
	boolean doseObjectExist(String bucketName,String OssId);
	/**
	 * 设置权限 Default为遵循存储空间的访问权限。
	 */
	void setObjectAcl(String bucketName,String OssId,CannedAccessControlList acl);
	/**
	 * 获取文献权限
	 */
	ObjectAcl getObjectAcl(String bucketName,String OssId);
	/**
	 * 删除文献
	 */
	void deleteObject(String bucketName,String OssId);
}
