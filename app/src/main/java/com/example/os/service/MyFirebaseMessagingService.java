package com.example.os.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.os.R;
import com.example.os.activitys.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";

    private static final String JSON_KEY_AUTHOR = CloudContract.COLUMN_AUTHOR;
    private static final String JSON_KEY_AUTHOR_KEY = CloudContract.COLUMN_AUTHOR_KEY;
    private static final String JSON_KEY_MESSAGE = CloudContract.COLUMN_MESSAGE;
    private static final String JSON_KEY_DATE = CloudContract.COLUMN_DATE;


    private static final int NOTIFICATION_MAX_CHARACTERS = 30;
    private static final String CHANNELID = "notify_001";


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: ");
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String,String> data = remoteMessage.getData();
        if(data.size() > 0){
            Log.d(TAG, "onMessageReceived: ");
            sendNotification(data);
        }
    }

    private void sendNotification(Map<String, String> data) {

        Log.d(TAG, "sendNotification: "+data.get(JSON_KEY_MESSAGE));
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String author = data.get(JSON_KEY_AUTHOR);
        String message = data.get(JSON_KEY_MESSAGE);
        if (message.length() > NOTIFICATION_MAX_CHARACTERS) {
            message = message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026";
        }
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CHANNELID)
                .setSmallIcon(R.drawable.ic_push_notifcation)
                .setContentTitle(String.format(getString(R.string.notification_message), author))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }

        notificationManager.notify(0,notificationBuilder.build());
    }
}
