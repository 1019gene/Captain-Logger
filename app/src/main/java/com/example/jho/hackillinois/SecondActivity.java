package com.example.jho.hackillinois;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {

    private TextView food;
    private TextView clothing;
    private TextView entertainment;
    private TextView gas;
    private TextView travel;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        food = (TextView) findViewById(R.id.foodamount_tv);
        clothing = (TextView) findViewById(R.id.clothingamount_tv);
        entertainment = (TextView) findViewById(R.id.entertainmentamount_tv);
        gas = (TextView) findViewById(R.id.gasamount_tv);
        travel = (TextView) findViewById(R.id.travelamount_tv);

        Log.d("SecondActivity", "Reached second activity");

        Button manually = findViewById(R.id.manually_btn);

        Button camera = findViewById(R.id.camera_btn);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("woke", "woke");
                Log.e("Hey", String.valueOf(dataSnapshot.child("user").child("clothing").getValue()));

                food.setText(String.valueOf(dataSnapshot.child("user").child("food").getValue()));
                clothing.setText(String.valueOf(dataSnapshot.child("user").child("clothing").getValue()));
                entertainment.setText(String.valueOf(dataSnapshot.child("user").child("entertainment").getValue()));
                gas.setText(String.valueOf(dataSnapshot.child("user").child("gas").getValue()));
                travel.setText(String.valueOf(dataSnapshot.child("user").child("travel").getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("dd","djdj");
            }
        });



        camera.setOnClickListener(cameraOnClickListener);
        //Button graph = findViewById(R.id.graph_btn);
        //graph.setOnClickListener(graphOnClickListener);

        manually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
            }
        });
    }
    private View.OnClickListener graphOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(SecondActivity.this, SpendingsVisualizer.class));
        }
    };

    private View.OnClickListener cameraOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            startActivity(new Intent(SecondActivity.this, takePhoto.class));
        }

    };


}