package com.example.jho.hackillinois;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThirdActivity extends AppCompatActivity {

    private Spinner categoryPull;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Button done = findViewById(R.id.done_btn);

        Spinner type = findViewById(R.id.spendtype_spn);

        final TextView amount = findViewById(R.id.amount_pt);

        final String category = type.getSelectedItem().toString();


        addItemsOncategoryPull();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentAmount = String.valueOf(myRef.child("user").child(category).child("0"));
                Knmn                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     d = Double.parseDouble(currentAmount);
                String s = amount.getText().toString();
                Double dd = Double.parseDouble(s);
                Integer f = d + dd;

                myRef.child("user").child(category).child("0").setValue("$$$$$");

                startActivity(new Intent(ThirdActivity.this, SecondActivity.class));
            }
        });


    }
    public void addItemsOncategoryPull(){
        categoryPull = (Spinner) findViewById(R.id.spendtype_spn);
        categoryPull.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
    public void addListenerOnButton() {

        categoryPull = (Spinner) findViewById(R.id.spendtype_spn);


    }

}
