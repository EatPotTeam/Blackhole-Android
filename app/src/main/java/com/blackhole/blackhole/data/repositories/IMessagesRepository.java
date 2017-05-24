package com.blackhole.blackhole.data.repositories;

import com.blackhole.blackhole.data.entities.Message;

import io.reactivex.Observable;

/**
 * Author: perqin
 * Date  : 5/18/17
 */

public interface IMessagesRepository {
    Observable<Message> fetchNewMessages(String userId);
}
