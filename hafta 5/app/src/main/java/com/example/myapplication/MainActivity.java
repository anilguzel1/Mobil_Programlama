package com.example.myapplication;

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

        Button btnBluetooth = findViewById(R.id.btnBluetooth);
        Button btnWifi = findViewById(R.id.btnWifi);
        Button btnCamera = findViewById(R.id.btnCamera);

        btnBluetooth.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
            startActivity(intent);
        });

        btnWifi.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WifiActivity.class);
            startActivity(intent);
        });

        btnCamera.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
        });
    }
}