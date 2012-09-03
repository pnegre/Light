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
                setOnTorch();
            }
        });

        Button but2 = (Button) findViewById(R.id.butend);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOffTorch();
            }
        });

        sview = (SurfaceView) findViewById(R.id.surfaceview);
        camDevice = new CamDevice(sview);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    private void setOffTorch() {
        camDevice.ledOff();
    }

    private void setOnTorch() {
        camDevice.ledOn();
    }
}


class CamDevice implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Camera camera;
    boolean flashing = false;

    CamDevice(SurfaceView sv) {
        surfaceHolder = sv.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    void ledOn() {
        try {
            if ((flashing) || (camera != null)) {
                return;
            }
            camera = Camera.open();
            camera.setPreviewDisplay(surfaceHolder);
            Camera.Parameters params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            flashing = true;
        } catch (IOException e) { };
    }

    void ledOff() {
        if ((!flashing) || (camera == null)) {
            return;
        }
        flashing = false;
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);

        camera.stopPreview();
        camera.release();
        camera = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}