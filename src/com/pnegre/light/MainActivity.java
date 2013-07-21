package com.pnegre.light;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity
{
    private CamDevice camDevice;
    private SurfaceView sview;
    private ImageView imageView;
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

        imageView = (ImageView) findViewById(R.id.theimage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOn)
                    on();
                else
                    off();
            }
        });
    }

    void on() {
        camDevice.ledOn();
        imageView.setImageResource(R.drawable.lb_on);
        isOn = true;
    }

    void off() {
        camDevice.ledOff();
        imageView.setImageResource(R.drawable.lb_off);
        isOn = false;
    }

    public void onResume() {
        super.onResume();
        sview = (SurfaceView) findViewById(R.id.surfaceview);
        camDevice = new CamDevice(sview);
        camDevice.init();
        isOn = false;
        wakeLock.acquire();
    }

    public void onPause() {
        off();
        wakeLock.release();
        camDevice.shutoff();
        super.onPause();
    }
}

