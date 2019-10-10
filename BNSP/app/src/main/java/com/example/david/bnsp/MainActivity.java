package com.example.david.bnsp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.david.bnsp.helper.DataHelper;

public class MainActivity extends AppCompatActivity {
    String[] daftar;
    ListView daftarKegiatan;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbCenter;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTambah = (Button)findViewById(R.id.btnTambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TambahKegiatan.class);
                startActivity(i);
            }
        });

        ma = this;
        dbCenter = new DataHelper(this);
        RefreshList();
    }

    public void RefreshList() {
        SQLiteDatabase db = dbCenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM kegiatan", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        daftarKegiatan = findViewById(R.id.listKegiatan);
        daftarKegiatan.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        daftarKegiatan.setSelected(true);
        daftarKegiatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                final CharSequence[] dialogItem = {"Detail"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("PIlihan");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent i = new Intent(getApplicationContext(), DetailKegiatan.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)daftarKegiatan.getAdapter()).notifyDataSetInvalidated();
    }
}
