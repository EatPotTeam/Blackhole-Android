package com.blackhole.blackhole.loginpage;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.data.repositories.Repo;
import com.blackhole.blackhole.loginpage.contracts.LoginContract;
import com.blackhole.blackhole.loginpage.presenters.LoginPresenter;
import com.blackhole.blackhole.loginpage.views.LoginFragment;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar)findViewById(R.id.register_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        FragmentManager fm = getSupportFragmentManager();
        LoginFragment registerFragment = (LoginFragment) fm.findFragmentById(R.id.frameLayout_login_fragmentContainer);
        if(registerFragment == null) {
            registerFragment = LoginFragment.newInstance();
            fm.beginTransaction().add(R.id.frameLayout_login_fragmentContainer, registerFragment).commit();
        }
        LoginContract.Presenter presenter = new LoginPresenter(Repo.getUsersRepo(this), Repo.getMessagesRepo(), registerFragment);
        registerFragment.setPresenter(presenter);
    }
}
