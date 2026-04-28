package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Integer> shuffledNumbers;
    private ArrayAdapter<Integer> adapterNumbers;
    private String[] citiesArray = {
            "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin",
            "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa",
            "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan",
            "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta",
            "Mersin", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir",
            "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla",
            "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt",
            "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak",
            "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman",
            "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewNumbers = findViewById(R.id.listview_numbers);
        ListView listViewCities = findViewById(R.id.listview_cities);
        Button buttonChange = findViewById(R.id.button_change);

        shuffledNumbers = new ArrayList<>();
        for (int i = 1; i <= 81; i++) {
            shuffledNumbers.add(i);
        }
        Collections.shuffle(shuffledNumbers);

        adapterNumbers = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shuffledNumbers);
        ArrayAdapter<String> adapterCities = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, citiesArray);

        listViewNumbers.setAdapter(adapterNumbers);
        listViewCities.setAdapter(adapterCities);

        // Senkronize kaydırma işlemi
        listViewNumbers.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getChildAt(0) != null) {
                    int top = view.getChildAt(0).getTop();
                    listViewCities.setSelectionFromTop(firstVisibleItem, top);
                }
            }
        });

        listViewCities.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getChildAt(0) != null) {
                    int top = view.getChildAt(0).getTop();
                    listViewNumbers.setSelectionFromTop(firstVisibleItem, top);
                }
            }
        });

        buttonChange.setOnClickListener(v -> {
            Collections.shuffle(shuffledNumbers);
            adapterNumbers.notifyDataSetChanged();
        });

        listViewCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = citiesArray[position];
                int actualPlateCode = position + 1;
                int displayedPlateCode = shuffledNumbers.get(position);

                boolean isMatched = (actualPlateCode == displayedPlateCode);

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("CITY_NAME", cityName);
                intent.putExtra("IS_MATCHED", isMatched);
                startActivity(intent);
            }
        });
    }
}