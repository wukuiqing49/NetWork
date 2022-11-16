package com.wu.network.api;


import com.wu.net.api.BaseInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:11/16/22
 * <p>
 * 用途:
 */


public interface TestApi {

    @GET("api/User/GetInfo")
    Observable<BaseInfo<UserInfo>> getUserInfo();

}