package com.blackhole.blackhole.data.repositories;

/**
 * Author: perqin
 * Date  : 5/25/17
 */

public class Repo {
    public static IUsersRepository getUsersRepo() {
        return UsersRepository.getInstance();
    }

    public static IMessagesRepository getMessagesRepo() {
        return MessagesRepository.getInstance();
    }
}
