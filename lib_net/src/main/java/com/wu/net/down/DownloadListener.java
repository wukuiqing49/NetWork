package com.wu.net.down;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:10/19/22
 * <p>
 * 用途:
 */


public interface DownloadListener {
    void onStart();

    void onProgress(int currentLength);

    void onFinish(String localPath);

    void onFailure();
}