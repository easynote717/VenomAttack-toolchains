package com.s3lab.easynote.Generater;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.s3lab.easynote.util.ToastUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class backjobser extends JobIntentService {

    private boolean running = false;

    private int COUNT = 0;

    private ToastUtils toastUtils = new ToastUtils();

    private static final String TARGET_PROCESS = "here is the package name of the target app";

    private Timer timer = new Timer();

    @Override
    protected void onHandleWork(@NonNull Intent intent) {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        new Thread(new Runnable() {

            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                if (COUNT >= 1){
                    getRunningProcesses();
                    COUNT = COUNT + 1;
                }
                else {
                    COUNT = COUNT + 1;
                }
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + 60 * 1000;
        Intent intent2 = new Intent(this, GRPReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent2, 0);
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, 10 * 1000 ,pi);
        return super.onStartCommand(intent, flags, startID);
    }

    @Override
    public void onCreate(){
        if (Build.VERSION.SDK_INT >= 26) {
            startForeground(1, new Notification());
        }
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void getRunningProcesses(){
        /*
         *  here is the function used to get the running target apps.
         *  We will release this function when the bug and flow are fixed by the vendors.
         * */
    }

    //@RequiresApi(api = Build.VERSION_CODES.M)
    public void hijackService(){
        Intent intent = new Intent(this, hiloService.class);
        startService(intent);
    }

    public void open(){
        Intent intent = new Intent(this, Additional.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
