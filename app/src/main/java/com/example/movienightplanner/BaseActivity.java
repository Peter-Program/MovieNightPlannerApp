package com.example.movienightplanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


/*
 * By creating this BaseActivity and having my other activities extend this one, I can have a menu
 * bar that stays consistent across all the activates that have a menu bar and are not applying
 * a no Action bar them from the styles xml in values
 */

public class BaseActivity extends AppCompatActivity {



    // App bar menu methods start-------------------------------------------------------------------
    // Menu item list is in res/menu/menu_main_activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);                                          // Initializing the app bar menu layout/style
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuMainSettings:                                                             // When clicking on Settings
                Toast.makeText(this, "Clicked Settings",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                // user clicked the back button
                Toast.makeText(this, "Clicked Back",
                        Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // App bar menu methods end---------------------------------------------------------------------
}
