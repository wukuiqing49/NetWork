package com.wu.net.intercept;

import android.text.TextUtils;

import com.wu.net.model.NetInitInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:9/22/22
 * <p>
 * 用途:
 */


public abstract class HttpAddHeadersInterceptor implements Interceptor {
    NetInitInfo mLogcatInfo;
    Request.Builder builder;

    public HttpAddHeadersInterceptor(NetInitInfo info) {
        mLogcatInfo = info;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        builder = request.newBuilder();
        Request.Builder mBuilder = addHeader(builder);
        if (mLogcatInfo == null) {
            request = mBuilder
                    .addHeader("PhoneType", "Android")
                    .build();
        } else {
            mBuilder.addHeader("PhoneType", "Android");
            mBuilder.addHeader("PageName", TextUtils.isEmpty(mLogcatInfo.getAndroidPageName()) ? "" : mLogcatInfo.getAndroidPageName());
            mBuilder.addHeader("AppVersionCode", TextUtils.isEmpty(mLogcatInfo.getAndroidVersionCode()) ? "" : mLogcatInfo.getAndroidVersionCode());
            mBuilder.addHeader("AppVersionName", TextUtils.isEmpty(mLogcatInfo.getAndroidVersionName()) ? "" : mLogcatInfo.getAndroidVersionName());
            request = mBuilder.build();
        }
        return chain.proceed(request);

    }

    public abstract Request.Builder addHeader(Request.Builder builder);


}
