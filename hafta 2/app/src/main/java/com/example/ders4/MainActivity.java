package com.example.ders4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewNumbers, listViewCities;
    private Button btnGenerate;
    private List<Integer> numbers;
    private List<String> cities;
    private boolean isSyncing = false;
    private String[] allCities = {
            "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin",
            "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa",
            "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan",
            "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta",
            "Mersin", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir",
            "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla",
            "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop",
            "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat",
            "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın",
            "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewNumbers = findViewById(R.id.listViewNumbers);
        listViewCities = findViewById(R.id.listViewCities);
        btnGenerate = findViewById(R.id.btnGenerate);

        generateLists();

        btnGenerate.setOnClickListener(v -> generateLists());

        listViewNumbers.setOnItemClickListener((parent, view, position, id) -> checkMatch(position));
        listViewCities.setOnItemClickListener((parent, view, position, id) -> checkMatch(position));

        // Sync scrolling
        listViewNumbers.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isSyncing) {
                    isSyncing = true;
                    View v = view.getChildAt(0);
                    int top = (v == null) ? 0 : v.getTop();
                    listViewCities.setSelectionFromTop(firstVisibleItem, top);
                    isSyncing = false;
                }
            }
        });

        listViewCities.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isSyncing) {
                    isSyncing = true;
                    View v = view.getChildAt(0);
                    int top = (v == null) ? 0 : v.getTop();
                    listViewNumbers.setSelectionFromTop(firstVisibleItem, top);
                    isSyncing = false;
                }
            }
        });
    }

    private void generateLists() {
        numbers = new ArrayList<>();
        cities = new ArrayList<>();
        for (int i = 1; i <= 81; i++) {
            numbers.add(i);
            cities.add(allCities[i - 1]);
        }

        Collections.shuffle(numbers); // Sadece numaralar karışacak
        // Collections.shuffle(cities); // Şehirler alfabetik/plaka sırasıyla kalacak

        ArrayAdapter<Integer> adapterNumbers = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numbers);
        ArrayAdapter<String> adapterCities = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);

        listViewNumbers.setAdapter(adapterNumbers);
        listViewCities.setAdapter(adapterCities);
    }

    private void checkMatch(int position) {
        int selectedNumber = numbers.get(position);
        String selectedCity = cities.get(position);
        
        // Correct city for the selected plate number
        String correctCity = allCities[selectedNumber - 1];
        
        String message;
        if (selectedCity.equals(correctCity)) {
            message = selectedCity + " ile eşleşti";
        } else {
            message = selectedCity + " ile eşleşmedi";
        }

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
    }
}