package com.blackhole.blackhole.data.repositories;

import io.reactivex.Observable;

/**
 * Author: perqin
 * Date  : 5/25/17
 */

class UsersRepository implements IUsersRepository {
    private static UsersRepository sInstance;

    static UsersRepository getInstance() {
        if (sInstance == null) {
            sInstance = new UsersRepository();
        }
        return sInstance;
    }

    private UsersRepository() {}

    @Override
    public Observable<String> requestNewUserId() {
        return null;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public Observable<Object> refreshLastActiveTime(String userId) {
        return null;
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
