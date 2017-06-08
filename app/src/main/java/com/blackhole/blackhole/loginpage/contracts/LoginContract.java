package com.blackhole.blackhole.loginpage.contracts;

import com.blackhole.blackhole.framework.BaseView;

/**
 * Created by YZQ on 2017/6/8.
 */

public interface LoginContract {
    interface Presenter {

        void login(String nickname);
    }

    interface View extends BaseView<Presenter>{

    }
}
