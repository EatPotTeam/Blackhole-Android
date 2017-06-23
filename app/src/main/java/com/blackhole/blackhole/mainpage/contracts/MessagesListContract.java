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

        void destroy();

        void onMessageLongClick(Message message);

        void onLinkToFabClick();
    }

    interface View extends BaseView<Presenter> {
        void switchToNicknamePage();

        void showFailToBeOnlineError();

        void showFailToFetchMessagesError();

        void showLinkToFab();

        void hideLinkToFab();

        void showLinkToComposePage(String linkToId);

        void appendMessages(ArrayList<Message> messages);

        void setSessionId(String sessionId);
    }
}
