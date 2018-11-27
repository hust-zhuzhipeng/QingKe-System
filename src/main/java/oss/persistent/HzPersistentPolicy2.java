package oss.persistent; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import oss.core.operate.OSSOperationObject;

public class HzPersistentPolicy2 implements PersistentPolicy{
	@Autowired
	private OSSOperationObject oSSOperationObject;
	private String bucketName;
	private String fileName;
	private static Logger log = LoggerFactory.getLogger(HzPersistentPolicy2.class);
	
	private final static int optimize = 51;
	private final static int lengthOfName = 16;
	private final static String initName = "0000000000000000";
	
	public HzPersistentPolicy2(String bucketName,String fileName){
		this.bucketName = bucketName;
		this.fileName = fileName;
		log.info("HzPersistentPolicy2构造成功,bucketName={},fileName={}",bucketName,fileName);
	}
	
	@Override
	public int getObjectCount() {
		String num = oSSOperationObject.getString(bucketName, "PoemShowNum");
		log.info("PoemShowNum={}",num);
		int res = Integer.parseInt(num);
		return res;
	}
	@Override
	public String nextName(int count) {
		return Integer.toBinaryString(count);
	}
	@Override
	public String nextName() {
		int count = getObjectCount();
		String subName = Integer.toBinaryString(count);
		StringBuilder name = new StringBuilder(HzPersistentPolicy2.initName);
		name.delete(HzPersistentPolicy2.lengthOfName-1 - subName.length(), HzPersistentPolicy2.lengthOfName-1);
		return name.append(subName).toString();
	}

	@Override
	public String getNameById(int id) {
		id = id>getObjectCount()?0:id;
		if(id < 0) {
			return "-1";
		}
		String subName = Integer.toBinaryString(id);
		StringBuilder name = new StringBuilder(HzPersistentPolicy2.initName);
		name.delete(HzPersistentPolicy2.lengthOfName-1 - subName.length(), HzPersistentPolicy2.lengthOfName-1);
		return name.append(subName).toString();
	}

	@Override
	public int getIdByName(String name) {
		// TODO Auto-generated method stub
		int result =  Integer.parseInt(name, 2);
		if(result > getObjectCount()) {
			return -1;
		}
		return result;
	}

	@Override
	public Map<String, List<String>> getExactPolicy(boolean isDesc, int offset, int limit) {
		// TODO Auto-generated method stub
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		int start = isDesc?getObjectCount()-1:0;
		start += offset;
		if(start < 0) {
			start = 0;
		}
		int end = start + limit;
		if(limit < optimize) {
			List<String> allowCommand = new ArrayList<>();
			for(int i=start;i<end;i++) {
				allowCommand.add(getNameById(i));
			}
			result.put("Allow", allowCommand);
		}
		return result;
	}

	@Override
	public Map<String, List<String>> getVaguePolicy(boolean isDesc, int offset, int limit) {
		return getExactPolicy(isDesc,offset,limit);
	}

	public OSSOperationObject getoSSOperationObject() {
		return oSSOperationObject;
	}

	public void setoSSOperationObject(OSSOperationObject oSSOperationObject) {
		this.oSSOperationObject = oSSOperationObject;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
