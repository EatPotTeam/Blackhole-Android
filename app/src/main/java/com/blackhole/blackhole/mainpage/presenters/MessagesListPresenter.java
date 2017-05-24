package com.blackhole.blackhole.mainpage.presenters;

import com.blackhole.blackhole.data.repositories.IMessagesRepository;
import com.blackhole.blackhole.data.repositories.IUsersRepository;
import com.blackhole.blackhole.mainpage.contracts.MessagesListContract;

/**
 * Author: perqin
 * Date  : 5/17/17
 */

public class MessagesListPresenter implements MessagesListContract.Presenter {
    private final IUsersRepository mUserRepository;
    private final IMessagesRepository mMessageRepository;

    public MessagesListPresenter(IUsersRepository ur, IMessagesRepository mr) {
        mUserRepository = ur;
        mMessageRepository = mr;
    }
}
