package rpc.protocol;

import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * rpc编码器
 * @author zzp
 * 将message转换成ByteBuffer
 */
public class RpcEncoder extends MessageToByteEncoder <NettyMessage> {

	public RpcEncoder(){};
	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
		out.clear();
		ByteBuf sendBuf = out;
		sendBuf.writeInt(msg.getCrcCode());
		sendBuf.writeInt(msg.getLength());
		sendBuf.writeLong(msg.getSessionID());
		sendBuf.writeByte(msg.getType());
		sendBuf.writeByte(msg.getPriority());
		
		//附加头信息
		Map<String,Object> attachment = msg.getAttachment();
		if(attachment == null || attachment.size()==0){
			sendBuf.writeInt(0);
		}else{
			String key = null;
			byte[] keyArray = null;
			Object value = null;
			sendBuf.writeInt(attachment.size());
			for(Map.Entry<String, Object> m : attachment.entrySet()){
				key = m.getKey();
				keyArray = key.getBytes("UTF-8");
				sendBuf.writeInt(keyArray.length);
				sendBuf.writeBytes(keyArray);
				value = m.getValue();
				byte[] obj = SerializationUtil.serialize(value);
				sendBuf.writeInt(obj.length);
				sendBuf.writeBytes(obj);
			}
		}
		if(msg.getBody()==null){
			sendBuf.writeInt(0);
		}else{
			byte[] body = SerializationUtil.serialize(msg.getBody());
			sendBuf.writeInt(body.length);
			sendBuf.writeBytes(body);
		}
		// 在第4个字节出写入Buffer的长度
        int readableBytes = sendBuf.readableBytes();
        //发送数据包长度 = 长度域的值 + lengthFieldOffset + lengthFieldLength + lengthAdjustment
        sendBuf.setInt(4, readableBytes-8);
	}
}
