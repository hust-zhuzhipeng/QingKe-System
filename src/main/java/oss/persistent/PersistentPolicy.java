package oss.persistent;

import java.util.List;
import java.util.Map;

/**
 * 存储策略
 * 提供的方法：从OSS处获取当前最大文件数 getFileNum(); 注意，文件name空间需要足够大，比如有10位char
 * 2.计算下一文件的name
 * 3.计算第n个文件的name
 * 4.设计访问权限策略，为用户的顺序查找提供权限（用户一定是顺序查找 比如 第5个到第10个）当前提供的策略有两种
 * 		1.Allow, a?a*,?代表一个char，*代表0个或多个char
 * 		2.Deny, 同上。Deny的优先级大于Allow，一个文件，有一个Deny，就不能再被访问
 * 5.要求提供两种策略模式，一种提供精细的（最简单的是，每个文件提供一个Allow语句）
 * 		一种要求提供带步长的，以减少语句量，比如，要求第005个文件，我范围0-9的文件Allow语句00?。
 * 6映射是 id->name id从0开始
 * @author 99759
 *
 */
public interface PersistentPolicy {
	String getBucketName();
	String getFileName();
	/*
	 * zzp提供的方法，返回当前文件数量
	 */
	int getObjectCount();
	/*
	 * 计算下一文件的name
	 */
	String nextName();
	String nextName(int count);	//已知当前文件数
	/*
	 * 计算第n个文件的name
	 */
	String getNameById(int id);
	/*
	 * 计算name文件是第几个(程序员当然从0开始)
	 */
	int getIdByName(String name);
	/**
	 * 
	 * @param isDesc true:从最新的开始 false:从第一个文件开始数
	 * @param offset 0代表第一个
	 * @param limit	 需要的文件数，至少为1。
	 * @return 权限语句集合，Allow与Deny
	 */
	Map<String,List<String>> getExactPolicy(boolean isDesc,int offset,int limit);
	Map<String,List<String>> getVaguePolicy(boolean isDesc,int offset,int limit);
}
