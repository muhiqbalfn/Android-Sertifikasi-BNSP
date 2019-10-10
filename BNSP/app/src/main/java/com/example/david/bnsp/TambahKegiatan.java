package com.example.david.bnsp;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.david.bnsp.helper.DataHelper;

import java.util.Calendar;

public class TambahKegiatan extends AppCompatActivity {
    Cursor cursor;
    DataHelper dbHelper;
    Button btnAdd, btnDone, btnBack;
    EditText addNomor, addNama, addKeterangan, addWaktu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan);

        dbHelper = new DataHelper(this);
        addNomor = findViewById(R.id.editNomor);
        addNama = findViewById(R.id.editNama);
        addKeterangan = findViewById(R.id.editKeterangan);
        addWaktu = findViewById(R.id.editWaktu);
        btnAdd = findViewById(R.id.btnAdd);
        btnDone = findViewById(R.id.btnSelesai);
        btnBack = findViewById(R.id.btnKembali);

        addWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TambahKegiatan.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        addWaktu.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Pilih Waktu");
                mTimePicker.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("INSERT INTO kegiatan (nomor, nama, keterangan, waktu) VALUES ('" + addNomor.getText().toString() + "', '" + addNama.getText().toString() + "', '" + addKeterangan.getText().toString() + "', '" + addWaktu.getText().toString() + "')");
                Toast.makeText(getApplicationContext(), "Berhasil Menambah Kegiatan", Toast.LENGTH_LONG).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });

        btnDone.setEnabled(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
