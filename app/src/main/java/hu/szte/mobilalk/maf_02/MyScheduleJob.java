package hu.szte.mobilalk.maf_02;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class MyScheduleJob extends JobService {
    private static final String CHANNEL_ID = "hu.szte.mobilalk.maf_02.CUSTOM_CHANNEL";

    @Override
    public boolean onStartJob(JobParameters params) {
        String channelName = null;

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this,
                        0,
                        new Intent(this, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelName = "my scheduler channel";
            String description = "this is our channel!";
            int importance = NotificationManager.IMPORTANCE_MIN;
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(description);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this,
                        channelName != null ? channelName.toString() : null)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("MAF_02")
                        .setContentText(this.getClass().getName())
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notifcationManager = NotificationManagerCompat.from(this);
        notifcationManager.notify(0, builder.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
