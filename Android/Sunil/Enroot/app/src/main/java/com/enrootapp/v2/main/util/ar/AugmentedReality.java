package com.enrootapp.v2.main.util.ar;

/**
 * Created by rmuttineni on 15/01/2015.
 */
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.enrootapp.v2.main.util.Logger;

/**
 * This class extends the SensorsActivity and is designed tie the AugmentedView
 *
 * @author Sunil dhaker <sunil965@live.com>
 */

public class AugmentedReality extends SensorsActivity implements Callback {
    public static boolean useDataSmoothing = true;
    SurfaceHolder mSurfaceHolder;
    FragmentManager fm;
    MyGLSurfaceView mGLSurfaceView;
    SurfaceHolder arg0;
    int arg1, arg2, arg3;
    private Camera camera;
    private SurfaceView mSurfaceView;
    private MyCamera mCamera;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mCamera = new MyCamera();
        MyData.setContext(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mSurfaceView = new SurfaceView(this);

//		addContentView(mSurfaceView, new LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT));

        mGLSurfaceView = new MyGLSurfaceView(this, mCamera);
        addContentView(mGLSurfaceView, new LayoutParams(
                android.app.ActionBar.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mGLSurfaceView.setZOrderMediaOverlay(true);


        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT
                | LayoutParams.FILL_PARENT);
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //mCamera.switchCam(mSurfaceHolder);
                return false;
            }
        });
        Logger.d("Augumented reality ", " x");

    }


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

        this.arg0 = arg0;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        arg0 = holder;
    }

    @Override
    protected void onResume() {

        super.onResume();
        mGLSurfaceView.onResume();

    }

    @Override
    protected void onPause() {

        super.onPause();
        mGLSurfaceView.onPause();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void openCamera() {
        //mCamera.switchCam(mSurfaceHolder);
        //mGLSurfaceView.setVisibility(View.INVISIBLE);
    }
}
