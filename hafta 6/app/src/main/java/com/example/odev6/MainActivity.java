package com.example.odev6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sensor1Button = findViewById(R.id.sensor1_button);
        sensor1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccelerometerSensorActivity.class));
            }
        });

        Button sensor2Button = findViewById(R.id.sensor2_button);
        sensor2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CompassSensorActivity.class));
            }
        });

        Button sensor3Button = findViewById(R.id.sensor3_button);
        sensor3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GyroscopeSensorActivity.class));
            }
        });

        Button sensor4Button = findViewById(R.id.sensor4_button);
        sensor4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HumiditySensorActivity.class));
            }
        });

        Button sensor5Button = findViewById(R.id.sensor5_button);
        sensor5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LightSensorActivity.class));
            }
        });

        Button sensor6Button = findViewById(R.id.sensor6_button);
        sensor6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MagnetometerSensorActivity.class));
            }
        });

        Button sensor7Button = findViewById(R.id.sensor7_button);
        sensor7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PressureSensorActivity.class));
            }
        });

        Button sensor8Button = findViewById(R.id.sensor8_button);
        sensor8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProximitySensorActivity.class));
            }
        });

        Button sensor9Button = findViewById(R.id.sensor9_button);
        sensor9Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThermometerSensorActivity.class));
            }
        });
    }
}
