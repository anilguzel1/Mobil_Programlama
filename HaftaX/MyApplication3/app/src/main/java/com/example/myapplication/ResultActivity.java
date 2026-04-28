package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textViewResult = findViewById(R.id.textViewResult);
        Button buttonBack = findViewById(R.id.buttonBack);

        String cityName = getIntent().getStringExtra("CITY_NAME");
        boolean isMatched = getIntent().getBooleanExtra("IS_MATCHED", false);

        if (isMatched) {
            textViewResult.setText(cityName + " eşleşti");
            textViewResult.setTextColor(Color.GREEN);
        } else {
            textViewResult.setText(cityName + " eşleşmedi");
            textViewResult.setTextColor(Color.RED);
        }

        buttonBack.setOnClickListener(v -> finish());
    }
}