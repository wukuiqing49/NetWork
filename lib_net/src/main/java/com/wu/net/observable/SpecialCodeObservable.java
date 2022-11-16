package com.wu.net.observable;

import java.util.Observable;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/16/22
 * <p>
 * 用途:  特殊code 指定返回   (登录失效,Vip到期,IM单点登录)
 */


public class SpecialCodeObservable extends Observable {

    private static volatile SpecialCodeObservable instance;

    public static SpecialCodeObservable getInstance() {

        if (instance == null) {
            synchronized (SpecialCodeObservable.class) {
                instance = new SpecialCodeObservable();
            }
        }
        return instance;
    }

    /**
     * 发送特殊code
     *
     * @param code
     */
    public void sendSpecialCode(int code) {
        setChanged();
        notifyObservers(code);

    }
}
