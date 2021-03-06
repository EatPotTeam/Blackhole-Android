package com.blackhole.blackhole.data.repositories;

import com.blackhole.blackhole.data.entities.Message;
import com.blackhole.blackhole.framework.RxResult;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Author: perqin
 * Date  : 5/18/17
 */

public interface IMessagesRepository {
    @Deprecated
    Observable<ArrayList<Message>> fetchNewMessages(String userId);

    Observable<RxResult<ArrayList<Message>>> startFetchingNewMessages(String userId);

    Observable<RxResult<Message>> sendMessage(Message message);
}
