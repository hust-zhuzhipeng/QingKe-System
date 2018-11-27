package util;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * json的相关操作
 * 对象转json，json转对象
 * 注意，该类是需要同步的，使用了java内置锁进行同步
 */
public class JsonOperate {
	private final static ObjectMapper mapper = new ObjectMapper();
	/*
	 * 将obj转为json字符串
	 */
	public static String ObjToJson(Object obj){
		String json = null;
		try {
			synchronized(mapper){
				json = mapper.writeValueAsString(obj);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	/*
	 * 将json字符串转为obj
	 */
	public static <T> T JsonToObj(String json,Class<T> c){
		T res = null;
		try {
			res = mapper.readValue(json, c);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	public static <T> T JsonToObj(InputStream jsonStream,Class<T> c){
		T res = null;
		try {
			res = mapper.readValue(jsonStream, c);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
