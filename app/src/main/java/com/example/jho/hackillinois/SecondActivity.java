package com.example.jho.hackillinois;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.d("SecondActivity", "Reached second activity");

        Button manually = findViewById(R.id.manually_btn);

        Button camera = findViewById(R.id.camera_btn);
        camera.setOnClickListener(cameraOnClickListener);


        manually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
            }
        });
    }
    private View.OnClickListener cameraOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(SecondActivity.this, takePhoto.class));
        }

    };


}