package com.example.jho.hackillinois;


import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import com.camerakit.CameraKitView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.support.annotation.*;

import java.io.*;

import android.os.Environment;

public class takePhoto extends AppCompatActivity {
        private CameraKitView cameraKitView;
        private Button photoButton;
        private Uri filePath;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.camerakitview);
            cameraKitView = findViewById(R.id.camera);

            photoButton = findViewById(R.id.photoButton);
            photoButton.setOnClickListener(photoOnClickListener);

        }

        @Override
        protected void onResume() {
            super.onResume();
            cameraKitView.onResume();
        }

        @Override
        protected void onPause() {
            super.onPause();
            cameraKitView.onPause();
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

                        filePath = Uri.fromFile(savedPhoto);
                        outputStream.write(photo);
                        outputStream.close();

                       Intent ParseReceipt = new Intent(takePhoto.this, ReceiptDataAnalyzer.class);
                       ParseReceipt.putExtra("uriString", filePath.toString());
                       startActivity(ParseReceipt);

                    } catch (java.io.IOException e) {
                       e.printStackTrace();
                    }

                }
            });


        }
    };
}

