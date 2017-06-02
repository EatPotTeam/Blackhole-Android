package com.blackhole.blackhole.data.retrofit;

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
            mBlackholeService = new Retrofit.Builder()
                    .baseUrl(BlackholeService.API_HOST)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(BlackholeService.class);
        }
        return mBlackholeService;
    }

    private ApiFactory() {}
}
