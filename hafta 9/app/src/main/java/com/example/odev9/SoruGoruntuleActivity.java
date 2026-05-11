package com.example.odev9;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class SoruGoruntuleActivity extends AppCompatActivity {

    private TextView tvSayac, tvSoru;
    private Button btnA, btnB, btnC, btnD;
    private List<Soru> sorular;
    private int mevcutIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_goruntule);

        tvSayac = findViewById(R.id.tvSayac);
        tvSoru = findViewById(R.id.tvSoru);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        sorular = dbHelper.tumSorulariGetir();

        if (sorular.isEmpty()) {
            Toast.makeText(this, "Henüz soru eklenmemiş!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Collections.shuffle(sorular);
        soruGoster();

        btnA.setOnClickListener(v -> sonrakiSoru());
        btnB.setOnClickListener(v -> sonrakiSoru());
        btnC.setOnClickListener(v -> sonrakiSoru());
        btnD.setOnClickListener(v -> sonrakiSoru());
    }

    private void soruGoster() {
        Soru s = sorular.get(mevcutIndex);
        tvSayac.setText((mevcutIndex + 1) + "/" + sorular.size());
        tvSoru.setText(s.getSoruMetni());
        btnA.setText("A) " + s.getSecenekA());
        btnB.setText("B) " + s.getSecenekB());
        btnC.setText("C) " + s.getSecenekC());
        btnD.setText("D) " + s.getSecenekD());
    }

    private void sonrakiSoru() {
        mevcutIndex++;
        if (mevcutIndex >= sorular.size()) {
            Toast.makeText(this, "Tüm sorular tamamlandı!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            soruGoster();
        }
    }
}
