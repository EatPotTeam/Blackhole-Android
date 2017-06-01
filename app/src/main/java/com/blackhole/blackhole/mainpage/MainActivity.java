package com.blackhole.blackhole.mainpage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.data.repositories.Repo;
import com.blackhole.blackhole.mainpage.contracts.MessagesListContract;
import com.blackhole.blackhole.mainpage.presenters.MessagesListPresenter;
import com.blackhole.blackhole.mainpage.views.MessagesListFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());*/
        fab.setOnClickListener(view -> Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show());

        FragmentManager fm = getSupportFragmentManager();
        MessagesListFragment messagesListFragment = (MessagesListFragment) fm.findFragmentById(R.id.frameLayout_fragmentContainer);
        if (messagesListFragment == null) {
            messagesListFragment = MessagesListFragment.newInstance();
            fm.beginTransaction().add(R.id.frameLayout_fragmentContainer, messagesListFragment).commit();
        }
        MessagesListContract.Presenter presenter = new MessagesListPresenter(Repo.getUsersRepo(), Repo.getMessagesRepo(), messagesListFragment);
        messagesListFragment.setPresenter(presenter);
    }
}
