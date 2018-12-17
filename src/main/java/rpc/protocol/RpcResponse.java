package rpc.protocol;

/**
 * RPC 调用响应
 * @author huangyong
 */
public class RpcResponse {
    private String requestId;
    private String error;
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    @Override
    public String toString() {
    	return "RpcResponse["+"requestId="+requestId+",error="+error+
    			",result="+result+"]";
    }
}
