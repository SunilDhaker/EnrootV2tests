package com.enrootapp.v2.cameratest.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.enrootapp.v2.cameratest.R;
import com.enrootapp.v2.cameratest.glCam.MyCamera;
import com.enrootapp.v2.cameratest.glCam.MyGLSurfaceView;
import com.enrootapp.v2.cameratest.glCam.util.Logger;

/**
 * Created by Vivz on 10/25/13.
 */
public class FragmentA extends Fragment {

    public static String TAG = "Enroot";
    LinearLayout selfieLayout;
    LayoutInflater layoutInflater;
    private MyGLSurfaceView glSurfaceView;
    private MyCamera mCamera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mCamera = new MyCamera();
        //glSurfaceView = new MyGLSurfaceView(this, mCamera);
        //MyData.setContext(this);
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


        View v = inflater.inflate(R.layout.selfie_view, container, false);
        glSurfaceView = (MyGLSurfaceView) v.findViewById(R.id.glviewxml);
        glSurfaceView.setParams(mCamera);
        glSurfaceView.setControls(glSurfaceView, glSurfaceView);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        //Logger.d(CameraActivity.TAG, "onResume Camera");
        glSurfaceView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Logger.d(CameraActivity.TAG, "onPause Camera");
        Logger.persist();
        glSurfaceView.onPause();
    }
}


