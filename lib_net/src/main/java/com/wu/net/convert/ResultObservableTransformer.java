package com.wu.net.convert;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:10/14/22
 * <p>
 * 用途: 线程切换,数据处理,异常处理
 */


public class ResultObservableTransformer<T> implements ObservableTransformer<T,T> {
    Context mContext;
    public ResultObservableTransformer(Context context){
        mContext=context;
    }
    @NonNull
    @Override
    public ObservableSource<T> apply(@NonNull Observable<T> upstream) {

        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //异常处理
                .flatMap(new ResultErrFun<T>())
                .onErrorResumeNext(new ErrFun<T>(mContext));
    }
}
