package com.blackhole.blackhole.sendpage.presenters;

import android.util.Log;

import com.blackhole.blackhole.common.RC;
import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.data.repositories.IMessagesRepository;
import com.blackhole.blackhole.data.repositories.IUsersRepository;
import com.blackhole.blackhole.sendpage.contracts.MessageSendContract;

import io.reactivex.disposables.Disposable;

/**
 * Created by YZQ on 2017/5/27.
 */

public class MessageSendPresenter implements MessageSendContract.Presenter {
    private static final String TAG = "MessageSendPresenter";

    private IUsersRepository mUserRepository;
    private IMessagesRepository mMessagesRepository;
    private MessageSendContract.View mView;

    public MessageSendPresenter(IUsersRepository ur, IMessagesRepository mr, MessageSendContract.View view) {
        mUserRepository = ur;
        mMessagesRepository = mr;
        mView = view;
    }

    @Override
    public void sendMessage(String message) {
        if (message.length() == 0) {
            mView.showErrorToast(RC.EDITOR_EMPTY_TEXT);
            return;
        }
        String nickname = mUserRepository.getNickname();

        Message mes = new Message();
        mes.setContent(message);
        mes.setNickname(nickname);
        Disposable disposable = mMessagesRepository.sendMessage(mes)
                .subscribe(messageRxResult -> {
                    if (messageRxResult.isError()) {
                        Log.w(TAG, "message send fail");
                        return;
                    }
                    mView.successReturn();
                }, throwable -> {
                    Log.w(TAG, "network fail", throwable);
                    mView.showErrorToast("Network problem");
                });
        //disposable.dispose();
    }
}
