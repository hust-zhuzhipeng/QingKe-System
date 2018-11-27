package oss.persistent;

/**
 * 命名服务:为Poem和Author提供命名服务
 * @author zzp
 * Poem和Author需要存入OSS，因此需要提供一套obj->OSSFileName的服务
 */
public interface NameService {
	//为author生成OSSname
	String getOSSAuthorName(String authorName, int authorIndex);
	//为poem生成OSSname
	String getOSSPoemName(String title, String authorName, int authorIndex);
}
