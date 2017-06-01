package com.blackhole.blackhole.data.repositories;

import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.data.retrofit.BlackholeService;
import com.blackhole.blackhole.framework.RxResult;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: perqin
 * Date  : 5/25/17
 */

class MessagesRepository implements IMessagesRepository {
    private static MessagesRepository sInstance;

    private final BlackholeService mService;

    static MessagesRepository getInstance() {
        if (sInstance == null) {
            sInstance = new MessagesRepository();
        }
        return sInstance;
    }

    private MessagesRepository() {
        mService = new Retrofit.Builder()
                .baseUrl(BlackholeService.API_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(BlackholeService.class);
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
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(aLong -> remoteFetchNewMessages(userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
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
