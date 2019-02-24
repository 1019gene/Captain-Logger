package com.example.jho.hackillinois;


import android.net.Uri;
import android.widget.Button;
import com.camerakit.CameraKitView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.support.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import android.os.Environment;

import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.FirstPartyScopes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class takePhoto extends AppCompatActivity {


        private static final String TAG = "wack";


        private CameraKitView cameraKitView;
        private Button photoButton;
        private Button executeButton;


        public FirebaseVisionImage image;
        public FirebaseVisionText text;

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
                        Log.d(TAG, savedPhoto.getAbsolutePath());

                        filePath = Uri.fromFile(savedPhoto);
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

                 FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

                 Task<FirebaseVisionText> result = detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {

                                    @Override
                                     public void onSuccess(FirebaseVisionText firebaseVisionText) {

                                         text = firebaseVisionText;
                                         List<FirebaseVisionText.TextBlock> stringText = text.getTextBlocks();
                                         List<FirebaseVisionText.Line> lines = new ArrayList<>();
                                         List<FirebaseVisionText.Element> elements = new ArrayList<>();

                                         String blockString = "";

                                         for(FirebaseVisionText.TextBlock block: stringText){
                                             Log.e(TAG, "killme");

                                             for(FirebaseVisionText.Line line: block.getLines()){
                                                 for(FirebaseVisionText.Element element: line.getElements()){

                                                 }
                                             }
                                             Log.d("SUPERWACK", block.getText());
                                             blockString += block.toString();

                                         }
                                         String toDisplay2= "";
                                         toDisplay2+= blockString.length();
                                         Log.d("wackier", toDisplay2);
                                         String toDisplay= "";
                                         toDisplay+= stringText.size();
                                         Log.d("wackier", toDisplay);
                                         Log.d("wackier1", blockString);

                                     }
                                 })
                                 .addOnFailureListener(
                                         new OnFailureListener() {
                                             @Override
                                             public void onFailure(@NonNull Exception e) {
                                                 Log.e(TAG, "actually killme");
                                             }
                                         });
             } catch (IOException e) {
                 e.printStackTrace();
             }

         }

     };









}

