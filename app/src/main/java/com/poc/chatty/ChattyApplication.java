package com.poc.chatty;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Jon on 1/14/2018.
 */

public class ChattyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("8812ae35cb75fe6d52f018ec0a8add5339b999e8")
                .clientKey("dc0190dd5f7ca905b849a6ab18d292c4602dec93")
                .server("http://ec2-18-220-7-92.us-east-2.compute.amazonaws.com:80/parse/")
                .build()
        );
    }
}
