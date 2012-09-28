package com.pnegre.light;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * User: pnegre
 * Date: 04/09/12
 * Time: 01:07
 */

class CamDevice implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private boolean flashing = false;

    CamDevice(SurfaceView sv) {
        surfaceHolder = sv.getHolder();
        surfaceHolder.addCallback(this);
    }

    void ledOn() {
        try {
            if (camera != null) {
                return;
            }
            camera = Camera.open(getCameraid());
            camera.setPreviewDisplay(surfaceHolder);
            Camera.Parameters params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            flashing = true;
        } catch (IOException e) {

            if (camera != null) {
                camera.stopPreview();
                camera.release();
            }
            camera = null;

        }
    }

    void ledOff() {
        if (camera == null) {
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

    private int getCameraid() {
        Camera.CameraInfo cinfo = new Camera.CameraInfo();
        int ccount = Camera.getNumberOfCameras();
        for (int i=0; i<ccount; i++) {
            Camera.getCameraInfo(i, cinfo);
            if (cinfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
                return i;
        }
        throw new RuntimeException("Back camera not present");
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