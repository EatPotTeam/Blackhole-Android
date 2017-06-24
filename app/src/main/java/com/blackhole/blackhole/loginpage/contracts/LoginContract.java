package com.blackhole.blackhole.loginpage.contracts;

import com.blackhole.blackhole.framework.BaseView;

/**
 * Author: YZQ
 * Date  : 2017/6/8
 */

public interface LoginContract {
    interface Presenter {
        void viewCreated(boolean isChangingNickname, boolean firstLaunch, boolean alwaysShowIntro);

        void loginOrChangeNickname(String nickname);
    }

    interface View extends BaseView<Presenter>{
        void setNicknameEditText(String nickname);

        void showErrorToast(String s);

        void finishLogin();

        void finishChangingNickname();

        void showIntroPage();

        void startLoading();

        void stopLoading();
    }
}
