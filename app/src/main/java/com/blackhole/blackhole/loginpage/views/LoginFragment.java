package com.blackhole.blackhole.loginpage.views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blackhole.blackhole.BuildConfig;
import com.blackhole.blackhole.R;
import com.blackhole.blackhole.intropage.IntroActivity;
import com.blackhole.blackhole.loginpage.contracts.LoginContract;
import com.blackhole.blackhole.mainpage.MainActivity;

public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener {
    public static final String EXTRA_IS_CHANGING_NICKNAME = "IS_CHANGING_NICKNAME";

    private LoginContract.Presenter mPresenter;
    private TextInputEditText nickname;
    private FloatingActionButton mOkFab;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean isChangingNickname = getActivity().getIntent().getBooleanExtra(EXTRA_IS_CHANGING_NICKNAME, false);

        TextView titleTextView = (TextView) getActivity().findViewById(R.id.textView_title);
        titleTextView.setText(isChangingNickname ? R.string.change_nickname : R.string.set_nickname);
        mOkFab = (FloatingActionButton) getActivity().findViewById(R.id.fab_ok);
        mOkFab.setOnClickListener(this);
        nickname = (TextInputEditText) view.findViewById(R.id.login_nickname);

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final boolean firstLaunch = sp.getBoolean(getString(R.string.pk_first_launch), true);
        sp.edit().putBoolean(getString(R.string.pk_first_launch), false).apply();
        final boolean alwaysShowIntro = sp.getBoolean(getString(R.string.pk_always_show_intro_page), BuildConfig.DEBUG);
        mPresenter.viewCreated(isChangingNickname, firstLaunch, alwaysShowIntro);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fab_ok:
                mPresenter.loginOrChangeNickname(nickname.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void setNicknameEditText(String nickname) {
        this.nickname.setText(nickname);
    }

    @Override
    public void showErrorToast(String s) {
        Toast.makeText(this.getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishLogin() {
        getActivity().startActivity(new Intent(this.getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void finishChangingNickname() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showIntroPage() {
        getActivity().startActivity(new Intent(getActivity(), IntroActivity.class));
    }
}
