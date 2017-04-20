package com.arakelyan.hovsep.androidserviceusingaidl.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.arakelyan.hovsep.androidserviceusingaidl.FileInfo;
import com.arakelyan.hovsep.androidserviceusingaidl.IMainService;
import com.arakelyan.hovsep.androidserviceusingaidl.db.DBHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Hovsep
 */
public class MainService extends Service {

    private void log(String message) {
        Log.v("MainService", message);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("********", "Received start command.");
        callAsynchronousTask();
        startForeground(startId, new Notification());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        log("Received binding.");
        return mBinder;
    }

    private final IMainService.Stub mBinder = new IMainService.Stub() {
        @Override
        public FileInfo[] listFiles() throws RemoteException {
            final DBHelper dbHelper = new DBHelper(getBaseContext());
            List<FileInfo> toSend = dbHelper.getAllFeeds();
            return toSend.toArray(new FileInfo[toSend.size()]);
        }

        @Override
        public void setCountOfClick(int itemId, int newCount) throws RemoteException {
            log("Received exit command.");
            //  stopSelf();

            DBHelper dbHelper = new DBHelper(getApplicationContext());
            dbHelper.updateClickCount(itemId, newCount);
        }
    };

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        final DBHelper dbHelper = new DBHelper(getBaseContext());
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            String path = Environment.getExternalStorageDirectory().toString() + "/Download";
                            File directory = new File(path);
                            File[] files = directory.listFiles();
                            List<FileInfo> fileInfos = new ArrayList<>();
                            for (int i = 0; i < files.length; i++) {
                                Log.e("RRRR", "RRRRR" + files[i].getName());
                                FileInfo fileInfo = new FileInfo(files[i].getName(), files[i].getPath(), "0", new Date().toString());
                                fileInfos.add(fileInfo);
                            }

                            dbHelper.saveFileInfo(fileInfos);
                            Toast.makeText(MainService.this, "UPDATED", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 3 * 60 * 1000); //execute in every 3 min
    }
}
