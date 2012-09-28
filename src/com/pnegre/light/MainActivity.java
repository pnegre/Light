package com.pnegre.light;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity
{

    private CamDevice camDevice;
    private SurfaceView sview;
    private boolean isOn = false;

    private PowerManager pmanager;
    private PowerManager.WakeLock wakeLock;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        pmanager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock =  pmanager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Light");

        Button but1 = (Button) findViewById(R.id.butstart);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOn)
                    camDevice.ledOn();
                else
                    camDevice.ledOff();

                isOn = !isOn;
            }
        });

    }

    public void onResume() {
        sview = (SurfaceView) findViewById(R.id.surfaceview);
        camDevice = new CamDevice(sview);
        isOn = false;
        super.onResume();
        wakeLock.acquire();
    }

    public void onPause() {
        camDevice.ledOff();
        wakeLock.release();
        super.onPause();
    }
}

