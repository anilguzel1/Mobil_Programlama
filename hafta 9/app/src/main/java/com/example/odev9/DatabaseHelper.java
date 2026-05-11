package com.example.odev9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_ADI = "sorular.db";
    private static final int DB_VERSIYON = 1;
    private static final String TABLO = "sorular";

    private static final String KOL_ID = "_id";
    private static final String KOL_SORU = "soru";
    private static final String KOL_A = "secenek_a";
    private static final String KOL_B = "secenek_b";
    private static final String KOL_C = "secenek_c";
    private static final String KOL_D = "secenek_d";
    private static final String KOL_DOGRU = "dogru_cevap";

    public DatabaseHelper(Context context) {
        super(context, DB_ADI, null, DB_VERSIYON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLO + " (" +
                KOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KOL_SORU + " TEXT NOT NULL, " +
                KOL_A + " TEXT NOT NULL, " +
                KOL_B + " TEXT NOT NULL, " +
                KOL_C + " TEXT NOT NULL, " +
                KOL_D + " TEXT NOT NULL, " +
                KOL_DOGRU + " INTEGER NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int eskiVersiyon, int yeniVersiyon) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLO);
        onCreate(db);
    }

    public boolean soruEkle(String soru, String a, String b, String c, String d, int dogru) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KOL_SORU, soru);
        values.put(KOL_A, a);
        values.put(KOL_B, b);
        values.put(KOL_C, c);
        values.put(KOL_D, d);
        values.put(KOL_DOGRU, dogru);
        long sonuc = db.insert(TABLO, null, values);
        db.close();
        return sonuc != -1;
    }

    public List<Soru> tumSorulariGetir() {
        List<Soru> liste = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLO, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Soru s = new Soru(
                        cursor.getInt(cursor.getColumnIndexOrThrow(KOL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KOL_SORU)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KOL_A)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KOL_B)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KOL_C)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KOL_D)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KOL_DOGRU))
                );
                liste.add(s);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return liste;
    }

    public int soruSayisi() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLO, null);
        int sayi = 0;
        if (cursor.moveToFirst()) sayi = cursor.getInt(0);
        cursor.close();
        db.close();
        return sayi;
    }
}
