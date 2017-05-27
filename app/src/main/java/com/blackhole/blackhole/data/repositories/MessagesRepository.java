package com.blackhole.blackhole.data.repositories;

import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.framework.RxResult;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    // NOTE: Should handle thread scheduling!!!
    @Override
    public Observable<ArrayList<Message>> fetchNewMessages(String userId) {
        ArrayList<Message> messages = new ArrayList<>();
        Random random = new Random();
        if (random.nextBoolean()) {
            messages.add(new Message());
            messages.add(new Message());
            messages.add(new Message());
            return Observable.just(messages);
        } else {
            return Observable.error(new Error("Failed to fetch new messages: Random Error"));
        }
    }

    @Override
    public Observable<RxResult<ArrayList<Message>>> startFetchingNewMessages(String userId) {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(aLong -> remoteFetchNewMessages(userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
    }

    /**
     * This is just a mock method for Retrofit Service
     * @param userId The user ID
     * @return Observable which emits three new messages or an error
     */
    private Observable<RxResult<ArrayList<Message>>> remoteFetchNewMessages(String userId) {
        ArrayList<Message> messages = new ArrayList<>();
        Random random = new Random();
        if (random.nextBoolean()) {
            messages.add(new Message());
            messages.add(new Message());
            messages.add(new Message());
            return Observable.just(RxResult.result(messages));
        } else {
            return Observable.just(RxResult.error(new Error("Failed to fetch new messages: Random Error")));
        }
    }
}
