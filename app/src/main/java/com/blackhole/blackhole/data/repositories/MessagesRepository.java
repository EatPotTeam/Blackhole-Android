package com.blackhole.blackhole.data.repositories;

import com.blackhole.blackhole.BuildConfig;
import com.blackhole.blackhole.data.TickObservable;
import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.data.retrofit.ApiFactory;
import com.blackhole.blackhole.data.retrofit.BlackholeService;
import com.blackhole.blackhole.framework.Irrelevant;
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

    private final BlackholeService mService;
    private TickObservable.Ticker mTicker;

    static MessagesRepository getInstance() {
        if (sInstance == null) {
            sInstance = new MessagesRepository();
        }
        return sInstance;
    }

    private MessagesRepository() {
        mService = ApiFactory.blackhole();
    }

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
        // If in debug variant, poll every 10 seconds.
        return Observable.merge(
                Observable.interval(0, BuildConfig.DEBUG ? 10 : 30, TimeUnit.SECONDS).map(aLong -> Irrelevant.INSTANCE),
                new TickObservable(ticker -> mTicker = ticker)
        )
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(irrelevant -> remoteFetchNewMessages(userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
    }

    @Override
    public Observable<RxResult<Message>> sendMessage(Message message) {
        return mService.sendMessage(message)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(RxResult::result)
                // Tick immediately after new message sent
                .doOnNext(messageRxResult -> mTicker.tick())
                .onErrorReturn(RxResult::error);
    }

    /**
     * This is a wrapper method for Retrofit Service
     * @param userId The user ID
     * @return Observable which emits new messages or an error
     */
    private Observable<RxResult<ArrayList<Message>>> remoteFetchNewMessages(String userId) {
        return mService.getLatestMessage(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(RxResult::result)
                .onErrorReturn(RxResult::error);
    }
}
