package com.example.haritaservisiapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnCurrentLocation;
    private Button btnSetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCurrentLocation = findViewById(R.id.btn_current_location);
        btnSetLocation = findViewById(R.id.btn_set_location);

        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CurrentLocationActivity.class);
                startActivity(intent);
            }
        });

        btnSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetLocationActivity.class);
                startActivity(intent);
            }
        });
    }
}