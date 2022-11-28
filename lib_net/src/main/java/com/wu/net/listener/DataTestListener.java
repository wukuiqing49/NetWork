package com.wu.net.listener;

import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/28/22
 * <p>
 * 用途:
 */


public abstract class DataTestListener<T> {
    protected abstract void onSuccess(T t);

    protected abstract void onFail(int code, String message);



    T data  = null;



    protected abstract void onFailed(int status, String message);


    //收到
    public Consumer<T> next= new Consumer<T>() {
        @Override
        public void accept(T t) throws Exception {

            data=t;
        }
    };
    //异常
    public Consumer<Throwable> err= new Consumer<Throwable>() {
        @Override
        public void accept(Throwable e) throws Exception {

//            onFailed(code, exceptionMessage);


        }
    };
    //结束
    public Action onComplete= new Action(){
        @Override
        public void run() throws Exception {
        }
    };
    // 开始
    Consumer onSubscribe = new  Consumer<Disposable> (){

        @Override
        public void accept(Disposable disposable) throws Exception {

        }
    };

}
