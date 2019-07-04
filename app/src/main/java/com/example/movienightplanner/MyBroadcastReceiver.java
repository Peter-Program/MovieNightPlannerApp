package com.example.movienightplanner;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("codeRunner", "Receiver Action: " + intent.getAction());

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected)
            runJob(context);
    }

    private void runJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler)
                context.getSystemService(context.getApplicationContext().JOB_SCHEDULER_SERVICE);
        JobInfo info = new JobInfo.Builder(MyJobService.JOB_ID,
                new ComponentName(context, MyJobService.class))
                .setMinimumLatency(0)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING)
                // More parameters to set here, like network
                .build();
        jobScheduler.schedule(info);
    }
}
