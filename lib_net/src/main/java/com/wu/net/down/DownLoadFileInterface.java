package com.wu.net.down;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:10/19/22
 * <p>
 * 用途:
 */


public interface DownLoadFileInterface {

    /**
     *
     *文件下载
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
