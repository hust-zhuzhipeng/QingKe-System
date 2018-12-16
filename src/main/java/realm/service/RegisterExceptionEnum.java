package realm.service;

public enum RegisterExceptionEnum{
	UNKOWN_ERROR(-1, "未知错误,注册失败"),
    SUCCESS(0, "注册成功"),
    Mail_Register_Exception(1, "mail注册失败"),
    Phone_Register_Exception(2, "phone注册失败"),
    VerificationCode_Expired(3,"验证码失效")
    ;
    
    private Integer code;
    
    private String  msg;
    
    private RegisterExceptionEnum(Integer code,String msg) {
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
