package com.blackhole.blackhole.data.entities;

/**
 * Author: perqin
 * Date  : 5/18/17
 */

public class Message {
    private String nickname;
    private String content;
    private long createdTime;
    private int color;

    public Message() {}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
