package com.poc.chatty;

import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.poc.chatty.models.Result;
import com.poc.chatty.models.UserListResponse;
import com.poc.chatty.rest.ApiClient;
import com.poc.chatty.rest.ApiInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jon on 1/14/2018.
 */

public class UserListActivity extends AppCompatActivity {

   List<Result> usersToMessage;
   ListView userListView;

   ArrayAdapter arrayAdapter;
   
   public static String TAG = "chatty";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_listview);
        
        setTitle("User List");
    
        userListView = findViewById(R.id.userListView);
        
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    
        Call<UserListResponse> call = apiService.getUserList();
        call.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                Log.e(TAG, "onResponse: " + response.body().getResult() );
                List<Result> userListResponses = response.body().getResult();
                updateAdapter(userListResponses);
            }
    
            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });
        
        userListView.setAdapter(arrayAdapter);
        
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UserListActivity.this, ChatActivity.class);
                intent.putExtra("username", usersToMessage.get(i));
                startActivity(intent);
            }
        });
        
    }
    
    private void updateAdapter(List<Result> userListView) {
        
        usersToMessage = userListView;
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, usersToMessage);
        
    }
}
