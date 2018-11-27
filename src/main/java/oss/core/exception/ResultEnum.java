package oss.core.exception;
/**
 * 简历异常和信息之间的关系
 * @author zzp
 *
 */
public enum ResultEnum {
	UNKOWN_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    TOKEN_UNFOUND(1, "token获取失败"),
    OSS_SERVER_EXCEPTION(2, "OSS服务器错误"),
    OSS_CLIENT_EXCEPTION(3,"OSS客户端错误")
    ;
    
    private Integer code;
    
    private String  msg;
    
    private ResultEnum(Integer code,String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

}
