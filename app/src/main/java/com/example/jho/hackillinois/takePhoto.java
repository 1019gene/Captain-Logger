package com.example.jho.hackillinois;


import android.net.Uri;
import android.widget.Button;
import com.camerakit.CameraKitView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.support.annotation.*;

import java.io.*;


import android.os.Environment;

import android.util.Log;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;

public class takePhoto extends AppCompatActivity {





        private CameraKitView cameraKitView;
        private Button photoButton;
        private Button executeButton;

        FirebaseVisionImage image;

        private Uri filePath;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.camerakitview);
            cameraKitView = findViewById(R.id.camera);

            photoButton = findViewById(R.id.photoButton);
            photoButton.setOnClickListener(photoOnClickListener);

            executeButton = findViewById(R.id.executeButton);
            executeButton.setOnClickListener(executeOnClickListener);
        }

        @Override
        protected void onResume() {
            super.onResume();
            cameraKitView.onResume();
        }

        @Override
        protected void onPause() {
            cameraKitView.onPause();
            super.onPause();
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    private View.OnClickListener photoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                @Override
                public void onImage(CameraKitView cameraKitView, final byte[] photo) {
                    File savedPhoto = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
                    try {
                        FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                        filePath = Uri.parse(savedPhoto.getPath());
                        outputStream.write(photo);
                        outputStream.close();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                        Log.e("CKDemo", "Exception in photo callback");
                    }
                }
            });
        }
    };


     private View.OnClickListener executeOnClickListener = new View.OnClickListener(){
         @Override
         public void onClick(View v){
             try {
                 image = FirebaseVisionImage.fromFilePath(getApplicationContext(), filePath);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }

     };









}
