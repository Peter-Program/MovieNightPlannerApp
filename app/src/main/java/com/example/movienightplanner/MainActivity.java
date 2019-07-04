package com.example.movienightplanner;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienightplanner.Database.DataSourceEvents;
import com.example.movienightplanner.Database.DataSourceMovies;
import com.example.movienightplanner.Views.DisplayEventsList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends BaseActivity {
    String TAG = "codeRunner";
    int ADD_EVENT_REQUEST = 1000;


    Button viewEventsListBtn;
    Button nextWeek;
    Button prevWeek;
    Button viewMap;
    TextView currentDay;
    FloatingActionButton floatingActionButton;
    Calendar calendar = Calendar.getInstance();
    Date currentDate = calendar.getTime();
    LinearLayout weekLayout;

    DataSourceEvents mDataSourceEvents;
    DataSourceMovies mDataSourceMovies;

//    BroadcastReceiver mReciver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.i(TAG, "job service message recived");
//        }
//    };

    BroadcastReceiver br;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataSourceEvents = new DataSourceEvents(this);
        mDataSourceEvents.open();

        mDataSourceMovies = new DataSourceMovies(this);
        mDataSourceMovies.open();

        //runJob();

        br = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(br, filter);

        viewEventsListBtn = findViewById(R.id.viewEventsListBtn);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        nextWeek = findViewById(R.id.nextWeekBtn);
        prevWeek = findViewById(R.id.prevWeekBtn);
        currentDay = findViewById(R.id.displayCurrDayText);
        weekLayout = findViewById(R.id.calendar_week_Events);
        viewMap = findViewById(R.id.viewMapBtn);

        currentDay.setText(String.format("%d", calendar.get(Calendar.DAY_OF_MONTH)));

        setWeek(weekLayout, getdaysLayoutParams());

        //final Intent intent = new Intent(this, CalenderTest.class);

//        imageView.setOnTouchListener(new MyTouchListener(this) {
//            @Override
//            public void onTap(){
//                //startActivity(intent);
//                //startActivityForResult(addEventInfoIntent, SET_TIME_REQUEST);
//                //Event e = new Event("8", "test", "2/01/2019 1:00:00 AM", "end", "venue", "Loc");
//
//            }
//        });

        viewEventsListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayEventsList.class);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEventInfoIntent = new Intent(v.getContext(), AddEventInfo.class);
                addEventInfoIntent.putExtra(getString(R.string.new_Event), true);
                startActivityForResult(addEventInfoIntent, ADD_EVENT_REQUEST);
            }
        });

        nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Next Week",
                        Toast.LENGTH_SHORT).show();
            }
        });

        prevWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Prev Week",
                        Toast.LENGTH_SHORT).show();
            }
        });

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "View Map",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        LocalBroadcastManager.getInstance(getApplicationContext())
//                .registerReceiver(mReciver, new IntentFilter("Service message"));
    }

    int getStartDayOfWeek() {
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
        return dayInt;
    }

    void setWeek(LinearLayout view, LinearLayout.LayoutParams buttonParams) {
        int dayValue = getStartDayOfWeek();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        for (int i = 0; i < 7; i++) {
            final Button day = new Button(this);
            if ((dayValue + i) == calendar.get(Calendar.DAY_OF_MONTH))
                day.setTextColor(getColor(R.color.colorBlue));
            else
                day.setTextColor(getColor(R.color.colorLightGrey));
            day.setLayoutParams(buttonParams);
            day.setBackgroundResource(R.drawable.solid_boarder);
            day.setText(String.format("%d", dayValue + i));
            day.setTextSize((int) metrics.density * 8);
            day.setSingleLine();
            day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked Day",
                            Toast.LENGTH_SHORT).show();
                }
            });
            view.addView(day);
        }
    }

    private LinearLayout.LayoutParams getdaysLayoutParams() {
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1;
        return buttonParams;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EVENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "Added Event");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSourceEvents.saveDataToDataBase();
        mDataSourceMovies.saveDataToDataBase();
        mDataSourceMovies.close();
        mDataSourceEvents.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSourceEvents.open();
        mDataSourceMovies.open();
    }

    private void runJob() {
        JobScheduler jobScheduler = (JobScheduler)
                getSystemService(getApplicationContext().JOB_SCHEDULER_SERVICE);
        JobInfo info = new JobInfo.Builder(MyJobService.JOB_ID,
                new ComponentName(this, MyJobService.class))
                .setMinimumLatency(0)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING)
                // More parameters to set here, like network
                .build();
        jobScheduler.schedule(info);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReciver);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(br);
        Log.i(TAG, "Destroying app");
        super.onDestroy();

    }
}