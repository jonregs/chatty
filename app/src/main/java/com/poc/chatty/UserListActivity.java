package com.poc.chatty;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.poc.chatty.MainActivity.XSRF_TOKEN;

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
        
        Call<ResponseBody> call = apiService.getChatRoomList(XSRF_TOKEN);
        call.enqueue(new Callback<ResponseBody>() {
                         @Override
                         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                             Log.i(TAG, "onResponse: " + response);
                         }
    
                         @Override
                         public void onFailure(Call<ResponseBody> call, Throwable t) {
        
                         }
                     });
        
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
