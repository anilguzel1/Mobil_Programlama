package com.example.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class WifiActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private ArrayList<String> wifiList;
    private ArrayAdapter<String> adapter;
    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wifiList);

        ListView lvWifi = findViewById(R.id.lvWifiList);
        lvWifi.setAdapter(adapter);

        Button btnOn = findViewById(R.id.btnWifiOn);
        Button btnOff = findViewById(R.id.btnWifiOff);
        Button btnList = findViewById(R.id.btnWifiList);

        btnOn.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                startActivity(panelIntent);
            } else {
                wifiManager.setWifiEnabled(true);
                Toast.makeText(this, "Wifi Açılıyor...", Toast.LENGTH_SHORT).show();
            }
        });

        btnOff.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                startActivity(panelIntent);
            } else {
                wifiManager.setWifiEnabled(false);
                Toast.makeText(this, "Wifi Kapatılıyor...", Toast.LENGTH_SHORT).show();
            }
        });

        btnList.setOnClickListener(v -> {
            if (!wifiManager.isWifiEnabled()) {
                Toast.makeText(this, "Lütfen önce Wifi'yi açın", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isLocationEnabled()) {
                Toast.makeText(this, "Wifi taraması için Konum servislerini açmalısınız", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                return;
            }
            checkPermissionsAndScan();
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiReceiver, intentFilter);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void checkPermissionsAndScan() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            startWifiScan();
        }
    }

    private void startWifiScan() {
        Toast.makeText(this, "Wifi taranıyor...", Toast.LENGTH_SHORT).show();
        boolean success = wifiManager.startScan();
        if (!success) {
            // Tarama başarısız olsa bile (throttle vs), mevcut sonuçları göster
            updateWifiList();
        }
    }

    private void updateWifiList() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        List<ScanResult> results = wifiManager.getScanResults();
        wifiList.clear();
        for (ScanResult result : results) {
            String ssid = result.SSID;
            if (ssid == null || ssid.isEmpty()) {
                ssid = "[Gizli Ağ]";
            }
            wifiList.add(ssid + " (" + result.level + " dBm)");
        }
        if (wifiList.isEmpty()) {
            wifiList.add("Hiçbir ağ bulunamadı.");
        }
        adapter.notifyDataSetChanged();
    }

    private final BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
            updateWifiList();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startWifiScan();
            } else {
                Toast.makeText(this, "Konum izni olmadan Wifi taraması yapılamaz", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(wifiReceiver);
        } catch (Exception e) {
            // Receiver zaten kayıtlı değilse hata vermemesi için
        }
    }
}