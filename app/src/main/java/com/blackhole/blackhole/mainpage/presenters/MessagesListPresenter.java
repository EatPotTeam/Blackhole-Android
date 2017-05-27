package com.blackhole.blackhole.mainpage.presenters;

import android.util.Log;

import com.blackhole.blackhole.data.repositories.IMessagesRepository;
import com.blackhole.blackhole.data.repositories.IUsersRepository;
import com.blackhole.blackhole.mainpage.contracts.MessagesListContract;

/**
 * Author: perqin
 * Date  : 5/17/17
 */

public class MessagesListPresenter implements MessagesListContract.Presenter {
    private static final String TAG = "MessagesListPresenter";

    private final IUsersRepository mUserRepository;
    private final IMessagesRepository mMessageRepository;
    private final MessagesListContract.View mView;

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
            mUserRepository.refreshLastActiveTime()
                    .flatMap(o -> mMessageRepository.startFetchingNewMessages(mUserRepository.getUserId()))
                    .subscribe(arrayListRxResult -> {
                        if (arrayListRxResult.isError()) {
                            Log.w(TAG, "viewCreated: Failed to fetch some message", arrayListRxResult.error());
                            mView.showFailToFetchMessagesError();
                            return;
                        }
                        mView.appendMessages(arrayListRxResult.result());
                    }, throwable -> {
                        Log.w(TAG, "viewCreated: Failed to fetch", throwable);
                        mView.showFailToBeOnlineError();
                    });
        }
    }
}
