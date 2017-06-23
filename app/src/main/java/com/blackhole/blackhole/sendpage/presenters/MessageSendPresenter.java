package com.blackhole.blackhole.sendpage.presenters;

import android.util.Log;

import com.blackhole.blackhole.common.CommonFactory;
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
    private Message mes;

    private String mLinkedTo;

    public MessageSendPresenter(IUsersRepository ur, IMessagesRepository mr, MessageSendContract.View view) {
        mUserRepository = ur;
        mMessagesRepository = mr;
        mView = view;
        mes = new Message();
        mes.setColor(0xFF000000);
    }

    @Override
    public void sendMessage(String message) {
        if (message.length() == 0) {
            mView.showErrorToast(CommonFactory.EDITOR_EMPTY_TEXT);
            return;
        }
        String nickname = mUserRepository.getNickname();

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

    @Override
    public void setColor(int color) {
        mes.setColor(color);
        mView.setEditorColor(color);
    }
}
