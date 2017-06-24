package com.blackhole.blackhole.sendpage.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.blackhole.blackhole.R;
import com.blackhole.blackhole.common.CommonFactory;
import com.blackhole.blackhole.sendpage.contracts.MessageSendContract;

public class MessageSendFragment extends Fragment implements MessageSendContract.View, View.OnClickListener {
    public static final String EXTRA_LINKED_TO = "LINKED_TO";
    private MessageSendContract.Presenter mPresenter;
    private EditText mEditorText;
    private GridLayout mGridLayout;
    private AlertDialog mAlertDialog;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditorText = (EditText) view.findViewById(R.id.send_page_editor);
        mPresenter.viewCreated(getActivity().getIntent().getStringExtra(EXTRA_LINKED_TO));
        mGridLayout = (GridLayout) view.findViewById(R.id.message_palette);

        Point t = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(t);

        int buttonWidth = 40, buttonHeight = 40;
        int buttonWidthInPixel = CommonFactory.dip2px(getActivity(), buttonWidth);
        int buttonHeightInPixel = CommonFactory.dip2px(getActivity(), buttonHeight);
        int buttonMarginWidth = (CommonFactory.px2dip(getActivity(), t.x) - buttonWidth * 6) / 12;
        int buttonMarginHeight = 8;
        int buttonMarginWidthInPixel = CommonFactory.dip2px(getActivity(), buttonMarginWidth);
        int buttonMarginHeightInPixel = CommonFactory.dip2px(getActivity(), buttonMarginHeight);

        int i;
        for (i = 0; i < CommonFactory.ColorPalette.length; i++) {
            Button button = new Button(getActivity());
            button.setMinimumWidth(0);
            button.setMinimumHeight(0);
            button.setWidth(buttonWidthInPixel);
            button.setHeight(buttonHeightInPixel);
            button.setBackgroundResource(R.drawable.message_palette_ripple);
            RippleDrawable bgShape = (RippleDrawable)button.getBackground();
            bgShape.setTint(CommonFactory.ColorPalette[i]);

            button.setId(i);
            button.setOnClickListener(this);

            GridLayout.Spec rowSpec = GridLayout.spec(i / 6);
            GridLayout.Spec columnSpec = GridLayout.spec(i%6);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.setMargins(buttonMarginWidthInPixel, buttonMarginHeightInPixel, buttonMarginWidthInPixel, buttonMarginHeightInPixel);
            mGridLayout.addView(button, params);
        }
    }

    @Override
    public void onClick(View v) {
        mPresenter.setColor(CommonFactory.ColorPalette[v.getId()]);
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
                return true;
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
        data.putExtra(CommonFactory.MESSAGE_SEND_SUCCESS, CommonFactory.MESSAGE_SEND_SUCCESS);
        this.getActivity().setResult(Activity.RESULT_OK, data);
        this.getActivity().finish();
    }

    @Override
    public void setEditorColor(int color) {
        mEditorText.setTextColor(color);
    }

    @Override
    public void startLoading() {
        mAlertDialog = new ProgressDialog.Builder(getActivity()).setCancelable(false).setMessage(R.string.sending_message).create();
        mAlertDialog.show();
    }

    @Override
    public void stopLoading() {
        if (mAlertDialog != null) {
            mAlertDialog.cancel();
        }
    }
}
