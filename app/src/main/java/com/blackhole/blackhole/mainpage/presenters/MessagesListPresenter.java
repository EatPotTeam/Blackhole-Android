package com.blackhole.blackhole.mainpage.presenters;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.data.repositories.IMessagesRepository;
import com.blackhole.blackhole.data.repositories.IUsersRepository;
import com.blackhole.blackhole.mainpage.contracts.MessagesListContract;

import java.util.Iterator;
import java.util.LinkedList;

import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Author: perqin
 * Date  : 5/17/17
 */

public class MessagesListPresenter implements MessagesListContract.Presenter {
    private static final String TAG = "MessagesListPresenter";
    private static final long LINK_TO_FAB_SHOW_TIMEOUT = 3000;

    private final IUsersRepository mUserRepository;
    private final IMessagesRepository mMessageRepository;
    private final MessagesListContract.View mView;

    private Runnable mLinkToFabAutoHideRunnable = new Runnable() {
        @Override
        public void run() {
            mView.hideLinkToFab();
            mRunnableRemoved = true;
        }
    };
    private boolean mRunnableRemoved = true;

    /**
     * Add Disposable (returned from Observable.subscribe) to this list and they will be disposed
     * when this presenter is destroyed.
     */
    private LinkedList<Disposable> mOnDestroyDisposables = new LinkedList<>();
    private Handler mUiHandler;
    private Message mLinkToMessage;

    public MessagesListPresenter(IUsersRepository usersRepository, IMessagesRepository messagesRepository, MessagesListContract.View view) {
        mUserRepository = usersRepository;
        mMessageRepository = messagesRepository;
        mView = view;
        mUiHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void viewCreated() {
        if (("").equals(mUserRepository.getUserId())) {
            mView.switchToNicknamePage();
        } else {
            mUserRepository.renewSessionId();
            mView.setSessionId(mUserRepository.getSessionId());

            Disposable disposable = mUserRepository.refreshLastActiveTime()
                    .flatMap(o -> mMessageRepository.startFetchingNewMessages(mUserRepository.getUserId()))
                    .subscribe(arrayListRxResult -> {
                        if (arrayListRxResult.isError()) {
                            Log.w(TAG, "viewCreated: Failed to fetch some item_message");
                            mView.showFailToFetchMessagesError();
                            return;
                        }
                        mView.appendMessages(arrayListRxResult.result());
                    }, throwable -> {
                        Log.w(TAG, "viewCreated: Failed to fetch", throwable);
                        Log.d(TAG, "viewCreated: User id = " + mUserRepository.getUserId());
                        mView.showFailToBeOnlineError();
                        if (throwable instanceof HttpException) {
                            if (((HttpException) throwable).code() == 401) {
                                mView.switchToNicknamePage();
                            }
                        }
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

    @Override
    public void onMessageLongClick(Message message) {
        mView.showLinkToFab();
        mLinkToMessage = message;
        if (!mRunnableRemoved) {
            mUiHandler.removeCallbacks(mLinkToFabAutoHideRunnable);
            mRunnableRemoved = true;
        }
        mUiHandler.postDelayed(mLinkToFabAutoHideRunnable, LINK_TO_FAB_SHOW_TIMEOUT);
        mRunnableRemoved = false;
    }

    @Override
    public void onLinkToFabClick() {
        mView.showLinkToComposePage(mLinkToMessage.getSessionId());
    }
}
