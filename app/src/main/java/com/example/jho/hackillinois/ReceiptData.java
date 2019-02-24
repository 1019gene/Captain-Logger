package com.example.jho.hackillinois;

import android.util.Pair;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class ReceiptData {

    private HashMap<String,Double> data;

    public ReceiptData(FirebaseVisionText VisionText){
        ParseBlocks(VisionText);
    }


    private void ParseBlocks(FirebaseVisionText VisionText){
        for(FirebaseVisionText.TextBlock block: VisionText.getTextBlocks()){
            FindItemsInBlock(block);
        }
    }

    private void FindItemsInBlock(FirebaseVisionText.TextBlock block){

    }

    public HashMap<String,Double> getData(){
        return data;
    }

}

