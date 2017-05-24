package com.blackhole.blackhole;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Author: perqin
 * Date  : 5/18/17
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
