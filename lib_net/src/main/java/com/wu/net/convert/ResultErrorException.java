package com.wu.net.convert;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:9/23/22
 * <p>
 * 用途:
 */


public class ResultErrorException extends Throwable {
    public int state;
    public String message;

    public ResultErrorException(int state, String message) {
        this.message=message;
        this.state=state;
    }
};
