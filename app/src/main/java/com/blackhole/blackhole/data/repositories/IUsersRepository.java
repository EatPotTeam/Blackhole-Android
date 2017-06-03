package com.blackhole.blackhole.data.repositories;

import com.blackhole.blackhole.data.entities.User;
import com.blackhole.blackhole.framework.RxResult;

import io.reactivex.Observable;

/**
 * Author: perqin
 * Date  : 5/24/17
 */

public interface IUsersRepository {
    Observable<RxResult<User>> requestNewUserId();

    String getUserId();

    Observable<Object> refreshLastActiveTime();

    String setUserNickname();

    String getUserNickname();
}
