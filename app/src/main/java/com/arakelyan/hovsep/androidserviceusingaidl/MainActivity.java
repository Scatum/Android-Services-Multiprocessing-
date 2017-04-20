package com.arakelyan.hovsep.androidserviceusingaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.arakelyan.hovsep.androidserviceusingaidl.adapter.InfoAdapter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements InfoAdapter.OnItemClickListener {

    private IMainService mService;
    RecyclerView recyclerView;
    InfoAdapter infoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent = new Intent("com.arakelyan.hovsep.androidserviceusingaidl.service.MainService");
        Toast.makeText(this, "Starting service…", Toast.LENGTH_SHORT).show();
        startService(serviceIntent);
        Toast.makeText(this, "Binding service…", Toast.LENGTH_SHORT).show();
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        infoAdapter = new InfoAdapter(this, this);
        recyclerView.setAdapter(infoAdapter);


    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Toast.makeText(MainActivity.this, "Service binded!", Toast.LENGTH_SHORT).show();
            mService = IMainService.Stub.asInterface(service);
            performListing();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };

    private void performListing() {
        Toast.makeText(this, "Requesting file listing…", Toast.LENGTH_SHORT).show();
        try {
            FileInfo[] results = mService.listFiles();
            infoAdapter.swapData(Arrays.asList(results));

        } catch (RemoteException e) {
            e.printStackTrace();
        }
       /* try {
            mService.exit();
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onclickItem(int position, FileInfo fileInfo) {
        try {
            mService.setCountOfClick(fileInfo.getId(), 1 + Integer.parseInt(fileInfo.getClickedCount()));
            infoAdapter.notifyDataSetChanged();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
