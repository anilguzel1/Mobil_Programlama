package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1️⃣ TextView Bağlantısı
        tvDisplay = findViewById(R.id.tvDisplay);

        // 2️⃣ Sayı Butonlarını Dizide Tanımla
        int[] numberButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        // 3️⃣ Sayı Butonları İçin Tıklama Olayı (OnClickListener)
        View.OnClickListener numberClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                currentInput += b.getText().toString();
                tvDisplay.setText(currentInput);
            }
        };

        // Sayı butonlarına listener'ı ata
        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(numberClick);
        }

        // 4️⃣ Operatör Butonlarını Dizide Tanımla (Karekök butonu buradan çıkarıldı)
        int[] operatorButtonIds = {
                R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnFraction, R.id.btnPower
        };

        // 5️⃣ Operatör Butonları İçin Tıklama Olayı
        View.OnClickListener operatorClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);
                    Button b = (Button) v;
                    operator = b.getText().toString();
                    currentInput = "";
                    tvDisplay.setText(operator); // Ekranda kısa süreliğine operatörü göster
                }
            }
        };

        // Operatör butonlarına listener'ı ata
        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(operatorClick);
        }

        // 6️⃣ Özel Buton: 1/x (Tek sayıyla anında çalışan işlem)
        findViewById(R.id.btnInverse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput);
                    if (value != 0) {
                        currentInput = formatResult(1.0 / value);
                        tvDisplay.setText(currentInput);
                    } else {
                        tvDisplay.setText("Sıfıra bölünemez");
                        currentInput = "";
                    }
                }
            }
        });

        // 🌟 YENİ: Özel Buton: Karekök (√) - Anında Çalışan İşlem
        findViewById(R.id.btnRoot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput);
                    if (value >= 0) {
                        currentInput = formatResult(Math.sqrt(value));
                        tvDisplay.setText(currentInput);
                    } else {
                        tvDisplay.setText("Tanımsız"); // Negatif sayıların reel kökü olmaz
                        currentInput = "";
                    }
                }
            }
        });

        // 7️⃣ Temizle Butonu (C)
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                operator = "";
                firstOperand = 0;
                tvDisplay.setText("");
            }
        });

        // 8️⃣ Eşittir Butonu (=) - Asıl işlemlerin yapıldığı yer
        findViewById(R.id.btnEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty() && !operator.isEmpty()) {
                    double secondOperand = Double.parseDouble(currentInput);
                    double result = 0;
                    boolean isError = false;

                    switch (operator) {
                        case "+": result = firstOperand + secondOperand; break;
                        case "-": result = firstOperand - secondOperand; break;
                        case "*": result = firstOperand * secondOperand; break;
                        case "/":
                        case "a/b": // a/b işlemini bölme olarak kabul ediyoruz
                            if (secondOperand != 0) result = firstOperand / secondOperand;
                            else isError = true;
                            break;
                        case "x^y": result = Math.pow(firstOperand, secondOperand); break;
                    }

                    if (isError) {
                        tvDisplay.setText("Tanımsız");
                        currentInput = "";
                    } else {
                        currentInput = formatResult(result);
                        tvDisplay.setText(currentInput);
                    }

                    // İşlem bittikten sonra operatörü sıfırla
                    operator = "";
                }
            }
        });
    }

    // 9️⃣ Sonucu formatla (Örn: Sonuç 5.0 ise ekrana sadece "5" yazdırır)
    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.valueOf(result);
        }
    }
}