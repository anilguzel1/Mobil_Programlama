package com.example.hafta8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupButton(R.id.sensor1_button, AccelerometerActivity.class);
        setupButton(R.id.sensor2_button, CompassActivity.class);
        setupButton(R.id.sensor3_button, GyroscopeActivity.class);
        setupButton(R.id.sensor4_button, HumidityActivity.class);
        setupButton(R.id.sensor5_button, LightActivity.class);
        setupButton(R.id.sensor6_button, MagnetometerActivity.class);
        setupButton(R.id.sensor7_button, PressureActivity.class);
        setupButton(R.id.sensor8_button, ProximityActivity.class);
        setupButton(R.id.sensor9_button, ThermometerActivity.class);
    }

    private void setupButton(int buttonId, Class<?> targetActivity) {
        Button button = findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, targetActivity);
                startActivity(intent);
            });
        }
    }
}
