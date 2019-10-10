package com.example.david.bnsp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "daftarkegiatan.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE kegiatan(nomor INTEGER PRIMARY KEY, nama TEXT, keterangan, waktu TEXT);";
        db.execSQL(sql);
        sql = "INSERT INTO kegiatan (nomor, nama, keterangan, waktu) VALUES ('1', 'Sahur', 'Makan pagi sebelum puasa', '03:00');";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
