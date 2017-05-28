package com.blackhole.blackhole.mainpage.presenters;

import android.util.Log;

import com.blackhole.blackhole.data.repositories.IMessagesRepository;
import com.blackhole.blackhole.data.repositories.IUsersRepository;
import com.blackhole.blackhole.mainpage.contracts.MessagesListContract;

import java.util.Iterator;
import java.util.LinkedList;

import io.reactivex.disposables.Disposable;

/**
 * Author: perqin
 * Date  : 5/17/17
 */

public class MessagesListPresenter implements MessagesListContract.Presenter {
    private static final String TAG = "MessagesListPresenter";

    private final IUsersRepository mUserRepository;
    private final IMessagesRepository mMessageRepository;
    private final MessagesListContract.View mView;

    /**
     * Add Disposable (returned from Observable.subscribe) to this list and they will be disposed
     * when this presenter is destroyed.
     */
    private LinkedList<Disposable> mOnDestroyDisposables = new LinkedList<>();

    public MessagesListPresenter(IUsersRepository ur, IMessagesRepository mr, MessagesListContract.View v) {
        mUserRepository = ur;
        mMessageRepository = mr;
        mView = v;
    }

    @Override
    public void viewCreated() {
        if (mUserRepository.getUserId() == null) {
            mView.switchToNicknamePage();
        } else {
            Disposable disposable = mUserRepository.refreshLastActiveTime()
                    .flatMap(o -> mMessageRepository.startFetchingNewMessages(mUserRepository.getUserId()))
                    .subscribe(arrayListRxResult -> {
                        if (arrayListRxResult.isError()) {
                            Log.w(TAG, "viewCreated: Failed to fetch some message");
                            mView.showFailToFetchMessagesError();
                            return;
                        }
                        mView.appendMessages(arrayListRxResult.result());
                    }, throwable -> {
                        Log.w(TAG, "viewCreated: Failed to fetch", throwable);
                        mView.showFailToBeOnlineError();
                    });
            mOnDestroyDisposables.add(disposable);
        }
    }

    @Override
    public void destroy() {
        Iterator<Disposable> iterator = mOnDestroyDisposables.iterator();
        while (iterator.hasNext()) {
            iterator.next().dispose();
            iterator.remove();
        }
    }
}
