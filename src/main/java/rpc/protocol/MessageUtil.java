package rpc.protocol;

import java.util.HashMap;
import java.util.Map;

/*
 * 为外部提供msg的一些信息
 */
public class MessageUtil {
	//msg的header中addach和key-value匹配
    private static Map<String,Class> attachMap = new HashMap<>();
    //type与body的匹配 待完善此处
	private static Map<Byte,Class> bodyMap = new HashMap<>();
	static {
		attachMap.put("attach", Object.class);
		bodyMap.put(new Byte((byte)0), String.class);
	}
	public static Class getAttachClass(String attachname) throws Exception{
		if(attachMap.containsKey(attachname)){
			return attachMap.get(attachname);
		}else{
			throw new Exception("no such attach is defined ->"+attachname);
		}
	}
	public static Class getbodyClass(byte msgType) throws Exception{
		switch(msgType){
			case (byte)0: return RpcRequest.class;
			case (byte)1: return RpcResponse.class;
			case (byte)2:
			case (byte)3:
			case (byte)4:
			case (byte)5:
			default: throw new Exception("no such msgType is defined ->"+msgType);
		}
	}
}
