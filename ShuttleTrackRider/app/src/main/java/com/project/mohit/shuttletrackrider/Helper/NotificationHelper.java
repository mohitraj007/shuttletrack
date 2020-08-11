package com.project.mohit.shuttletrackrider.Helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.project.mohit.shuttletrackrider.R;

//import com.project.mohit.shuttletrackrider.Model.Notification;

public class NotificationHelper extends ContextWrapper
{

    private static final String SHUTTLE_CHANNEL_ID = "com.project.mohit.shuttletrackrider.NOTIF";
    private static final String SHUTTLE_CHANNEL_NAME = "NOTIF Shuttle";

    private NotificationManager manager;

    public NotificationHelper(Context base)
    {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannels();
    }

    @RequiresApi (api = Build.VERSION_CODES.O)
    private void createChannels()
    {
        NotificationChannel shuttleTrackChannels = new NotificationChannel(SHUTTLE_CHANNEL_ID, SHUTTLE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        shuttleTrackChannels.enableLights(true);
        shuttleTrackChannels.enableVibration(true);
        shuttleTrackChannels.setLightColor(Color.GRAY);
        shuttleTrackChannels.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);//could have error
        
        getManager().createNotificationChannel(shuttleTrackChannels);
    }

    public NotificationManager getManager()
    {
        if (manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public Notification.Builder getShuttleTrackNotification(String title, String content, PendingIntent contentIntent, Uri soundUri)
    {
        return new Notification.Builder(getApplicationContext(),SHUTTLE_CHANNEL_ID)
                .setContentText(content)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_car);
    }

}
