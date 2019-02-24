package com.example.jho.hackillinois;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.lang.Math;
import java.lang.Object;

public class ReceiptDataAnalyzer extends AppCompatActivity {

    private Button executeButton;
    private Button backButton;
    public FirebaseVisionText text;

    private String uriString;
    private Uri filePath;

    private ArrayList<String> clothingKeywords = new ArrayList<>(Arrays.asList("shirt", "fleece", "hood", "sock"
            , "dress", "pant", "coat", "jacket", "underwear", "garment", "skirt", "suit", "bra", "wear"));
    private ArrayList<String> foodKeywords = new ArrayList<>(Arrays.asList("burrito", "taco", "steak", "chicken", "sandwich", "boba", "drink", "fries", "burger", "noodles"));
    private ArrayList<String> travelKeywords = new ArrayList<>(Arrays.asList("car", "ride", "uber", "flight", "train", "bus", "subway"));
    private ArrayList<String> entertainmentKeywords = new ArrayList<>(Arrays.asList("movie", "play", "park", "amusement", "toy"));

    protected void onCreate(Bundle savedInstanceState) {
        Log.d("REEEE", "DASDASSDSADAS");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_data);
        Bundle b = getIntent().getExtras();
        uriString = b.getString("uriString");
        filePath = Uri.parse(uriString);
        backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(backOnClickListener);
        executeButton = findViewById(R.id.save_btn);
        executeButton.setOnClickListener(executeOnClickListener);

    }

    public HashMap<Categories, ArrayList<Item>> sortToCategories(){
        List<Item> items = createItems();
        for(Item item:items){
            Log.d("GENERATE ITEMS", item.getName() + "  :   " + item.getPrice());
        }
        return null;
    }

    private List<Item> createItems(){
        List<Item> items = new ArrayList<>();
        List<FirebaseVisionText.Element> elements = ElementsFromBlocks(text);
        for(FirebaseVisionText.Element price: elements){
            if(price.getText().contains("$")) {
                Point pricePos1 = price.getCornerPoints()[0];
                Point pricePos2 = price.getCornerPoints()[3];
                String itemNameStr = "";
                for (FirebaseVisionText.Element itemName : elements) {
                    if (itemName != price && itemName.getText().contains("$") == false &&
                            itemName.getText().contains("total") == false &&
                            itemName.getText().contains("Total") == false &&
                            itemName.getText().contains("tax") == false &&
                            itemName.getText().contains("Tax") == false) {
                        Point itemPos1 = itemName.getCornerPoints()[0];
                        Point itemPos2 = itemName.getCornerPoints()[3];
                        double error = Math.abs(itemPos2.y - itemPos1.y) * 0.33;
                        if(itemPos2.y < pricePos2.y + error && itemPos2.y > pricePos2.y - error &&
                                itemPos1.y < pricePos1.y + error &&
                                itemPos1.y > pricePos1.y - error);{
                            itemNameStr+=itemName.getText() + " ";
                        }
                    }
                }
                String priceStr = price.getText();
                priceStr = priceStr.substring(priceStr.indexOf("$") + 1);
                int decimalIndex = priceStr.indexOf(".");
                if(priceStr.substring(decimalIndex + 1).length() > 2){
                    priceStr = priceStr.substring(0, decimalIndex+3);
                }

                Item i = new Item( itemNameStr, Double.parseDouble(priceStr));
                items.add(i);
            }
        }
        return items;
    }

    private List<FirebaseVisionText.Element> ElementsFromBlocks(@NonNull FirebaseVisionText VisionText){
        List<FirebaseVisionText.Element> elements = new ArrayList<>();
        for(FirebaseVisionText.TextBlock block: VisionText.getTextBlocks()){
            for(FirebaseVisionText.Line line: block.getLines()){
                for(FirebaseVisionText.Element element: line.getElements()){
                    elements.add(element);
                }
            }
        }

        return elements;
    }

    private View.OnClickListener executeOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            try {

                FirebaseVisionImage image = FirebaseVisionImage.fromFilePath(getApplicationContext(), filePath);

                FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

                Task<FirebaseVisionText> result = detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {

                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {

                        text = firebaseVisionText;
                        sortToCategories();

                    }
                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    };

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePhoto = new Intent(ReceiptDataAnalyzer.this, takePhoto.class);
            startActivity(takePhoto);
        }
    };
}

