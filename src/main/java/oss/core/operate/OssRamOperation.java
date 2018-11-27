package oss.core.operate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
/**
 * 待完善
 * OSS的用户操作，危险！
 * @author 99759
 * 用户权限极大，只应管理员操作
 */
@Service("ossRamOperation")
public class OssRamOperation {
	@Autowired
	@Qualifier("${oss_client}")
	private OSSClient ossClient;
	
	public OSSClient getOssClient() {
		return ossClient;
	}
	
}
