package com.info.renklisre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SeekBar s1, s2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s1 = findViewById(R.id.seekBar1);
        s2 = findViewById(R.id.seekBar2);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(v -> {

            int min = Math.min(s1.getProgress(), s2.getProgress());
            int max = Math.max(s1.getProgress(), s2.getProgress());

            Random random = new Random();
            int randomNumber = random.nextInt((max - min) + 1) + min;

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("sure", randomNumber);
            startActivity(intent);
        });
    }
}