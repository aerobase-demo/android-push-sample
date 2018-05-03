package io.aerobase.push.demo;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.FirebaseApp;

import org.aerogear.mobile.core.MobileCore;
import org.aerogear.mobile.push.MessageHandler;
import org.aerogear.mobile.push.PushService;

import java.util.Map;

public class PushApplication extends Application implements MessageHandler {
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 6578;
    private static final String CHANNEL_ID = "Aerobase-Test-Channel";

    @Override
    public void onCreate() {
        super.onCreate();

        PushService.registerMainThreadHandler(this);
        PushService.unregisterBackgroundThreadHandler(this);

        createChannel();
    }


    @Override
    public void onMessage(Context context, Map<String, String> messageParts) {
        MobileCore.getLogger().info("AB", "Aerobae Message received");

        final String title = "Aerobase Push";
        final String message = messageParts.get("alert");

        showNotifications(title, message);
    }

    private void showNotifications(String title, String msg) {
        Intent i = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE,
                i, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText(msg)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }

    private void createChannel () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            CharSequence name = "Aerobase-Test-Channel";
            String description = "Aerobase-Test-Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }
    }

}
