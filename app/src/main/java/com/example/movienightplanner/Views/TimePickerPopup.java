package com.example.movienightplanner.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.movienightplanner.BaseActivity;
import com.example.movienightplanner.R;

public class TimePickerPopup extends BaseActivity {

    TimePicker timePicker;
    Button setTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker_popup);

        timePicker = findViewById(R.id.timePicker);
        setTimeBtn = findViewById(R.id.setTimeBtn);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.75));

        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                // Hour is in military time standard
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                intent.putExtra(getString(R.string.timeHour), hour);
                intent.putExtra(getString(R.string.timeMinute), minute);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
