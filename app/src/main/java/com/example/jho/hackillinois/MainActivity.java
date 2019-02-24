package com.example.jho.hackillinois;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.username_txt);
        editTextPassword = findViewById(R.id.password_txt);

        Button loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(this);

        TextView signUp = findViewById(R.id.signUpHyperlink);
        signUp.setOnClickListener(this);

        FirebaseApp.initializeApp(MainActivity.this);
        fire = FirebaseAuth.getInstance();
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
}
