package com.poc.chatty;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.poc.chatty.models.LoginModel;
import com.poc.chatty.rest.ApiClient;
import com.poc.chatty.rest.ApiInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jonregalado on 10/28/17.
 */

public class MainActivity extends AppCompatActivity {
    
    private String TAG = "ChattyApp";
    private Boolean loginModeActive = false;
    
    private EditText usernameEditText;
    private EditText passwordEditText;
    
    private Button signupLoginButton;
    
    private static final String HEADER_KEY = "Set-Cookie";
    private static final String XSRF_TOKEN_KEY = "XSRF-TOKEN";
    
    public static String XSRF_TOKEN;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        signupLoginButton = findViewById(R.id.signupLoginButton);
        
        signupLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupOrLogin();
            }
        });
    }
    
    public void toggleLoginMode(View view) {
        
        TextView toggle_loginsignup_mode = findViewById(R.id.toggle_loginsignup_mode);
        
        if (loginModeActive) {
            loginModeActive = false;
            signupLoginButton.setText("Sign up");
            toggle_loginsignup_mode.setText("Have an account? Login here");
            
        } else {
            loginModeActive = true;
            signupLoginButton.setText("Login");
            toggle_loginsignup_mode.setText("Don't have an account? Signup here");
        }
    }
    
    public void signupOrLogin() {
        
        usernameEditText = findViewById(R.id.input_email);
        passwordEditText = findViewById(R.id.input_password);
        
        if (loginModeActive) {
            if (!validate()) {
                signInFailed();
                return;
            } else {
                ApiInterface apiService = ApiClient.getClient()
                        .create(ApiInterface.class);
                
                LoginModel loginModel = new LoginModel(usernameEditText.getText()
                        .toString(), passwordEditText.getText()
                        .toString());
                
                Call<LoginModel> call = apiService.userLogin(loginModel);
                call.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        Log.d(TAG, "header values: " + response.headers()
                                .newBuilder());
                        
                        List<String> headers = response.headers()
                                .values(HEADER_KEY);
                        
                        for (String header : headers) {
                            if (header.contains(XSRF_TOKEN_KEY)) {
                                String[] splitHeader = header.split(";");
                                
                                for (String separatedHeader : splitHeader) {
                                    if (separatedHeader.contains(XSRF_TOKEN_KEY)) {
                                        XSRF_TOKEN = separatedHeader.substring(11);
                                    }
                                }
                            }
                        }
                        Log.i(TAG, "XSRF TOKEN Final Value " + XSRF_TOKEN);
                        proceedToHomeActivity();
                    }
                    
                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t);
                    }
                });
                Log.i(TAG, "exit retrofit callback");
            }
        } else {
            if (!validate()) {
                signInFailed();
                return;
            } else {
                ParseUser user = new ParseUser();
                
                user.setUsername(usernameEditText.getText()
                        .toString());
                user.setPassword(passwordEditText.getText()
                        .toString());
                
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i(TAG, "user signed up");
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
            }
        }
    }
    
    private boolean validate() {
        boolean valid = true;
        
        EditText userName = findViewById(R.id.input_email);
        String _userName = userName.getText()
                .toString();
        
        EditText password = findViewById(R.id.input_password);
        String _password = password.getText()
                .toString();
        
        if (_userName.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_userName)
                .matches()) {
            userName.setError("enter a valid email address");
            valid = false;
        } else {
            userName.setError(null);
        }
        
        if (_password.isEmpty() || _password.length() < 4 || password.length() > 30) {
            password.setError("between 4 and 30 alphanumeric characters required");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }
    
    private void signInFailed() {
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT)
                .show();
    }
    
    private void proceedToHomeActivity () {
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
        this.finish();
    }
}