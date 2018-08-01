package org.test.web.exception;

/**
 * http 响应失败异常
 * Date: 2018/5/14 17:29
 */
public class HttpResponseNotOkException extends RuntimeException {

    private String errorCode;
    private String errorDesc;

    public HttpResponseNotOkException() {
        super();
    }

    public HttpResponseNotOkException(Throwable t) {
        super(t);
    }

    public HttpResponseNotOkException(String code, String message, Throwable t) {
        super(t);
        this.errorCode = code;
        this.errorDesc = message;
    }

    public HttpResponseNotOkException(String code, String message) {
        super(message);
        this.errorCode = code;
        this.errorDesc = message;
    }

    public HttpResponseNotOkException(String message) {
        super(message);
        this.errorDesc = message;
    }

    public HttpResponseNotOkException(String message, Throwable t) {
        this(null, message, t);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
    
}
