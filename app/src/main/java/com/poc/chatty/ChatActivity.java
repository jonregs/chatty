package com.poc.chatty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Jon on 1/14/2018.
 */

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();

        String activeUser = intent.getStringExtra("username");

        setTitle("Chat with " + activeUser);

        Toast.makeText(this, activeUser , Toast.LENGTH_SHORT).show();

    }
}
