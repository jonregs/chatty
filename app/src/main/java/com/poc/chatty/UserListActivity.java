package com.poc.chatty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Jon on 1/14/2018.
 */

public class UserListActivity extends AppCompatActivity {

   ArrayList<String> userToMessage = new ArrayList<>();

   ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_listview);

        ListView userListView = findViewById(R.id.userListView);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UserListActivity.this, ChatActivity.class);
                intent.putExtra("username", userToMessage.get(i));
                startActivity(intent);
            }
        });

        userToMessage.add("Peter");
        userToMessage.add("Craig");
        userToMessage.add("Shawn");
        userToMessage.add("Murphy");
        userToMessage.add("Pedro");
        userToMessage.add("Sam");
        userToMessage.add("Jonathan");
        userToMessage.add("David");
        userToMessage.add("James");
        userToMessage.add("John");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userToMessage);
        userListView.setAdapter(arrayAdapter);
    }
}
