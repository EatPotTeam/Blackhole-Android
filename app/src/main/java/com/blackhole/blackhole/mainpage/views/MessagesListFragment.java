package com.blackhole.blackhole.mainpage.views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.mainpage.contracts.MessagesListContract;
import com.blackhole.blackhole.mainpage.ui.MessagesListRecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesListFragment extends Fragment implements MessagesListContract.View {
    private MessagesListRecyclerAdapter mMessagesListRecyclerAdapter;
    private MessagesListContract.Presenter mPresenter;

    @BindView(R.id.recyclerView_messagesList)
    RecyclerView mMessagesListRecyclerView;

    public MessagesListFragment() {
        // Required empty public constructor
    }

    public static MessagesListFragment newInstance() {
        return new MessagesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMessagesListRecyclerAdapter = new MessagesListRecyclerAdapter();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        mMessagesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mMessagesListRecyclerView.setAdapter(mMessagesListRecyclerAdapter);

        mPresenter.viewCreated();
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

    @Override
    public void switchToNicknamePage() {
        getActivity().finish();
    }

    @Override
    public void showFailToBeOnlineError() {
    }

    @Override
    public void showFailToFetchMessagesError() {
    }

    @Override
    public void appendMessages(ArrayList<Message> messages) {
        mMessagesListRecyclerAdapter.appendMessages(messages);
    }
}
