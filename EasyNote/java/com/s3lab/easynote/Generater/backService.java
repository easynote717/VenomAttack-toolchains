package com.s3lab.easynote.Generater;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import com.s3lab.easynote.util.ToastUtils;
import java.util.Timer;

public class backService extends Service {

    private boolean running = false;

    private int COUNT = 0;

    private ToastUtils toastUtils = new ToastUtils();
    private static final String TARGET_PROCESS = "here is the package name of the target app";

    private Timer timer = new Timer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRunningProcesses();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + 60 * 1000;
        Intent intent2 = new Intent(this, GRPReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent2, 0);
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, 100 * 1000 ,pi);
        return super.onStartCommand(intent, flags, startID);
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true); //true will remove notification
        }
        super.onDestroy();
    }

    public void getRunningProcesses(){
        /*
         *  here is the function used to get the running target apps.
         *  We will release this function when the bug and flow are fixed by the vendors.
         * */
    }

    public void open(){
        Intent intent = new Intent(this, Additional.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
