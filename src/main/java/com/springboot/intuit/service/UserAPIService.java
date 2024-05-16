package com.springboot.intuit.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserAPIService {
    @GET("intuit/api/user/{userId}/{modifyType}")
    Call<Long> modifyBonusOfUserId(@Path("userId") Long userId,@Path("modifyType") String modifyType);

    @GET("intuit/api/user/{userId}/bonus")
    Call<Long> getBonusOfUserId(@Path("userId") Long userId);

}

