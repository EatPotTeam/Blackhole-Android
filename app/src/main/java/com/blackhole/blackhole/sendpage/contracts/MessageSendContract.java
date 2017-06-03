package com.blackhole.blackhole.sendpage.contracts;

import com.blackhole.blackhole.framework.BaseView;

/**
 * Created by YZQ on 2017/5/27.
 */

public interface MessageSendContract {
    interface Presenter {
        /**
         * Send message to server
         *
         * @param message message in editor
         */
        void sendMessage(String message);
    }

    interface View extends BaseView<Presenter> {
        void showErrorToast(String errorText);

        void successReturn();
    }
}
