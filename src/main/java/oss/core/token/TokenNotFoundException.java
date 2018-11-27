package oss.core.token;

import oss.core.exception.QKException;
import oss.core.exception.ResultEnum;

/**
 * 自定义的Token申请失败异常
 * @author zzp
 *
 */
public class TokenNotFoundException extends QKException{
	private static final long serialVersionUID = 2974345908267975120L;
	public TokenNotFoundException(int code, String msg) {
		super(code, msg);
	}
	public TokenNotFoundException(ResultEnum resultEnum) {
		super(resultEnum);
	}
}
