package com.example.jho.hackillinois;

import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUsername, editTextPassword;

    private FirebaseAuth fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.username_txt);
        editTextPassword = findViewById(R.id.password_txt);

        FirebaseApp.initializeApp(MainActivity.this);
        fire = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(this);

        TextView signUp = findViewById(R.id.signUpHyperlink);
        signUp.setOnClickListener(this);
    }

    private void userLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        fire.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("login", "login success");
                    FirebaseUser user = fire.getCurrentUser();
                    Log.d("SecondActivity", "Have not reached");
                    startSecondActivity();
                    //change to new activity
                } else {
                    AlertDialog failureMessage = new AlertDialog.Builder(MainActivity.this).create();
                    failureMessage.setTitle("Login failure");
                    failureMessage.setMessage("Invalid email or password. Please try again.");
                    failureMessage.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    failureMessage.show();
                    Log.d("login", "login failure");
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpHyperlink:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.login_btn:
                userLogin();
        }
    }

    private void startSecondActivity() {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
