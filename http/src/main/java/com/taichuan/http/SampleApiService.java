package com.taichuan.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gui on 2017/7/14.
 * 常规ApiService接口
 */
public interface SampleApiService {
    /**
     * 登陆接口
     */
    @FormUrlEncoded
    @POST("/MobileApi/House/Login")
    Call<ResponseBody> Login(@Field("account") String account, @Field("password") String password);
}
