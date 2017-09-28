/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  Boolean signUpModeIsActive = true;
  TextView changeSignupTextview;

  @Override
  public void onClick(View view) {

    if (view.getId()==R.id.changeSignupTextview){

      Button signupButton = (Button)findViewById(R.id.signupButton);

      if(signUpModeIsActive){

        signUpModeIsActive=false;

        signupButton.setText("Login");
        changeSignupTextview.setText("Or, Signup");


      }else{

        signUpModeIsActive=true;

        signupButton.setText("Signup");
        changeSignupTextview.setText("Or, Login");

      }
    }

  }

  public void signUp(View view){

    EditText usernameText = (EditText) findViewById(R.id.usernameText);

    EditText passwordText = ( EditText)findViewById(R.id.passwordText);

    if ( usernameText.getText().toString().matches("") || passwordText.getText().toString()=="" ){

      Toast.makeText(this, "A username and password are required", Toast.LENGTH_SHORT).show();
    }else{

      if(signUpModeIsActive) {

        ParseUser user = new ParseUser();

        user.setUsername(usernameText.getText().toString());
        user.setPassword(passwordText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {

            if (e == null) {


              Log.i("signup", "successfull");
            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
          }
        });

      }else{
        // so if we are in the signup mode we sign up if not, we log in - the code below is for logging in

        ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

            if( user!= null){

              Log.i("user", "login successfull");

            }else{

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

          }
        });
      }
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    changeSignupTextview = (TextView)findViewById(R.id.changeSignupTextview);

    changeSignupTextview.setOnClickListener(this);


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}


