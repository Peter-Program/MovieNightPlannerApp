package com.example.movienightplanner;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsValuesOfPref {

    public static int getNotificationPref(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(
                SettingsActivity.MY_GLOBAL_PREFS, Context.MODE_PRIVATE);
        String noti =
                prefs.getString(context.getString(R.string.PREF_NOTIFICATION_PERIOD),
                        SettingsActivity.NOTIFICATION_PERIOD_DEFAULT);
        int notification = Integer.parseInt(noti);

        return notification;
    }

    public static int getThresholdPref(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(SettingsActivity.MY_GLOBAL_PREFS,
                Context.MODE_PRIVATE);
        String thresh =
                prefs.getString(context.getString(R.string.PREF_NOTIFICATION_THRESHOLD),
                        SettingsActivity.THRESHOLD_DEFAULT);
        int threshold = Integer.parseInt(thresh);

        return threshold;
    }

    public static int getRemindAgainPref(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(SettingsActivity.MY_GLOBAL_PREFS,
                Context.MODE_PRIVATE);
        String remind =
                prefs.getString(context.getString(R.string.PREF_REMINDER),
                        SettingsActivity.REMIND_AGAIN_DEFAULT);
        int reminder = Integer.parseInt(remind);

        return reminder;
    }
}
