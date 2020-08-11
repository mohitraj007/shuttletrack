package com.project.mohit.shuttletrackrider.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.project.mohit.shuttletrackrider.Helper.NotificationHelper;
import com.project.mohit.shuttletrackrider.R;

import java.util.Map;
import java.util.logging.Handler;

/**
 * Created by Mohit on 12-03-2018.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage)
    {
        //handler to run toast
        if (remoteMessage.getData() != null)
        {
            Map<String , String> data = remoteMessage.getData();
            String title = data.get("title");
            final String message = data.get("message");
            if (title.equals("Cancel"))
            {
                android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(MyFirebaseMessaging.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (title.equals("Arrived"))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    showArrivedNotificationAPI26(message);
                } else
                    {
                    showArrivedNotification(message);
                }
            }
        }
    }

    private void showArrivedNotificationAPI26(String body) {
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),
                0,new Intent(),PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationHelper notificationHelper = new NotificationHelper(getBaseContext());
        Notification.Builder builder = notificationHelper.getShuttleTrackNotification("Arrived",body,contentIntent,defaultSound);

        notificationHelper.getManager().notify(1,builder.build());

    }

    private void showArrivedNotification(String body) {
        //only works for API 25 & below
        //for 26+, need to create a notification channel
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),
                0,new Intent(),PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_car)
                .setContentTitle("Arrived")
                .setContentText(body)
                .setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }
}

