package com.example.safesms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private static MainActivity ins;


    private static final int MY_PERMISSIONS_REQUEST_RECIEVE_SMS = 0;


    public static MainActivity getInst()
    {
        return ins;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ins = this;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS))
            {
                ;
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECIEVE_SMS);
            }
        }
    }

    public void updateUI(final String s)
    {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView  textView=(TextView) findViewById(R.id.textView);
                textView.setText(s);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],  int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_RECIEVE_SMS:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "SMS izin verdiginiz icin tesekkurler", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "SMS izin vermelisiniz", Toast.LENGTH_LONG).show();
                }
            }
        }
    }




}