package com.wu.net.listener;

import com.wu.net.convert.ResultErrorException;
import com.wu.net.util.NetConstant;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/16/22
 * <p>
 * 用途:
 */


public abstract class DataListener<T> implements Observer<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof ResultErrorException) {
            onFail(((ResultErrorException) e).state, ((ResultErrorException) e).message);
        } else {
            onFail(NetConstant.ERR_OTHER, NetConstant.ERR_OTHER_MESSAGE);
        }

    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFail(int code, String message);
}
