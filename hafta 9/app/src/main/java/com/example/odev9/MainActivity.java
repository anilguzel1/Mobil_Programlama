package com.example.odev9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHazirla = findViewById(R.id.btnHazirla);
        Button btnGoruntule = findViewById(R.id.btnGoruntule);

        btnHazirla.setOnClickListener(v ->
                startActivity(new Intent(this, SoruHazirlaActivity.class)));

        btnGoruntule.setOnClickListener(v ->
                startActivity(new Intent(this, SoruGoruntuleActivity.class)));
    }
}
