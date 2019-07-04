package com.example.movienightplanner;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalenderTest extends AppCompatActivity {

    TextView textView;

    RecyclerView recyclerViewMon;
    RecyclerView recyclerViewTue;
    RecyclerView recyclerViewWed;
    RecyclerView recyclerViewThu;
    RecyclerView recyclerViewFri;
    RecyclerView recyclerViewSat;
    RecyclerView recyclerViewSun;
    private RecyclerAdapterEvent adapter;

    Calendar calendar = Calendar.getInstance();
    Date currentDate = calendar.getTime();
    SimpleDateFormat day = new SimpleDateFormat("dd");
    String d = day.format(currentDate);

    String startDayOfTheWeek;
    EventDateTime eventDateTime = new EventDateTime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_test);

        recyclerViewMon = findViewById(R.id.recyclerViewMon);
        recyclerViewTue = findViewById(R.id.recyclerViewTue);
        recyclerViewWed = findViewById(R.id.recyclerViewWed);
        recyclerViewThu = findViewById(R.id.recyclerViewThu);
        recyclerViewFri = findViewById(R.id.recyclerViewFri);
        recyclerViewSat = findViewById(R.id.recyclerViewSat);
        recyclerViewSun = findViewById(R.id.recyclerViewSun);

        textView = findViewById(R.id.textView9);
        textView.setText(d);
        setStartDayOfTheWeek();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

//        recyclerViewMon.setHasFixedSize(true);
//        recyclerViewMon.setLayoutManager(layoutManager);
//
//        recyclerViewTue.setHasFixedSize(true);
//        recyclerViewTue.setLayoutManager(layoutManager);
//
//        recyclerViewWed.setHasFixedSize(true);
//        recyclerViewWed.setLayoutManager(layoutManager);
//
//        recyclerViewThu.setHasFixedSize(true);
//        recyclerViewThu.setLayoutManager(layoutManager);
//
//        recyclerViewFri.setHasFixedSize(true);
//        recyclerViewFri.setLayoutManager(layoutManager);
//
//        recyclerViewSat.setHasFixedSize(true);
//        recyclerViewSat.setLayoutManager(layoutManager);
//
//        recyclerViewSun.setHasFixedSize(true);
//        recyclerViewSun.setLayoutManager(layoutManager);

    }

    void setStartDayOfTheWeek() {
        SimpleDateFormat dayOfTheWeek = new SimpleDateFormat("E");
        SimpleDateFormat day = new SimpleDateFormat("dd");

        String dayS = day.format(currentDate);
        Integer dayInt = Integer.parseInt(dayS);
        Log.i("Calender", dayOfTheWeek.format(currentDate));
        switch (dayOfTheWeek.format(currentDate)) {
            case "Mon":
                break;
            case "Tue":
                dayInt -= 1;
                break;
            case "Wed":
                dayInt -= 2;
                break;
            case "Thu":
                dayInt -= 3;
                break;
            case "Fri":
                dayInt -= 4;
                break;
            case "Sat":
                dayInt -= 5;
                break;
            case "Sun":
                dayInt -= 6;
                break;
                default:
        }
        textView.setText("Monday was: " + dayInt);
        eventDateTime.setDay(dayInt);
    }
}
