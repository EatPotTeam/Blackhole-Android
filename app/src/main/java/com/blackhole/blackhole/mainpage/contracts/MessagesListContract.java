package com.blackhole.blackhole.mainpage.contracts;

import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.framework.BaseView;

import java.util.ArrayList;

/**
 * Author: perqin
 * Date  : 5/17/17
 */

public interface MessagesListContract {
    interface Presenter {
        void viewCreated();
    }

    interface View extends BaseView<Presenter> {
        void switchToNicknamePage();

        void showFailToBeOnlineError();

        void showFailToFetchMessagesError();

        void appendMessages(ArrayList<Message> messages);
    }
}
