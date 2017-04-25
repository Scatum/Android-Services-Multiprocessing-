package com.arakelyan.hovsep.androidserviceusingaidl.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.arakelyan.hovsep.androidserviceusingaidl.R;


public class SActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MY_PERMISSIONS = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startListActivity();

    }

    @Override
    public void onClick(View v) {

    }

    void startListActivity() {
        startActivity(new Intent(SActivity.this, MainActivity.class));
        finish();
    }


}
