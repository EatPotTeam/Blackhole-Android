package com.blackhole.blackhole.data.repositories;

import io.reactivex.Observable;

/**
 * Author: perqin
 * Date  : 5/24/17
 */

public interface IUsersRepository {
    Observable<String> requestNewUserId();

    String getUserId();

    Observable<Object> refreshLastActiveTime();
}
