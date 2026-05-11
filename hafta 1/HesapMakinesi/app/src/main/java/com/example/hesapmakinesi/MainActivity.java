package com.example.hesapmakinesi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private String currentInput = "", operator = "";
    private double firstOperand = Double.NaN;
    private double lastSecondOperand = Double.NaN; // Son kullanılan sayıyı tutmak için
    private String lastOperator = ""; // Son kullanılan operatörü tutmak için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // 1. Sayı Butonları
        int[] numIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for (int id : numIds) {
            findViewById(id).setOnClickListener(v -> {
                currentInput += ((Button) v).getText().toString();
                tvDisplay.setText(currentInput);
            });
        }

        // 2. Temel Operatörler (+, -, *, /)
        int[] basicOpIds = {R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide};
        for (int id : basicOpIds) {
            findViewById(id).setOnClickListener(v -> {
                if (!currentInput.isEmpty()) {
                    if (!Double.isNaN(firstOperand)) {
                        firstOperand = hesapla(firstOperand, Double.parseDouble(currentInput), operator);
                        tvDisplay.setText(formatResult(firstOperand));
                    } else {
                        firstOperand = Double.parseDouble(currentInput);
                    }
                    operator = ((Button) v).getText().toString();
                    currentInput = "";
                } else if (!Double.isNaN(firstOperand)) {
                    // Sayı yazmadan operatör değiştirilirse sadece operatörü güncelle
                    operator = ((Button) v).getText().toString();
                }
            });
        }

        // 3. Anında Hesaplayan Özel Butonlar (%, ½, x², √x)
        int[] instantOpIds = {R.id.btnYuzde, R.id.btnYarim, R.id.btnKare, R.id.btnKok};
        for (int id : instantOpIds) {
            findViewById(id).setOnClickListener(v -> {
                String input = currentInput.isEmpty() ? (Double.isNaN(firstOperand) ? "0" : String.valueOf(firstOperand)) : currentInput;
                double sayi = Double.parseDouble(input);
                double sonuc = 0;
                String islem = ((Button) v).getText().toString();

                switch (islem) {
                    case "%": sonuc = sayi / 100.0; break;
                    case "½": sonuc = sayi / 2.0; break;
                    case "x²": sonuc = Math.pow(sayi, 2); break;
                    case "√x": sonuc = Math.sqrt(sayi); break;
                }
                currentInput = formatResult(sonuc);
                tvDisplay.setText(currentInput);
                // Özel işlemlerde zincirleme mantığı genellikle aranmaz, sonucu direkt inputa alıyoruz.
            });
        }

        // 4. Temizle (C)
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            currentInput = ""; operator = ""; firstOperand = Double.NaN;
            lastSecondOperand = Double.NaN; lastOperator = "";
            tvDisplay.setText("0");
        });

        // 5. Eşittir (=) - ZİNCİRLEME MANTIĞI BURADA
        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            double second;

            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                // Normal hesaplama: 19 - 6 = 13
                second = Double.parseDouble(currentInput);
                lastSecondOperand = second; // 6'yı hafızaya al
                lastOperator = operator;    // - işlemini hafızaya al

                firstOperand = hesapla(firstOperand, second, operator);
                tvDisplay.setText(formatResult(firstOperand));

                currentInput = ""; // Yeni sayı girişi için hazırla
                operator = "";     // Operatörü temizle ama hafızadakiler duruyor
            }
            else if (!Double.isNaN(firstOperand) && !Double.isNaN(lastSecondOperand)) {
                // Zincirleme hesaplama: 13 (hafızadaki - 6) = 7
                firstOperand = hesapla(firstOperand, lastSecondOperand, lastOperator);
                tvDisplay.setText(formatResult(firstOperand));
                currentInput = "";
            }
        });
    }

    private double hesapla(double ilk, double ikinci, String op) {
        switch (op) {
            case "+": return ilk + ikinci;
            case "-": return ilk - ikinci;
            case "*": return ilk * ikinci;
            case "/": return ikinci != 0 ? ilk / ikinci : 0;
            default: return ikinci;
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.valueOf(result);
        }
    }
}