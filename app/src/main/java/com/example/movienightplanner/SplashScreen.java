package com.example.movienightplanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.movienightplanner.Database.DataSourceEvents;
import com.example.movienightplanner.Database.DataSourceMovies;
import com.example.movienightplanner.Models.FileInputReader;


// Should integrate threads/ Async tasks here

/*
 * So, If user has granted us all permissions that we need then before launching into the main
 * activity we should use an Async task to load the data from the files and Load the data from
 * the SQLite database
 */

/*
 * Permission checks,
 *  1. if deny all then finish and state that you must grant permission
 *  2. if not every single permission is granted then finish and state that you must grant permission
 *  3. if all permissions are granted then launch main activity
 */

public class SplashScreen extends AppCompatActivity {
    private final String TAG = "CodeRunner";

    private static final int PERMISSION_ALL = 2000;
    private ProgressBar progressBar;                                                                // Has a max value of 100
    private int progressBarUpdateValue = 0;

    DataSourceEvents mDataSourceEvents;
    DataSourceMovies mDataSourceMovies;

    // Put all permissions that you want here and in the manifest too
    String[] PERMISSIONS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        checkingPermissions();
    }

    private void checkingPermissions() {

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            loadData();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        // Need to double check if we have permission in the event the user leaves at least one
        // permission un granted
        if (hasPermissions(this, PERMISSIONS)) {
            loadData();
        } else {
            Toast.makeText(this, "You Must Grant Permission",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    //----------------------------------------------------------------------------------------------

    void loadData() {
        mDataSourceMovies = new DataSourceMovies(this);
        mDataSourceMovies.open();
        mDataSourceMovies.loadDataFromDataBase();

        mDataSourceEvents = new DataSourceEvents(this);
        mDataSourceEvents.open();
        mDataSourceEvents.loadDataFromDataBase();

        progressBar.setVisibility(View.VISIBLE);
        MyTask task = new MyTask();
        task.execute("events.txt", "movies.txt");
    }

    void launchMainActivity() {

        // start the next activity.
        Log.i(TAG, "Starting Main Activity");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        // Finish Splash Activity
        finish();
    }

    // The AsyncTask parameters are
    // Type1 = Input, Type2 = on Progress Update, Type3 = When task complete
    class MyTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {

            for (String file:
                 strings) {
                Log.i(TAG, "Do in Background: " + file);
                FileInputReader.readFile(file);
                publishProgress(25);

                // Simulating large files
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(25);
            }
            return "Threads are all done";
        }

        // Runs on the Main thread so It can make changes to the UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Update on Progress: " + values[0]);
            progressBarUpdateValue += values[0];
            progressBar.setProgress(progressBarUpdateValue, true);
        }

        // This function deals with the return value of doInBackground
        // Runs on the Main thread so It can make changes to the UI
        @Override
        protected void onPostExecute(String  s) {
            Log.i(TAG, s);
            launchMainActivity();
        }
    }
}
