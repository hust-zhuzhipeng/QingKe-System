package oss.core.exception;

/**
 * 根异常，所有的OSS异常都被封装
 * 异常通过 ResultEnum 被重新定义分类
 * @author zzp
 *
 */
public class QKException extends RuntimeException{

	private static final long serialVersionUID = 278685716716994854L;
	private final int code;
	private final String msg;
	public QKException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public QKException(ResultEnum resultEnum){
		this(resultEnum.getCode(),resultEnum.getMsg());
	}
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
}
