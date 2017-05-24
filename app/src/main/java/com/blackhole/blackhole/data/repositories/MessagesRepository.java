package com.blackhole.blackhole.data.repositories;

import com.blackhole.blackhole.data.entities.Message;

import io.reactivex.Observable;

/**
 * Author: perqin
 * Date  : 5/25/17
 */

class MessagesRepository implements IMessagesRepository {
    private static MessagesRepository sInstance;

    static MessagesRepository getInstance() {
        if (sInstance == null) {
            sInstance = new MessagesRepository();
        }
        return sInstance;
    }

    private MessagesRepository() {}

    @Override
    public Observable<Message> fetchNewMessages(String userId) {
        return null;
    }
}
