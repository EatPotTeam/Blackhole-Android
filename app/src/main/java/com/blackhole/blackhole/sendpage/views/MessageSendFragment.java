package com.blackhole.blackhole.sendpage.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.common.RC;
import com.blackhole.blackhole.sendpage.contracts.MessageSendContract;

public class MessageSendFragment extends Fragment implements MessageSendContract.View {
    public static final String EXTRA_LINKED_TO = "LINKED_TO";

    private MessageSendContract.Presenter mPresenter;
    private EditText mEditorText;

    public MessageSendFragment() {
        // Required empty public constructor
    }

    public static MessageSendFragment newInstance() {
        return  new MessageSendFragment();
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
        return inflater.inflate(R.layout.fragment_message_send, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditorText = (EditText) view.findViewById(R.id.send_page_editor);
        mPresenter.viewCreated(getActivity().getIntent().getStringExtra(EXTRA_LINKED_TO));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.send_page_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.getActivity().finish();
            case R.id.send_page_send_button:
                String mes = mEditorText.getText().toString();
                mPresenter.sendMessage(mes);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setPresenter(MessageSendContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showErrorToast(String errorText) {
        Toast.makeText(this.getActivity(), errorText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successReturn() {
        Intent data = new Intent();
        data.putExtra(RC.MESSAGE_SEND_SUCCESS, RC.MESSAGE_SEND_SUCCESS);
        this.getActivity().setResult(Activity.RESULT_OK, data);
        this.getActivity().finish();
    }
}
