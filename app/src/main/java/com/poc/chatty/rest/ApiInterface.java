package com.poc.chatty.rest;

import com.poc.chatty.models.LoginModel;
import com.poc.chatty.models.UserListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jonregalado on 1/16/18.
 */

public interface ApiInterface {
    
    @GET("userlist")
    Call<UserListResponse> getUserList();

    @POST("/login")
    Call<LoginModel> userLogin(@Body LoginModel loginModel);
    
//    @GET("/Nuclei/Messaging/Singleton/listAll")

}
