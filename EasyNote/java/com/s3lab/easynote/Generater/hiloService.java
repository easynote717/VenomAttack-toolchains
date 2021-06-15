package com.s3lab.easynote.Generater;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class hiloService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){

        return super.onStartCommand(intent, flags, startID);
    }

    @Override
    public void onCreate(){
        open();
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void open(){
        Intent intent = new Intent(this, Additional.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}
