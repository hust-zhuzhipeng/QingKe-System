package rpc.protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
/**
 * rpc解码器
 * @author zzp
 * 将byteBuf转换为NettyMessage
 */
public class RpcDecoder extends ByteToMessageDecoder{
	Map<String, Class> attachMap;	
	Map<Byte, Class> bodyMap;
	public RpcDecoder(Map<String, Class> attachMap,Map<Byte, Class>bodyMap){
		this.attachMap = attachMap;
		this.bodyMap = bodyMap;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes()<4){
			return;
		}
		ByteBuf frame = in;
		NettyMessage msg = new NettyMessage();
		MsgHeader header = new MsgHeader();
		header.setCrcCode(frame.readInt());
		int len = frame.readInt();
        header.setLength(len);     
        header.setSessionID(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());
        //attachment数量
        int size = frame.readInt();
        if (size > 0) {
        	Map<String,Object> attch = new HashMap<>();
        	int keysize = 0;
        	byte[] keyArray = null;
        	String key = null;
        	int valuesize=0;
        	byte[] valueArray=null;
        	//获取每个attch
        	for(int i=0;i<size;i++){
        		keysize = frame.readInt();
        		keyArray = new byte[keysize];
                frame.readBytes(keyArray);
                key = new String(keyArray, "UTF-8");
                
                valuesize = frame.readInt();
                valueArray = new byte[valuesize];
                frame.readBytes(valueArray);
                attch.put(key, SerializationUtil.deserialize(valueArray, attachMap.get(key)));
        	}
        }
        //body
        int bodysize = frame.readInt();
        if(bodysize >0){
        	byte[] bodyArray = new byte[bodysize];
        	frame.readBytes(bodyArray);
            	Object o = SerializationUtil.deserialize(bodyArray, bodyMap.get(header.getType()));
            	msg.setBody(o);           
        }
        msg.setHeader(header);
        out.add(msg);
	}

}
