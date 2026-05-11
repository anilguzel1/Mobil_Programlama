package com.example.odev9;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SoruHazirlaActivity extends AppCompatActivity {

    private EditText etSoru, etA, etB, etC, etD;
    private RadioButton rbA, rbB, rbC, rbD;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_hazirla);

        dbHelper = new DatabaseHelper(this);

        etSoru = findViewById(R.id.etSoru);
        etA = findViewById(R.id.etA);
        etB = findViewById(R.id.etB);
        etC = findViewById(R.id.etC);
        etD = findViewById(R.id.etD);
        rbA = findViewById(R.id.rbA);
        rbB = findViewById(R.id.rbB);
        rbC = findViewById(R.id.rbC);
        rbD = findViewById(R.id.rbD);
        Button btnKaydet = findViewById(R.id.btnKaydet);

        rbA.setOnClickListener(v -> secimYap(0));
        rbB.setOnClickListener(v -> secimYap(1));
        rbC.setOnClickListener(v -> secimYap(2));
        rbD.setOnClickListener(v -> secimYap(3));

        btnKaydet.setOnClickListener(v -> soruKaydet());
    }

    private void secimYap(int secilen) {
        rbA.setChecked(secilen == 0);
        rbB.setChecked(secilen == 1);
        rbC.setChecked(secilen == 2);
        rbD.setChecked(secilen == 3);
    }

    private void soruKaydet() {
        String soru = etSoru.getText().toString().trim();
        String a = etA.getText().toString().trim();
        String b = etB.getText().toString().trim();
        String c = etC.getText().toString().trim();
        String d = etD.getText().toString().trim();

        if (soru.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return;
        }

        int dogru = -1;
        if (rbA.isChecked()) dogru = 0;
        else if (rbB.isChecked()) dogru = 1;
        else if (rbC.isChecked()) dogru = 2;
        else if (rbD.isChecked()) dogru = 3;

        if (dogru == -1) {
            Toast.makeText(this, "Lütfen doğru cevabı seçin!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean basarili = dbHelper.soruEkle(soru, a, b, c, d, dogru);
        if (basarili) {
            Toast.makeText(this, "Soru kaydedildi!", Toast.LENGTH_SHORT).show();
            alanlarTemizle();
        } else {
            Toast.makeText(this, "Kayıt başarısız!", Toast.LENGTH_SHORT).show();
        }
    }

    private void alanlarTemizle() {
        etSoru.setText("");
        etA.setText("");
        etB.setText("");
        etC.setText("");
        etD.setText("");
        rbA.setChecked(false);
        rbB.setChecked(false);
        rbC.setChecked(false);
        rbD.setChecked(false);
    }
}
