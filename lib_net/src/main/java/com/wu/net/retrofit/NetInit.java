package com.wu.net.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.wu.net.intercept.HttpAddHeadersInterceptor;
import com.wu.net.intercept.HttpResponseInterceptor;
import com.wu.net.model.NetInitInfo;
import com.wu.net.util.NetConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/15/22
 * <p>
 * 用途:
 */


public abstract class NetInit {
    String baseUrl = "";
    private static NetInitInfo logcatInfo;

    // 设置BaseUrl
    public NetInit(String url) {
        baseUrl = url;
    }

    //初始化
    public static void init(NetInitInfo info) {
        logcatInfo = info;
    }

    public static void init(Context context, NetInitInfo info) {
        logcatInfo = info;
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NetConstant.SPECIAL_CODE_KEY, info.getSpecialCode());
        editor.apply();
    }

    // 获取Service
    public <T> T getService(Class<T> tClass) {
        return getRetrofit().create(tClass);
    }

    // 获取Retrofit
    private Retrofit getRetrofit() {

        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 数据转换
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;


    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.readTimeout(NetConstant.CONNECTTIME, TimeUnit.SECONDS);
        //链接时间
        builder.connectTimeout(NetConstant.CONNECTTIME, TimeUnit.SECONDS);
        //设置连接池
        builder.connectionPool(new ConnectionPool(5, 5, TimeUnit.SECONDS));
        //配置header 拦截器
        HttpAddHeadersInterceptor httpAddHeadersInterceptor = new HttpAddHeadersInterceptor(logcatInfo) {
            @Override
            public Request.Builder addHeader(Request.Builder builder) {
                return addCustomHeader(builder);
            }
        };
        //配置数据处理拦截器
        HttpResponseInterceptor httpResponseInterceptor = new HttpResponseInterceptor();
        builder.addInterceptor(httpAddHeadersInterceptor);
        builder.addInterceptor(httpResponseInterceptor);
        if (logcatInfo != null && logcatInfo.isDebug()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();

    }

    protected abstract Request.Builder addCustomHeader(Request.Builder builder);

}
