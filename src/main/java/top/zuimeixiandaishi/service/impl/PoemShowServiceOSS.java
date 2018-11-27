package top.zuimeixiandaishi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oss.core.operate.OSSOperationObject;
import oss.core.token.OSSTokenManager;
import oss.core.token.domain.PolicyEnum;
import oss.core.token.domain.Token;
import oss.persistent.PersistentPolicy;
import top.zuimeixiandaishi.domain.PoemShow;
import top.zuimeixiandaishi.service.PoemShowService;
@Service("poemShowService")
public class PoemShowServiceOSS implements PoemShowService{
	@Autowired
	PersistentPolicy poemShowPersistentPolicy;
	@Autowired
	OSSOperationObject oSSOperationObject;
	@Autowired
	OSSTokenManager oSSTokenManager;
	private String bucketName;	//poemshow所在的bucket名
	private String fileName;	//存有poemshow的文件名
	@PostConstruct
	void init(){
		bucketName = poemShowPersistentPolicy.getBucketName();
		fileName = poemShowPersistentPolicy.getFileName();
	}
	@Override
	public List<PoemShow> getPoemShow(boolean isDesc, int offset, int limit) {
		List<PoemShow> res = new ArrayList<>();
		int num = poemShowPersistentPolicy.getObjectCount();
		int start = 0,end=0;
		//诗歌数量
		if(isDesc){
			end = num-offset;
			start = end-limit;
		}else{
			start = offset;
			end = offset+limit;
		}
		if(start<0) start = 0;
		if(end>=num) end = num;
		for(int id=start;id<end;id++){
			String poemshowcontext = oSSOperationObject
					.getString(poemShowPersistentPolicy.getBucketName()	,
							poemShowPersistentPolicy.getNameById(id));
			// TODO 将poemshowcontext转成PoemShow
			System.out.println(poemshowcontext);
		}
		return res;
	}

	@Override
	public int addPoemShow(PoemShow poemShow) {
		int count = poemShowPersistentPolicy.getObjectCount();	//当前文件数
		String nextfilename = poemShowPersistentPolicy.nextName(count);
		oSSOperationObject.putObject(bucketName, nextfilename, poemShow);
		oSSOperationObject.putString(bucketName, fileName, (count+1)+"");
		return 1;
	}

	@Override
	public int updatePoemShow(PoemShow poemShow) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int count() {
		return  poemShowPersistentPolicy.getObjectCount();
	}

	@Override
	public Token getToken(boolean isDesc, int offset, int limit) {
		return oSSTokenManager.getReadToken(PolicyEnum.READ_POEMSHOW, isDesc, offset, limit);
	}

}
