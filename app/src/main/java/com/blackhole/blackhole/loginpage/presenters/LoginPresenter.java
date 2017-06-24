package com.blackhole.blackhole.loginpage.presenters;

import android.util.Log;

import com.blackhole.blackhole.BuildConfig;
import com.blackhole.blackhole.data.repositories.IMessagesRepository;
import com.blackhole.blackhole.data.repositories.IUsersRepository;
import com.blackhole.blackhole.loginpage.contracts.LoginContract;

/**
 * Author: YZQ
 * Date  : 2017/6/8
 */

public class LoginPresenter implements LoginContract.Presenter{
    private static final String TAG = "LoginPresenter";

    private IUsersRepository mUserRepository;
    private IMessagesRepository mMessagesRepository;
    private LoginContract.View mView;
    private boolean mIsChangingNickname;

    public LoginPresenter(IUsersRepository ur, IMessagesRepository mr, LoginContract.View view) {
        mUserRepository = ur;
        mMessagesRepository = mr;
        mView = view;
    }

    @Override
    public void viewCreated(boolean isChangingNickname, boolean firstLaunch, boolean alwaysShowIntro) {
        mIsChangingNickname = isChangingNickname;

        final boolean showIntro;
        if (BuildConfig.DEBUG) {
            showIntro = alwaysShowIntro || firstLaunch;
        } else {
            showIntro = firstLaunch;
        }
        if (showIntro) {
            mView.showIntroPage();
        }
    }

    @Override
    public void loginOrChangeNickname(String nickname) {
        if (nickname.isEmpty()) {
            mView.showErrorToast("Empty nickname");
            return;
        }
        mUserRepository.setNickname(nickname);
        if (mIsChangingNickname) {
            mView.finishChangingNickname();
        } else {
            mUserRepository.requestNewUserId()
                    .subscribe(userRxResult -> {
                        if (userRxResult.isError()) {
                            Log.w(TAG, userRxResult.toString());
                            mView.showErrorToast("Set nickname fail, try again");
                        } else {
                            mView.finishLogin();
                        }
                    }, throwable -> Log.w(TAG, "Network fail", throwable));
        }
    }
}
