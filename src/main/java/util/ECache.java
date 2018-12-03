package util;
/**
 * 缓存服务接口
 * @author 99759
 *
 */
public interface ECache<K,V> {
	//在缓存中
	boolean InCache(K key);
	//获取v,优先从缓存中获取
	V getValue(K key);
	//存入缓存
	void putValue(K key,V value);
}
