package com.blackhole.blackhole.mainpage.views;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.loginpage.LoginActivity;
import com.blackhole.blackhole.loginpage.views.LoginFragment;
import com.blackhole.blackhole.mainpage.contracts.MessagesListContract;
import com.blackhole.blackhole.mainpage.ui.MessagesListRecyclerAdapter;
import com.blackhole.blackhole.sendpage.MessageSendActivity;
import com.blackhole.blackhole.sendpage.views.MessageSendFragment;
import com.blackhole.blackhole.settingspage.SettingsActivity;
import com.blackhole.blackhole.util.CustomDivider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesListFragment extends Fragment implements MessagesListContract.View {
    private MessagesListRecyclerAdapter mAdapter;
    private MessagesListContract.Presenter mPresenter;
    @BindView(R.id.recyclerView_messagesList)
    RecyclerView mRecyclerView;

    FloatingActionButton mComposeFab;
    FloatingActionButton mLinkToFab;

    public MessagesListFragment() {
        // Required empty public constructor
    }

    public static MessagesListFragment newInstance() {
        return new MessagesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new MessagesListRecyclerAdapter();
        mAdapter.setListener(message -> mPresenter.onMessageLongClick(message));

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
        mComposeFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mLinkToFab = (FloatingActionButton) getActivity().findViewById(R.id.fab_linkTo);

        mComposeFab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MessageSendActivity.class);
            startActivity(intent);
        });

        mLinkToFab.setOnClickListener(v -> mPresenter.onLinkToFabClick());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider);
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        mRecyclerView.addItemDecoration(new CustomDivider(drawable, size.x));

        mRecyclerView.setAdapter(mAdapter);

        mPresenter.viewCreated();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.destroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_messages_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_change_nickname:
                Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                intent.putExtra(LoginFragment.EXTRA_IS_CHANGING_NICKNAME, true);
                this.getActivity().startActivityForResult(intent, 0);
                return true;
            case R.id.action_settings:
                getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setPresenter(MessagesListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void switchToNicknamePage() {
        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void showFailToBeOnlineError() {
        Toast.makeText(getActivity(), R.string.fail_to_connect_to_server, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailToFetchMessagesError() {
        Toast.makeText(getActivity(), R.string.fail_to_get_messages_maybe_network_unavailable, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLinkToFab() {
        mLinkToFab.show();
    }

    @Override
    public void hideLinkToFab() {
        mLinkToFab.hide();
    }

    @Override
    public void showLinkToComposePage(String linkToId) {
        Intent intent = new Intent(getActivity(), MessageSendActivity.class);
        intent.putExtra(MessageSendFragment.EXTRA_LINKED_TO, linkToId);
        startActivity(intent);
    }

    @Override
    public void appendMessages(ArrayList<Message> messages) {
        mAdapter.appendMessages(messages);
    }

    @Override
    public void setSessionId(String sessionId) {
        mAdapter.setSessionId(sessionId);
    }
}
