package com.wu.network.application;

import androidx.multidex.MultiDexApplication;

import com.wu.net.model.NetInitInfo;
import com.wu.net.observable.SpecialCodeObservable;
import com.wu.net.retrofit.NetInit;
import com.wu.network.BuildConfig;

import java.util.Observable;
import java.util.Observer;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/16/22
 * <p>
 * 用途:
 */


public class NetApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //网络请求参数, code  包名, 特殊code
        NetInit.init(this, new NetInitInfo() {
            @Override
            public String getAndroidVersionCode() {
                return BuildConfig.VERSION_CODE+"";
            }

            @Override
            public String getAndroidVersionName() {
                return BuildConfig.VERSION_NAME+"";
            }

            @Override
            public String getAndroidPageName() {
                return BuildConfig.APPLICATION_ID+"";
            }

            @Override
            public String getSpecialCode() {
                return "601,605";
            }

            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }
        });
    }


}
