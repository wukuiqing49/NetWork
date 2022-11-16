package com.wu.net.intercept;

import android.util.Log;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/15/22
 * <p>
 * 用途:
 */


public class HttpResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        long startTime=System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        Log.d("请求时长:",(System.currentTimeMillis()-startTime)+"");
        return response;
    }
}
