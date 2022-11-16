package com.wu.net.convert;

import com.wu.net.api.BaseInfo;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:10/13/22
 * <p>
 * 用途: 统一错误处理
 */


public class ResultErrFun<T> implements Function<T, Observable<T>> {

    @Override
    public Observable<T> apply(@NonNull T tBaseInfo) throws Exception {
        if (tBaseInfo instanceof BaseInfo) {
            BaseInfo baseInfo = (BaseInfo) tBaseInfo;
            if (baseInfo.resultCode == 200) {
                return Observable.just((T) baseInfo);
            } else {
                return Observable.error(new ResultErrorException(baseInfo.resultCode, baseInfo.message));
            }

        } else {
            return Observable.just(tBaseInfo);
        }

    }
}