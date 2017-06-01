package com.blackhole.blackhole.data.retrofit;

import com.blackhole.blackhole.data.entities.Message;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Author: perqin
 * Date  : 6/1/17
 */

public interface BlackholeService {
    @GET("pull/{id}")
    Observable<ArrayList<Message>> getLatestMessage(@Path("id") String userId);

    @POST("submit")
    Observable<Object> sendMessage(@Body Message message);
}
