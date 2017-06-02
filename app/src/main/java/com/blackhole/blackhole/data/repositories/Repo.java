package com.blackhole.blackhole.data.repositories;

import android.content.Context;

/**
 * Author: perqin
 * Date  : 5/25/17
 */

public class Repo {
    public static IUsersRepository getUsersRepo(Context context) {
        return UsersRepository.getInstance(context);
    }

    public static IMessagesRepository getMessagesRepo() {
        return MessagesRepository.getInstance();
    }
}
