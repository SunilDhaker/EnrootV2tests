package com.enrootapp.v2.cameratest;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.enrootapp.v2.cameratest.glCam.util.Logger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends ActionBarActivity implements GLSurfaceView.Renderer {

    int currentCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;
    boolean changeToFront = false;
    Camera mCamera;
    TextureView front;
    TextureView back;
    SurfaceTexture frontSurface;
    SurfaceTexture backSurface;
    boolean changeToBack = false;
    GLSurfaceView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater layoutInflator = getLayoutInflater();
        LinearLayout layout_back = (LinearLayout) layoutInflator.inflate(R.layout.activity_main, null, false);
        back = (TextureView) layout_back.findViewById(R.id.backCamera);
        back.setOpaque(false);
        back.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Logger.d("Enroot", "back: onSurfaceTextureAvailable");
                backSurface = surface;
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                Logger.d("Enroot", "back: onSurfaceTextureSizeChanged");
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                Logger.d("Enroot", "back: onSurfaceTextureDestroyed");
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.release();
                }
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                Logger.d("Enroot", "back: onSurfaceTextureUpdated");
            }
        });
        addContentView(layout_back,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout layout_front = (LinearLayout) layoutInflator.inflate(R.layout.activity_overlap, null, false);

        front = (TextureView) layout_front.findViewById(R.id.frontCamera);
        front.setOpaque(false);
        front.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Logger.d("Enroot", "front: onSurfaceTextureAvailable");
                frontSurface = surface;
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                Logger.d("Enroot", "front: onSurfaceTextureSizeChanged");
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                Logger.d("Enroot", "front: onSurfaceTextureDestroyed");
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.release();
                }
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                Logger.d("Enroot", "front: onSurfaceTextureUpdated");
            }
        });
        addContentView(layout_front,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        layout_front.bringToFront();

        glView = new GLSurfaceView(this);
        glView.setRenderer(this);
        glView.setZOrderOnTop(true);

        ((Button) layout_front.findViewById(R.id.camera_switch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });
    }

    public void switchCamera() {
        if (backSurface == null || frontSurface == null) return;
        if (mCamera != null) {
            mCamera.release();
            if (currentCameraType == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                currentCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;
            } else {
                currentCameraType = Camera.CameraInfo.CAMERA_FACING_FRONT;
            }
        }

        mCamera = Camera.open(currentCameraType);

        try {
            if (currentCameraType == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                mCamera.setPreviewTexture(frontSurface);
            } else {
                mCamera.setPreviewTexture(backSurface);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        front.setAlpha(0.2f);
        front.bringToFront();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
