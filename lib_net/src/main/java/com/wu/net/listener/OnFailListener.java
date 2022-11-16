package com.wu.net.listener;

import com.wu.net.convert.ResultErrorException;
import com.wu.net.util.NetConstant;

import io.reactivex.functions.Consumer;


/**
 * 作者:吴奎庆
 * <p>
 * 时间:9/25/22
 * <p>
 * 用途:  可取消请求的 错误处理
 */


public abstract class OnFailListener<Throwable> implements Consumer<Throwable> {

    public abstract void onFail(int code, String message);


    @Override
    public void accept(Throwable e) throws Exception {
        if (e instanceof ResultErrorException) {
            onFail(((ResultErrorException) e).state, ((ResultErrorException) e).message);
        } else {
            onFail(NetConstant.ERR_OTHER, NetConstant.ERR_OTHER_MESSAGE);
        }
    }
}
