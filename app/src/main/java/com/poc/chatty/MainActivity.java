package com.poc.chatty;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jonregalado on 10/28/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "LoginGimmyo";
    private String userAccount;
    private String passwordAccount;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button signinButton = (Button) findViewById(R.id.loginButton);
        signinButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.loginButton:
                signIn();
        }
    }

    private void signIn() {

        if(!validate()){
            onSignInFailed();
            return;
        } else {

            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating your account...");
            progressDialog.show();

            EditText userName = (EditText) findViewById(R.id.input_email);
            final String _userName = userName.getText().toString();

            EditText password = (EditText) findViewById(R.id.input_password);
            final String _password = password.getText().toString();

            setUserAccount(_userName, _password);

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

                            query.getInBackground("lhPdEne3nI", new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    if (e == null && object != null) {

                                        boolean loggedIn = confirmLoginAccount(object.getString("username"), Integer.toString(object.getInt("score")));
                                        Log.v(TAG, String.valueOf(loggedIn));
                                        if(!loggedIn) {
                                            onSignInFailed();
                                        }
                                    }
                                }
                            });
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }
    }

    public void setUserAccount(String userAccount, String passwordAccount){
        this.userAccount = userAccount;
        this.passwordAccount = passwordAccount;
    }

    private boolean confirmLoginAccount(String username, String password) {
        if (username.equals(userAccount) && password.equals(passwordAccount)) {
            Log.v(TAG, "success logging in");
            Intent successLogin = new Intent(MainActivity.this, UserListActivity.class);
            startActivity(successLogin);
            finish();
            return true;
        }
        else {
            return false;
        }
    }

    private void onSignInFailed() {
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        boolean valid = true;

        EditText userName = (EditText) findViewById(R.id.input_email);
        String _userName = userName.getText().toString();

        EditText password = (EditText) findViewById(R.id.input_password);
        String _password = password.getText().toString();

        if (_userName.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_userName).matches()){
            userName.setError("enter a valid email address");
            valid = false;
        } else {
            userName.setError(null);
        }

        if (_password.isEmpty() || _password.length() < 4 || password.length() > 15) {
            password.setError("between 4 and 15 alphanumeric characters required");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.parse.GetCallback;
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//
//public class MainActivity extends AppCompatActivity {
//
//    private String TAG = "Chatty";
//    private String userAccount;
//    private String passwordAccount;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_page);
//
//        Button loginButton = findViewById(R.id.loginButton);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });
//    }
//
//    private void signIn() {
//
//        if(!validCredentials()) {
//            onSignInFailed();
//            return;
//        } else {
//
//            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Authenticating your account...");
//            progressDialog.show();
//
//            EditText userName = (EditText) findViewById(R.id.input_email);
//            final String _userName = userName.getText().toString();
//
//            EditText password = (EditText) findViewById(R.id.input_password);
//            final String _password = password.getText().toString();
//
//            setUserAccount(_userName, _password);
//
//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//                            query.getInBackground("lhPdEne3nI", new GetCallback<ParseObject>() {
//                                @Override
//                                public void done(ParseObject object, ParseException e) {
//                                    if (e == null && object != null) {
//
//                                        boolean loggedIn = confirmLoginAccount(object.getString("username"), Integer.toString(object.getInt("score")));
//                                        if(!loggedIn) {
//                                            onSignInFailed();
//                                        }
//                                    }
//                                }
//                            });
//                            progressDialog.dismiss();
//                        }
//                    }, 1000);
//        }
//    }
//
//    public void setUserAccount(String userAccount, String passwordAccount){
//        this.userAccount = userAccount;
//        this.passwordAccount = passwordAccount;
//    }
//
//    private void onSignInFailed() {
//        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
//    }
//
//    private boolean confirmLoginAccount(String username, String password) {
//        if (username.equals(userAccount) && password.equals(passwordAccount)) {
//            Log.v(TAG, "success logging in");
//            Intent successLogin = new Intent(MainActivity.this, UserListActivity.class);
//            startActivity(successLogin);
//            finish();
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//
//    private boolean validCredentials() {
//        boolean credentialsState = true;
//
//        EditText userName = findViewById(R.id.input_email);
//        String _userName = userName.getText().toString();
//
//        EditText password = findViewById(R.id.input_password);
//        String _password = password.getText().toString();
//
//        if (_userName.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_userName).matches()){
//            userName.setError("enter a valid email address");
//            credentialsState = false;
//        } else {
//            userName.setError(null);
//        }
//
//        if (_password.isEmpty() || _password.length() < 4 || password.length() > 15) {
//            password.setError("between 4 and 15 alphanumeric characters required");
//            credentialsState = false;
//        } else {
//            password.setError(null);
//        }
//        return credentialsState;
//    }
//}
