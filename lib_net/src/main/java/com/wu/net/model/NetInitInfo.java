package com.wu.net.model;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/15/22
 * <p>
 * 用途:  初始化所需的字段
 */


public interface NetInitInfo {
    String getAndroidVersionCode();
    String getAndroidVersionName();
    String getAndroidPageName();
    String getSpecialCode();
    boolean isDebug();

}
