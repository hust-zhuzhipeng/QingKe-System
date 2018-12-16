package common.aliyun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import oss.core.operate.OSSOperationObject;
import oss.core.token.OSSTokenManager;
import oss.core.token.domain.Policy;
import oss.core.token.domain.PolicyEnum;
import oss.core.token.domain.Token;
import oss.persistent.HzPersistentPolicy2;
import oss.persistent.PersistentPolicy;
import oss.service.TokenServiceImpl;
import realm.domain.User;
import util.JsonOperate;

public class Test2 {
	public static void main(String[]args) throws Exception{
		String b = "zuimeixiandaishi";
		ApplicationContext ap = 
				new ClassPathXmlApplicationContext(
						new String[]{"classpath:/spring/spring-ehcache.xml"
								,"classpath:/spring/spring-aliyun.xml"
								,"classpath:/spring/spring-dao.xml"
								,"classpath:/spring/spring-service.xml"
								,"classpath:/spring/spring-util.xml"});
		OSSOperationObject m = (OSSOperationObject) ap.getBean("oSSOperationObject");
		OSSTokenManager manager = (OSSTokenManager)ap.getBean("oSSTokenManager");
		System.out.println(m.getString("zuimeixiandaishi", "PoemShowNum"));
		System.out.println("aaa");
		/*Token t = manager.getReadToken(PolicyEnum.READ_POEMSHOW,"haha");
		System.out.println(t);*/
		/*System.out.println("------");
		Token t1 = manager.getCommonToken(PolicyEnum.READ_POEMSHOW, "PoemShowNum","READ_POEMSHOW:PoemShowNum");
		//Thread.sleep(1000);
		Token t2 = manager.getCommonToken(PolicyEnum.READ_POEMSHOW, "1","READ_POEMSHOW:PoemShowNum");
		//Thread.sleep(1000);
		Token t3 = manager.getCommonToken(PolicyEnum.READ_POEMSHOW, "2","READ_POEMSHOW:PoemShowNum");
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t3);*/
		
	
		
	}
	
	public static String RamMethod(OSSClient ossClient,String bucketName,String objname) throws IOException{
		OSSObject ossObject = ossClient.getObject(bucketName, objname);
		BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
		StringBuilder str = new StringBuilder();
		while(true){
			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if (line == null) break;
			str.append(line+"\n");
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str.toString();
	}
	private static  String generateID(int id,int len){
		String sid = id+"";
		StringBuilder str = new StringBuilder();
		int l = len-sid.length();
		for(int i=0;i<l;i++){
			str.append('0');
		}
		str.append(sid);
		return str.toString();
	}
	public static OSSClient getClient(Token token){
		return new OSSClient("http://oss-cn-shenzhen.aliyuncs.com",token.getAccessKeyId(),token.getAccessKeySecret(),
				token.getToken());
	}
}
