package com.blackhole.blackhole.data.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.blackhole.blackhole.data.entities.User;
import com.blackhole.blackhole.data.retrofit.ApiFactory;
import com.blackhole.blackhole.data.retrofit.BlackholeService;
import com.blackhole.blackhole.framework.RxResult;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: perqin
 * Date  : 5/25/17
 */

class UsersRepository implements IUsersRepository {
    private static final String PK_USER_ID = "USER_ID";

    private static UsersRepository sInstance;

    private final BlackholeService mService;
    private final SharedPreferences mSp;

    static UsersRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UsersRepository(context);
        }
        return sInstance;
    }

    private UsersRepository(Context context) {
        mService = ApiFactory.blackhole();
        mSp = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    @Override
    public Observable<RxResult<User>> requestNewUserId() {
        return mService.createUser()
                .doOnNext(this::saveUserIdToLocal)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(RxResult::result).onErrorReturn(RxResult::error);
    }

    @Override
    public String getUserId() {
        return mSp.getString(PK_USER_ID, "");
    }

    @Override
    public Observable<Object> refreshLastActiveTime() {
        return mService.updateLastOnlineTime(getUserId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private void saveUserIdToLocal(User user) {
        mSp.edit().putString(PK_USER_ID, user.getId()).apply();
    }

    @Override
    public String setUserNickname() {
        return null;
    }

    @Override
    public String getUserNickname() {
        return null;
    }
}
