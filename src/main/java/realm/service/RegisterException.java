package realm.service;


/**
 * 注册失败
 * @author zzp
 *
 */
public class RegisterException extends RuntimeException{
	private static final long serialVersionUID = 392080207107158221L;
	private final int code;
	private final String msg;
	public RegisterException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public RegisterException(RegisterExceptionEnum resultEnum){
		this(resultEnum.getCode(),resultEnum.getMsg());
	}
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
}
