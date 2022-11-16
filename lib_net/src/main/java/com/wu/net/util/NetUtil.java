package com.wu.net.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.reflect.InvocationTargetException;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:9/22/22
 * <p>
 * 用途:
 */


public class NetUtil {

    /**
     * 判断网络连接
     * <p>需要{@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     */

    public static boolean isConnected(Context context) {
        ConnectivityManager cm=  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE) ;
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private   static Application mApplication= null;

    public static void init(Application context){
        mApplication = context;
    }

    public static Application getApplication()  {
        if (mApplication == null)
            mApplication = getApplicationByReflect();
        return mApplication;
    }

    private static Application getApplicationByReflect()  {
        try {

            Class<?>  activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            return (Application)app ;
        } catch ( NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch ( IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

}
