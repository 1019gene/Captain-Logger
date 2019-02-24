package com.example.jho.hackillinois;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import org.w3c.dom.Text;

public class SignUpActivity extends Activity implements View.OnClickListener {

    EditText editTextUsername, editTextPassword;

    private FirebaseAuth fire;

    private static final int MINIMUM_PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername = findViewById(R.id.username_txt);
        editTextPassword = findViewById(R.id.password_txt);

        Button signupButton = findViewById(R.id.signup_btn);
        signupButton.setOnClickListener(this);

        TextView login = findViewById(R.id.loginHyperlink);
        login.setOnClickListener(this);

        FirebaseApp.initializeApp(SignUpActivity.this);
        fire = FirebaseAuth.getInstance();
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        Log.d("TAG", "testing");

        if (username.isEmpty()) {
            editTextUsername.setError("Please enter a valid email.");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            editTextUsername.setError("Email not found, please enter a valid email.");
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter a password.");
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            editTextPassword.setError("Password must be at least 8 characters long.");
        }


        fire.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpHyperlink:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.signup_btn:
                registerUser();
                break;
        }
    }
}
