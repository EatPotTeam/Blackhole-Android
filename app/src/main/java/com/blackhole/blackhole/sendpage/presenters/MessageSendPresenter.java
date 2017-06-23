package com.blackhole.blackhole.sendpage.presenters;

import android.util.Log;

import com.blackhole.blackhole.common.RC;
import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.data.repositories.IMessagesRepository;
import com.blackhole.blackhole.data.repositories.IUsersRepository;
import com.blackhole.blackhole.sendpage.contracts.MessageSendContract;

/**
 * Author: YZQ
 * Date  : 2017/5/27
 */

public class MessageSendPresenter implements MessageSendContract.Presenter {
    private static final String TAG = "MessageSendPresenter";

    private IUsersRepository mUserRepository;
    private IMessagesRepository mMessagesRepository;
    private MessageSendContract.View mView;

    private String mLinkedTo;

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
        mes.setSessionId(mUserRepository.getSessionId());
        mes.setReply(mLinkedTo);
        mMessagesRepository.sendMessage(mes)
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
    }

    @Override
    public void viewCreated(String linkedTo) {
        mLinkedTo = linkedTo == null ? "" : linkedTo;
    }
}
