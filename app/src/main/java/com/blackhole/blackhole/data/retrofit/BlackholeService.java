package com.blackhole.blackhole.data.retrofit;

import com.blackhole.blackhole.BuildConfig;
import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.data.entities.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Author: perqin
 * Date  : 6/1/17
 */

public interface BlackholeService {
    String API_HOST = BuildConfig.API_HOST;

    @POST("register")
    Observable<User> createUser();

    @GET("login/{id}")
    Observable<Object> updateLastOnlineTime(@Path("id") String userId);

    @GET("messages")
    Observable<ArrayList<Message>> getLatestMessage(@Query("userId") String userId);

    @POST("messages")
    Observable<Message> sendMessage(@Body Message message);
}
