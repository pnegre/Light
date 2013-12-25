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

    CamDevice(SurfaceView sv) {
        surfaceHolder = sv.getHolder();
        surfaceHolder.addCallback(this);
        init();
    }

    void init() {

    }

    void shutoff() {
        if (camera == null) {
            return;
        }
        ledOff();
        camera.release();
        camera = null;
    }

    void ledOn() {
        if (camera != null) {
            Camera.Parameters params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
        }
    }

    void ledOff() {
        if (camera == null) {
            return;
        }
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
    }

    private int getCameraid() {
        Camera.CameraInfo cinfo = new Camera.CameraInfo();
        int ccount = Camera.getNumberOfCameras();
        for (int i = 0; i < ccount; i++) {
            Camera.getCameraInfo(i, cinfo);
            if (cinfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
                return i;
        }
        throw new RuntimeException("Back camera not present");
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            if (camera == null) {
                camera = Camera.open(getCameraid());
            }
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (camera != null) {
            shutoff();
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }
}