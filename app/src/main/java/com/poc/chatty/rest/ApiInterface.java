package com.poc.chatty.rest;

import com.poc.chatty.MainActivity;
import com.poc.chatty.models.LoginModel;
import com.poc.chatty.models.UserListResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.poc.chatty.MainActivity.XSRF_TOKEN;

/**
 * Created by jonregalado on 1/16/18.
 */

public interface ApiInterface {
    
    @GET("/Nuclei/Messaging/Singleton/listAll")
    Call<ResponseBody> getChatRoomList(@Header("X-XSRF-TOKEN") String lang);
    
    @POST("/login")
    Call<LoginModel> userLogin(@Body LoginModel loginModel);
    
//    @GET("/Nuclei/Messaging/Singleton/listAll")

}
