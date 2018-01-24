package com.poc.chatty;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jon on 1/14/2018.
 */

public class ChatActivity extends AppCompatActivity {
    
    String activeUser = "";
    
    private static final String SENDER_KEY = "sender";
    private static final String RECIPIENT_KEY = "recipient";
    private static final String MESSAGE_KEY = "message";
    private static final String MESSAGE_CLASSNAME_KEY = "Message";
    
    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    
    public void sendChat(View view) {
        
        final EditText chatEditText = findViewById(R.id.chatEditText);
        
        ParseObject message = new ParseObject(MESSAGE_CLASSNAME_KEY);
        
        message.put(SENDER_KEY, ParseUser.getCurrentUser()
                .getUsername());
        message.put(RECIPIENT_KEY, activeUser);
        message.put(MESSAGE_KEY, chatEditText.getText()
                .toString());
        
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    
                    messages.add(chatEditText.getText()
                            .toString());
                    
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");
        
        setTitle("Chat with " + activeUser);
        
        ListView chatListView = findViewById(R.id.chatListView);
        
        chatListView.setAdapter(arrayAdapter);
        
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, messages);
        
        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>(MESSAGE_CLASSNAME_KEY);
        
        query1.whereEqualTo(SENDER_KEY, ParseUser.getCurrentUser()
                .getUsername());
        query1.whereEqualTo(RECIPIENT_KEY, activeUser);
        
        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>(MESSAGE_CLASSNAME_KEY);
        
        query2.whereEqualTo(RECIPIENT_KEY, ParseUser.getCurrentUser()
                .getUsername());
        query2.whereEqualTo(SENDER_KEY, activeUser);
        
        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        
        queries.add(query1);
        queries.add(query2);
        
        ParseQuery<ParseObject> query = ParseQuery.or(queries);

//        query.orderByDescending("createdAt");
        
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                
                if (e == null) {
                    if (objects.size() > 0) {
                        
//                        messages.clear();
                        
                        for (ParseObject message : objects) {
                            String messageContent = message.getString(MESSAGE_KEY);
                            
                            if (!message.getString(SENDER_KEY)
                                    .equals(ParseUser.getCurrentUser()
                                            .getUsername())) {
                                
                                messageContent = "> " + messageContent;
                            }
                            
                            messages.add(messageContent);
                        }
                        
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
