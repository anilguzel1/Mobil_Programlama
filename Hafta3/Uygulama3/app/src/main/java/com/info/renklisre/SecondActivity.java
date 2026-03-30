package com.info.renklisre;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    LinearLayout rootLayout;
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        rootLayout = findViewById(R.id.rootLayout);
        tvTime = findViewById(R.id.tvTime);

        int sure = getIntent().getIntExtra("sure", 0);

        new CountDownTimer(sure * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int kalan = (int) millisUntilFinished / 1000;
                tvTime.setText("Geri Sayım: " + kalan + " saniye");

                Random rnd = new Random();
                int color = Color.rgb(
                        rnd.nextInt(256),
                        rnd.nextInt(256),
                        rnd.nextInt(256)
                );

                rootLayout.setBackgroundColor(color);
            }

            @Override
            public void onFinish() {
                Toast.makeText(SecondActivity.this,
                        "Uygulama bitmiştir.",
                        Toast.LENGTH_LONG).show();
            }
        }.start();
    }
}