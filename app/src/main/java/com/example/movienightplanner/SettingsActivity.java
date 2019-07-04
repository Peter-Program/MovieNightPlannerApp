package com.example.movienightplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "Settings";
    public static String MY_GLOBAL_PREFS = "my_global_prefs";
    public String TEST_KEY = "test_key";

    private EditText threshold;
    private EditText remindAgain;
    private EditText notiPeriod;

    public static String THRESHOLD_DEFAULT = "5";
    public static String REMIND_AGAIN_DEFAULT = "5";
    public static String NOTIFICATION_PERIOD_DEFAULT = "5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Log.i("Test", "ActionBar is not null");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        threshold = findViewById(R.id.editTextNumberThresh);
        remindAgain = findViewById(R.id.editTextRemindAgainDur);
        notiPeriod = findViewById(R.id.editTextNotificationPeri);


        loadPref();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "On Destroy");
        // Saving preferences
        savePref();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    void savePref() {
        SharedPreferences.Editor editor =
                getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE).edit();
        // can put various data types to be stored like a hash, with KEY/VALUE pairs
        editor.putString(getString(R.string.PREF_NOTIFICATION_THRESHOLD), threshold.getText().toString());
        editor.putString(getString(R.string.PREF_REMINDER), remindAgain.getText().toString());
        editor.putString(getString(R.string.PREF_NOTIFICATION_PERIOD), notiPeriod.getText().toString());
        // must apply to commit the data
        editor.apply();
    }

    void loadPref() {
        // Getting the data from SharedPreferences
        SharedPreferences prefs =
                getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE);
        // example of getting a string
        String thresh = prefs.getString(getString(R.string.PREF_NOTIFICATION_THRESHOLD), THRESHOLD_DEFAULT);
        String reminder = prefs.getString(getString(R.string.PREF_REMINDER), REMIND_AGAIN_DEFAULT);
        String notiPeri = prefs.getString(getString(R.string.PREF_NOTIFICATION_PERIOD), NOTIFICATION_PERIOD_DEFAULT);
        threshold.setText(thresh);
        remindAgain.setText(reminder);
        notiPeriod.setText(notiPeri);
    }
}