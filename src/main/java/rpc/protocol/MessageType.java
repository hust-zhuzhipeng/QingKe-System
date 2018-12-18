package rpc.protocol;

public enum MessageType {
	REQUEST,
	RESPONSE,
	CONNECT_REQUEST,
	CONNECT_RESPONSE,
	DI_REQUEST,
	DI_RESPONSE,
	OTHER;
	public static MessageType getType(byte t){
		switch(t){
			case (byte)0: return REQUEST;
			case (byte)1: return RESPONSE;
			case (byte)2: return CONNECT_REQUEST;
			case (byte)3: return CONNECT_RESPONSE;
			case (byte)4: return DI_REQUEST;
			case (byte)5: return DI_RESPONSE;
			default: return OTHER;
		}
	}
	public static byte TypeToByte(MessageType type){
		switch(type){
		case REQUEST: return 0;
		case RESPONSE: return 1;
		case CONNECT_REQUEST: return 2;
		case CONNECT_RESPONSE: return 3;
		case DI_REQUEST: return 4;
		case DI_RESPONSE: return 5;
		default: return 6;
	}
	}
}
