package com.wu.network.api;

import com.wu.net.retrofit.NetInit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Request;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/15/22
 * <p>
 * 用途:
 */


public class NetApi extends NetInit {

    private static volatile NetApi sInstance;

    public NetApi() {
        super("http://tcq.qianxy.top/");
    }

    public static NetApi getInstance() {
        if (sInstance == null) {
            synchronized (NetApi.class) {
                sInstance = new NetApi();
            }
        }
        return sInstance;
    }

    @Override
    public   TestApi getMyService() {
        return super.getService(TestApi.class);
    }

    // 添加自定义Header
    @Override
    public Request.Builder addCustomHeader(Request.Builder builder) {
        String version = 1047 + "";
        String pageName = "com.qxy.teleprompter";
        String source = "Android";
        String timestamp = (int) (System.currentTimeMillis() / 1000) + "";

        String str = "version=" + version + ",source=" + source + ",timestamp=" + timestamp;
        String sign = encrypt(str);

        builder.addHeader("sign", sign)
                .addHeader("version", version)
                .addHeader("timestamp", timestamp)
                .addHeader("source", source)
                .addHeader("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcGVuaWQiOiJvYkhRdjVoZXBxa3BaVWN0cjJKVXNKUGF1VjFrIiwiYXBwX29wZW5pZCI6Im9iSFF2NWhlcHFrcFpVY3RyMkpVc0pQYXVWMWsiLCJhcHBfaWQiOjEwMDAwLCJpZCI6MSwibmlja25hbWUiOiJBbmRyb2lkIiwidXNlcl9waG9uZSI6bnVsbCwiaGVhZGltZ3VybCI6Imh0dHBzOi8vdGhpcmR3eC5xbG9nby5jbi9tbW9wZW4vdmlfMzIvUTBqNFR3R1RmVEwzWmlic1lzT05UbTVVc2liRjZkQnVWNUJzSG0wMTFJb3cxcHNwMGxhRHJjcmo1aWJpYlRkdHVGcFg0RHBTbWhvN2xINXdISVhxN0tITnN3LzEzMiIsInNleCI6MCwiZXhwIjoxNjcxMDcxMjY1LjU1NDU3MzV9.FdGsS8a-9PvkgPform327ZeIAmYlFtsx6D72TPiJL98")
                .addHeader("pageName", pageName)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "text/plain");

        return builder;
    }

    public String encrypt(String raw) {
        String md5Str = raw;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // 创建一个MD5算法对象
            md.update(raw.getBytes()); // 给算法对象加载待加密的原始数据
            byte[] encryContext = md.digest(); // 调用digest方法完成哈希计算
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < encryContext.length; offset++) {
                i = encryContext[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
                // 把字节数组逐位转换为十六进制数
            }
            md5Str = buf.toString(); // 拼装加密字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Str.toUpperCase(); // 输出大写的加密串
    }
}
