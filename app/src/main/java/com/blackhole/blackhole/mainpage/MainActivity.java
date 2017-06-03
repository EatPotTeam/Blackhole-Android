package com.blackhole.blackhole.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.data.repositories.Repo;
import com.blackhole.blackhole.mainpage.contracts.MessagesListContract;
import com.blackhole.blackhole.mainpage.presenters.MessagesListPresenter;
import com.blackhole.blackhole.mainpage.views.MessagesListFragment;
import com.blackhole.blackhole.sendpage.MessageSendActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageSendActivity.class);
                startActivity(intent);
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        MessagesListFragment messagesListFragment = (MessagesListFragment) fm.findFragmentById(R.id.frameLayout_fragmentContainer);
        if (messagesListFragment == null) {
            messagesListFragment = MessagesListFragment.newInstance();
            fm.beginTransaction().add(R.id.frameLayout_fragmentContainer, messagesListFragment).commit();
        }
        MessagesListContract.Presenter presenter = new MessagesListPresenter(Repo.getUsersRepo(this), Repo.getMessagesRepo(), messagesListFragment);
        messagesListFragment.setPresenter(presenter);
    }
}
