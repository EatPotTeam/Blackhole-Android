package com.blackhole.blackhole.sendpage.presenters;

import android.util.Log;

import com.blackhole.blackhole.common.CommonFactory;
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
    private Message mes;

    public MessageSendPresenter(IUsersRepository ur, IMessagesRepository mr, MessageSendContract.View view) {
        mUserRepository = ur;
        mMessagesRepository = mr;
        mView = view;
        mes = new Message();
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

    @Override
    public void setColor(int color) {
        mes.setColor(color);
        mView.setEditorColor(color);
    }
}
