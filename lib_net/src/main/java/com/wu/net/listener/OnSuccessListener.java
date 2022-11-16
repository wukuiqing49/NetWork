package com.wu.net.listener;

import io.reactivex.functions.Consumer;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:9/25/22
 * <p>
 * 用途:可取消请求的 成功处理
 */


public abstract class OnSuccessListener<T> implements Consumer<T> {

    protected abstract void onSuccess(T t);
    @Override
    public void accept(T o)  {
        onSuccess(o);
    }
}
