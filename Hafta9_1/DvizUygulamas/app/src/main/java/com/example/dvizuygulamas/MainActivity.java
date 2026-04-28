package com.example.dvizuygulamas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView chfText;
    TextView usdText;
    TextView jpyText;
    TextView tryText;
    TextView cadText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chfText = findViewById(R.id.chfText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        tryText = findViewById(R.id.tryText);
        cadText = findViewById(R.id.cadText);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRates();
            }
        });
    }

    public void getRates() {
        DownloadData downloadData = new DownloadData();
        try {
            // Frankfurter API URL'i kullanılıyor
            String url = "https://api.frankfurter.app/latest";
            downloadData.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                // Zaman aşımı süreleri ekleyelim (bağlantı hatasını daha hızlı anlamak için)
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();

            } catch (Exception e) {
                Log.e("MainActivity", "Error downloading data", e);
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null) {
                Toast.makeText(MainActivity.this, "İnternet bağlantınızı kontrol edin veya sunucuya ulaşılamıyor.", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject rates = jsonObject.getJSONObject("rates");

                String chf = rates.getString("CHF");
                String usd = rates.getString("USD");
                String jpy = rates.getString("JPY");
                String turkishLira = rates.getString("TRY");
                String cad = rates.getString("CAD");

                chfText.setText("CHF: " + chf);
                usdText.setText("USD: " + usd);
                jpyText.setText("JPY: " + jpy);
                tryText.setText("TRY: " + turkishLira);
                cadText.setText("CAD: " + cad);

            } catch (Exception e) {
                Log.e("MainActivity", "Error parsing JSON", e);
                Toast.makeText(MainActivity.this, "Veri işlenirken bir hata oluştu.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
