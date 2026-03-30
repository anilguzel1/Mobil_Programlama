package com.example.hafta6kod;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

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

        Button btnOpenDialog = findViewById(R.id.btn_open_dialog);
        btnOpenDialog.setOnClickListener(v -> showCitiesDialog());
    }

    private void showCitiesDialog() {

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setPadding(50, 50, 50, 50);


        LinearLayout headerLayout = new LinearLayout(this);
        headerLayout.setOrientation(LinearLayout.HORIZONTAL);
        headerLayout.setGravity(Gravity.CENTER_VERTICAL);


        int[] drawables = {
                R.drawable.ank1, R.drawable.ank2, R.drawable.ank3,
                R.drawable.ist1, R.drawable.ist2, R.drawable.ist3,
                R.drawable.izm1, R.drawable.izm2, R.drawable.izm3
        };
        int randomResId = drawables[new Random().nextInt(drawables.length)];

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(randomResId);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(150, 150);
        imageView.setLayoutParams(imgParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView titleView = new TextView(this);
        titleView.setText("Gezilecek Şehirler");
        titleView.setTextSize(22);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setPadding(30, 0, 0, 0);

        headerLayout.addView(imageView);
        headerLayout.addView(titleView);
        rootLayout.addView(headerLayout);


        String[] cities = {"İstanbul", "Ankara", "İzmir"};
        Class<?>[] activities = {IstanbulActivity.class, AnkaraActivity.class, IzmirActivity.class};

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(rootLayout)
                .create();

        for (int i = 0; i < cities.length; i++) {
            TextView cityTv = new TextView(this);
            cityTv.setText(cities[i]);
            cityTv.setTextSize(20);
            cityTv.setPadding(20, 40, 20, 40);
            cityTv.setBackgroundColor(Color.TRANSPARENT);
            
            final Class<?> activityClass = activities[i];
            cityTv.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, activityClass));
                dialog.dismiss();
            });
            
            rootLayout.addView(cityTv);
        }

        dialog.show();
    }
}