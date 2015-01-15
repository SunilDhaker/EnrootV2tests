package com.enrootapp.v2.cameratest.glCam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.enrootapp.v2.cameratest.R;
import com.enrootapp.v2.cameratest.glCam.util.Logger;
import com.enrootapp.v2.cameratest.glCam.util.MyData;
import com.enrootapp.v2.cameratest.glCam.util.SensorsActivity;

/**
 * Created by sdhaker on 10-01-2015.
 */
public class CameraActivity extends SensorsActivity {
    public static String TAG = "Enroot";
    LinearLayout selfieLayout;
    LayoutInflater layoutInflater;
    private MyGLSurfaceView glSurfaceView;
    private MyCamera mCamera;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCamera = new MyCamera();
        //glSurfaceView = new MyGLSurfaceView(this, mCamera);
        MyData.setContext(this);
//        layoutInflater = getLayoutInflater();
//        LinearLayout ll = new LinearLayout(this);
//        ll.setOrientation(LinearLayout.VERTICAL);
//        ll.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT));
//        glSurfaceView.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
//        ll.addView(glSurfaceView);
//        RelativeLayout controls = (RelativeLayout)layoutInflater.inflate(R.layout.selfie_view , null ,false);
//        controls.setLayoutParams(new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                3.0f));
//        ll.addView(controls);
//        glSurfaceView.setControls(controls , glSurfaceView);
        setContentView(R.layout.selfie_view);
        glSurfaceView = (MyGLSurfaceView) findViewById(R.id.glviewxml);
        glSurfaceView.setParams(mCamera);
        glSurfaceView.setControls(glSurfaceView, glSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(CameraActivity.TAG, "onResume Camera");
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(CameraActivity.TAG, "onPause Camera");
        Logger.persist();
        glSurfaceView.onPause();
    }
}