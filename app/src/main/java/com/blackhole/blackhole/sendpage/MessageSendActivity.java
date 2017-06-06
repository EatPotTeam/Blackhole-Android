package com.blackhole.blackhole.sendpage;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.data.repositories.Repo;
import com.blackhole.blackhole.sendpage.contracts.MessageSendContract;
import com.blackhole.blackhole.sendpage.presenters.MessageSendPresenter;
import com.blackhole.blackhole.sendpage.views.MessageSendFragment;

public class MessageSendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);

        Toolbar toolbar = (Toolbar) findViewById(R.id.send_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        MessageSendFragment messageSendFragment = (MessageSendFragment) fm.findFragmentById(R.id.frameLayout_send_page_fragmentContainer);
        if(messageSendFragment == null) {
            messageSendFragment = MessageSendFragment.newInstance();
            fm.beginTransaction().add(R.id.frameLayout_send_page_fragmentContainer, messageSendFragment).commit();
        }
        MessageSendContract.Presenter presenter = new MessageSendPresenter(Repo.getUsersRepo(this), Repo.getMessagesRepo(), messageSendFragment);
        messageSendFragment.setPresenter(presenter);
    }
}
