package com.example.movienightplanner;

import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyJobService extends JobService {
    private static final String TAG = "codeRunner";
    static int JOB_ID = 2500;

    public MyJobService() {
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        // code that runs when starting job

        Log.i(TAG, "onStartJob: " + params.getJobId());


        // Putting the job service on its own thread for Async processing
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // Just for simulation of a task
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // doing network stuff here
                Log.i(TAG, "Run JobService Complete");

                // can use this intent, adding extras to it, to send more meaningful data
                LocalBroadcastManager.getInstance(getApplicationContext())
                        .sendBroadcast(new Intent("Service Message"));

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(getApplicationContext());

                // notificationId is a unique int for each notification that you must define
                // Need to check if we need to notify
                //So check the event distances and if they meet the paramaters then notify else ignore
                notificationManager.notify(100, setupNotification().build());

                // must call this when everything in the job has run ok
                jobFinished(params, false);

                // gives the system a moment so finish the job before refreshing
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                jobRefresh();
            }
        };

        // starting thread
        Thread thread = new Thread(r);
        thread.start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    private void jobRefresh() {
        // Checking if a jobRefresh is already running so that multiple do not occur when
        // network state changes to connected
        if (isJobIdRunning(getApplicationContext(), JOB_ID + 1)) {
            Log.i(TAG, "Job refresh is Already Running");
        } else {
            Log.i(TAG, "Refresh Job in: " +
                    SettingsValuesOfPref.getNotificationPref(getApplicationContext()) * 60 * 1000);
            JobScheduler jobScheduler = (JobScheduler)
                    getSystemService(getApplicationContext().JOB_SCHEDULER_SERVICE);
            JobInfo info = new JobInfo.Builder(JOB_ID + 1,
                    new ComponentName(getApplicationContext(), MyJobService.class))
                    .setMinimumLatency(
                            SettingsValuesOfPref.getNotificationPref(getApplicationContext()) * 60 * 1000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING)
                    // More parameters to set here, like network
                    .build();
            jobScheduler.schedule(info);
        }
    }

    // from https://stackoverflow.com/questions/50483874/how-can-i-know-whether-a-jobscheduler-is-running
    public static boolean isJobIdRunning( Context context, int JobId) {
        final JobScheduler jobScheduler = (JobScheduler)
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) ;

        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == JobId) {
                return true;
            }
        }
        return false;
    }

    private NotificationCompat.Builder setupNotification() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, AddEventInfo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent cancelIntent = new Intent(this, NotificationCancel.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingCancelIntent = PendingIntent.getActivity(this, 0, cancelIntent, 0);

//        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
//        dlgAlert.setMessage("This is an alert with no consequence");
//        dlgAlert.setTitle("App Title");
//        dlgAlert.setPositiveButton("OK", null);
//        dlgAlert.setCancelable(true);
//        dlgAlert.create();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "100")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Event is Near")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                //.setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_notifications_black_24dp, "Cancel", pendingCancelIntent)
                .addAction(R.drawable.ic_notifications_black_24dp, "Dismiss", pendingIntent)
                .addAction(R.drawable.ic_notifications_black_24dp, "remind in " +
                        SettingsValuesOfPref.getRemindAgainPref(this) + " minuets", null);
                //.setAutoCancel(true);

        return builder;
    }
}
