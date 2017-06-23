package com.blackhole.blackhole.data.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: perqin
 * Date  : 6/2/17
 */

public final class ApiFactory {
    private static BlackholeService mBlackholeService;

    public static BlackholeService blackhole() {
        if (mBlackholeService == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            mBlackholeService = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BlackholeService.API_HOST)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(BlackholeService.class);
        }
        return mBlackholeService;
    }

    private ApiFactory() {}
}
