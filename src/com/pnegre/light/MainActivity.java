package com.pnegre.light;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends Activity
{

    private CamDevice camDevice;
    SurfaceView sview;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button but1 = (Button) findViewById(R.id.butstart);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camDevice.ledOn();
            }
        });

        Button but2 = (Button) findViewById(R.id.butend);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camDevice.ledOff();
            }
        });


    }

    public void onResume() {
        sview = (SurfaceView) findViewById(R.id.surfaceview);
        camDevice = new CamDevice(sview);
        super.onResume();
    }

    public void onPause() {
        camDevice.ledOff();
        super.onPause();
    }
}

