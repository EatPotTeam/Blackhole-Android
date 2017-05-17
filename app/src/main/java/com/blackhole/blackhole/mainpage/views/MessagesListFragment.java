package com.blackhole.blackhole.mainpage.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.mainpage.contracts.MessagesListContract;

public class MessagesListFragment extends Fragment implements MessagesListContract.View {
    private MessagesListContract.Presenter mPresenter;

    public MessagesListFragment() {
        // Required empty public constructor
    }

    public static MessagesListFragment newInstance() {
        return new MessagesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages_list, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_messages_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(MessagesListContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
