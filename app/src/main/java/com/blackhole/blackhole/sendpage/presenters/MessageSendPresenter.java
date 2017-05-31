package com.blackhole.blackhole.sendpage.presenters;

import android.util.Log;

import com.blackhole.blackhole.common.RC;
import com.blackhole.blackhole.data.repositories.IUsersRepository;
import com.blackhole.blackhole.sendpage.contracts.MessageSendContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by YZQ on 2017/5/27.
 */

public class MessageSendPresenter implements MessageSendContract.Presenter {
    private IUsersRepository mUserRepository;
    private MessageSendContract.View mView;


    public MessageSendPresenter(IUsersRepository ur, MessageSendContract.View view) {
        mUserRepository = ur;
        mView = view;
    }

    @Override
    public void sendMessage(String message) {
        if (message.length() == 0) mView.showErrorToast(RC.EDITOR_EMPTY_TEXT);
        String nickname = mUserRepository.getUserNickname();
        String userId = mUserRepository.getUserId();

        JSONObject mes = new JSONObject();
        try {
            mes.put("id", userId);
            mes.put("nickname", nickname);
            mes.put("message", mes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String requestContent = mes.toString();
        String response = doRequest(requestContent);
        if (response == RC.MESSAGE_SEND_FAIL) {
            mView.showErrorToast(RC.MESSAGE_SEND_FAIL);
        } else {
            mView.successReturn();
        }
    }

    private String doRequest(String requestContent) {
        HttpURLConnection conn = null;
        URL url = null;
        try {
            url = new URL("localhost:8080/submit");
            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            //conn.connect()

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            byte[] request = requestContent.getBytes("UTF8");
            os.write(request, 0, request.length);
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                Log.i("Response Code: ", String.valueOf(responseCode));
                return RC.MESSAGE_SEND_FAIL/*String.valueOf(responseCode)*/;
            } else {
                InputStream is = conn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();
            }
        } catch (IOException e) {
            Log.e("Error: ", e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
