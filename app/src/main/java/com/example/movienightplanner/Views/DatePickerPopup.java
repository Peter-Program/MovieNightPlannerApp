package com.example.movienightplanner.Views;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.movienightplanner.R;

import java.sql.Time;

public class DatePickerPopup extends AppCompatActivity {

    DatePicker datePicker;
    Button setDateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_popup);

        datePicker = findViewById(R.id.datePicker);
        setDateBtn = findViewById(R.id.datePickerSaveBtn);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.75));

        setDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                intent.putExtra(getString(R.string.day), day);
                intent.putExtra(getString(R.string.month), month);
                intent.putExtra(getString(R.string.year), year);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
