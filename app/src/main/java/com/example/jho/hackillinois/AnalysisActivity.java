package com.example.jho.hackillinois;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AnalysisActivity extends AppCompatActivity {
    private static final String TAG = "AnalysisActivity";

    TextView text;

    @Override
    public void onCreate(Bundle savedInstanceStats) {
        super.onCreate(savedInstanceStats);

        setContentView(R.layout.analysis_activity);

        text = findViewById(R.id.description);
    }

    public String toDisplay() {


        return null;
    }
}
