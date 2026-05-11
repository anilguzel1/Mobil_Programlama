package com.example.ders4;

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
        Button btnBack = findViewById(R.id.btnBack);

        String message = getIntent().getStringExtra("message");
        textViewResult.setText(message);

        btnBack.setOnClickListener(v -> finish());
    }
}