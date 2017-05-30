package com.blackhole.blackhole.data.repositories;

import com.blackhole.blackhole.framework.Irrelevant;

import io.reactivex.Observable;

/**
 * Author: perqin
 * Date  : 5/25/17
 */

class UsersRepository implements IUsersRepository {
    private static UsersRepository sInstance;

    private UsersRepository() {}

    static UsersRepository getInstance() {
        if (sInstance == null) {
            sInstance = new UsersRepository();
        }
        return sInstance;
    }

    @Override
    public Observable<String> requestNewUserId() {
        return null;
    }

    @Override
    public String getUserId() {
        return "mock_id";
    }

    @Override
    public Observable<Object> refreshLastActiveTime() {
        return Observable.just(Irrelevant.INSTANCE);
    }
}
