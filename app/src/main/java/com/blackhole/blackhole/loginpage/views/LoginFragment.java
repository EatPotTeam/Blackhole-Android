package com.blackhole.blackhole.loginpage.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.loginpage.contracts.LoginContract;

public class LoginFragment extends Fragment  implements LoginContract.View, View.OnClickListener {
    private LoginContract.Presenter mPresenter;
    private EditText nickname;
    private Button mButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nickname = (EditText)view.findViewById(R.id.login_nickname);
        mButton = (Button)view.findViewById(R.id.login_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.login_button:
                mPresenter.login(nickname.getText().toString());
                break;
            default:
                break;
        }
    }
}
