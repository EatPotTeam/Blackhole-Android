package com.blackhole.blackhole.data;

import com.blackhole.blackhole.framework.Irrelevant;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

/**
 * Author: perqin
 * Date  : 6/24/17
 */

public class TickObservable extends Observable<Object> {
    private Observer<? super Object> mObserver;
    private final Ticker ticker;

    public TickObservable(Consumer<Ticker> consumer) {
        ticker = new Ticker();
        try {
            consumer.accept(ticker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void subscribeActual(Observer<? super Object> observer) {
        mObserver = observer;
    }

    public class Ticker {
        public void tick() {
            if (mObserver != null) {
                mObserver.onNext(Irrelevant.INSTANCE);
            }
        }
    }
}
