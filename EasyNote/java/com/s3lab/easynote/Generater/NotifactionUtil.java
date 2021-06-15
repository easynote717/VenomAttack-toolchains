package com.s3lab.easynote.Generater;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;


public class NotifactionUtil extends Notification {

    private static NotificationManager manager;
    public static final String id = "123";
    public static final String name = "123";
    private static NotificationManager getManager(Context context){
        if (manager == null){
            manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @TargetApi(26)
    public static void createNotificationChannel(Context context){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager(context).createNotificationChannel(channel);
    }

    @TargetApi(26)
    public static Notification.Builder getChannelNotification(Context context,String title, String content){
        return new Notification.Builder(context, id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }
    public static NotificationCompat.Builder getNotification_25(Context context, String title, String content){
        return new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    public static void sendNotification(Context context,String title, String content){
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel(context);
            Notification notification = getChannelNotification
                    (context,title, content).build();
            getManager(context).notify(1,notification);
        }else{
            Notification notification = getNotification_25(context,title, content).build();
            getManager(context).notify(1,notification);
        }
    }

}