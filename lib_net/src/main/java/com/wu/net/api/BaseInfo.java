package com.wu.net.api;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:9/22/22
 * <p>
 * 用途:
 */


public class BaseInfo<T>  extends Object{


    public int resultCode;
    public String message;
    public T data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
