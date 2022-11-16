package com.wu.net.util;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/16/22
 * <p>
 * 用途:
 */


public class NetConstant {
    public  static String SPECIAL_CODE_KEY="specialCode";

    //连接时长
    public static long CONNECTTIME = 5000L;

    //成功Code
    public  static int  SUCESSCODE = 200;
    public  static String  SUCESSCODE_MESSAGE = "成功";

    //错误Code
    public  static int  FAILCODE = -2;
    public  static String  FAILCODE_MESSAGE = "服务器异常";

    //网络异常Code
    public  static int  NETFAILCODE = -1;
    public  static String  NETFAILCODE_MESSAGE= "网络异常";

    //错误Code
    public  static int  ERR_SERVICE = -3;
    public static String  ERR_SERVICE_MESSAGE= "数据异常";

    public  static int  ERR_SOCKET= -4;
    public static String  ERR_SOCKET_MESSAGE= "连接超时";

    public  static int  ERR_OTHER= -5;
    public static String  ERR_OTHER_MESSAGE= "未知异常";
}
