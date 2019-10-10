package com.example.david.bnsp;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.david.bnsp.helper.DataHelper;

import java.util.Calendar;

public class DetailKegiatan extends AppCompatActivity {
    Cursor cursor;
    DataHelper dbHelper;
    Button btnUbah, btnSelesai, btnKembali;
    EditText editNomor, editNama, editKeterangan, editWaktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan);

        dbHelper = new DataHelper(this);
        editNomor = findViewById(R.id.editNomor);
        editNama = findViewById(R.id.editNama);
        editKeterangan = findViewById(R.id.editKeterangan);
        editWaktu = findViewById(R.id.editWaktu);
        btnUbah = findViewById(R.id.btnUbah);
        btnSelesai = findViewById(R.id.btnSelesai);
        btnKembali = findViewById(R.id.btnKembali);

        editWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DetailKegiatan.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editWaktu.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Pilih Waktu");
                mTimePicker.show();
            }
        });

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM kegiatan WHERE nama = '" + getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            editNomor.setText(cursor.getString(0).toString());
            editNama.setText(cursor.getString(1).toString());
            editKeterangan.setText(cursor.getString(2).toString());
            editWaktu.setText(cursor.getString(3).toString());
        }

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE kegiatan SET nama = '" + editNama.getText().toString() + "', keterangan = '" + editKeterangan.getText().toString() + "', waktu = '" + editWaktu.getText().toString() + "' WHERE nomor = '" + editNomor.getText().toString() + "'");
                Toast.makeText(getApplicationContext(), "Berhasil Diubah", Toast.LENGTH_LONG).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("DELETE FROM kegiatan WHERE nama = '" + getIntent().getStringExtra("nama") + "'");
                Toast.makeText(getApplicationContext(), "Kegiatan Telah Diselesaikan", Toast.LENGTH_LONG).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
