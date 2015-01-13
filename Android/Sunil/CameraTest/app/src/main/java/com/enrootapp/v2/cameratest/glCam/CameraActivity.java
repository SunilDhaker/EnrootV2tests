package com.enrootapp.v2.cameratest.glCam;

import android.os.Bundle;

import com.enrootapp.v2.cameratest.glCam.util.Logger;
import com.enrootapp.v2.cameratest.glCam.util.MyData;
import com.enrootapp.v2.cameratest.glCam.util.SensorsActivity;

/**
 * Created by sdhaker on 10-01-2015.
 */
public class CameraActivity extends SensorsActivity {
    public static String TAG = "Enroot";
    private MyGLSurfaceView glSurfaceView;
    private MyCamera mCamera;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyData.setContext(this);

        mCamera = new MyCamera();
        glSurfaceView = new MyGLSurfaceView(this, mCamera);

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
//        /*ImageView iv = new ImageView(this);
//        iv.setLayoutParams(new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                1.0f));
//        ll.addView(iv);*/
        setContentView(glSurfaceView);
        //glSurfaceView.setIV(iv);
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