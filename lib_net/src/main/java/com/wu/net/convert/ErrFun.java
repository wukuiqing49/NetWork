package com.wu.net.convert;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonSyntaxException;
import com.wu.net.observable.SpecialCodeObservable;
import com.wu.net.util.NetConstant;
import com.wu.net.util.NetUtil;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/16/22
 * <p>
 * 用途: 异常处理的逻辑
 */


public class ErrFun<T> implements Function<Throwable, ObservableSource<T>> {

    Context mContext;

    public ErrFun(Context context) {
        mContext = context;
    }

    @Override
    public ObservableSource<T> apply(@NonNull Throwable throwable) throws Exception {


        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {

                int code = -1;
                String exceptionMessage = "未知异常";
                if (NetUtil.isConnected(mContext)) {

                    if (throwable instanceof SocketTimeoutException) {
                        exceptionMessage = NetConstant.ERR_SOCKET_MESSAGE;
                        code = NetConstant.ERR_SOCKET;
                    } else if (throwable instanceof UnknownHostException) {
                        exceptionMessage = NetConstant.ERR_SERVICE_MESSAGE;
                        code = NetConstant.ERR_SERVICE;
                    } else if (throwable instanceof JsonSyntaxException) {
                        exceptionMessage = NetConstant.ERR_SERVICE_MESSAGE;
                        code = NetConstant.ERR_SERVICE;
                    } else if (throwable instanceof HttpException) {
                        HttpException httpException = (HttpException) throwable;
                        exceptionMessage = httpException.getMessage();
                        code = httpException.code();
                    } else if (throwable instanceof ResultErrorException) {
                        ResultErrorException httpException = (ResultErrorException) throwable;
                        exceptionMessage = httpException.message;
                        code = httpException.state;
                    } else {
                        exceptionMessage = NetConstant.ERR_OTHER_MESSAGE;
                        code = NetConstant.ERR_OTHER;
                    }

                } else {
                    exceptionMessage = NetConstant.NETFAILCODE_MESSAGE;
                    code = NetConstant.NETFAILCODE;
                }
                if (isContains(code)) {
                    //特定code 发送单独处理
                    SpecialCodeObservable.getInstance().sendSpecialCode(code);
                } else {
                    ResultErrorException errorException = new ResultErrorException(code, exceptionMessage);
                    emitter.onError(errorException);
                }
            }
        });
    }

    /**
     *
     * @param code
     * @return 返回是否是特殊的code
     */
    private boolean isContains(int code) {
        boolean isHave = false;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(), 0);
        String specialCode = sharedPreferences.getString(NetConstant.SPECIAL_CODE_KEY, "");

        for (String s : specialCode.split(",")) {
            if (s.equals(code + "")) {
                isHave = true;
                break;
            }
        }

        return isHave;
    }
}
