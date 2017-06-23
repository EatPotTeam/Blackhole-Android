package com.blackhole.blackhole.loginpage.contracts;

import com.blackhole.blackhole.framework.BaseView;

/**
 * Created by YZQ on 2017/6/8.
 */

public interface LoginContract {
    interface Presenter {
        void viewCreated(boolean isChangingNickname);

        void loginOrChangeNickname(String nickname);
    }

    interface View extends BaseView<Presenter>{

        void showErrorToast(String s);

        void finishLogin();

        void finishChangingNickname();
    }
}
