package com.s3lab.easynote.AutoConnectServer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, connectService.class);
        context.startService(intent1);
    }
}
