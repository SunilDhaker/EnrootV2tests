package com.enrootapp.v2.cameratest.glCam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;
import android.view.View;

import com.enrootapp.v2.cameratest.android.util.MatrixHelper;
import com.enrootapp.v2.cameratest.glCam.util.Logger;
import com.enrootapp.v2.cameratest.glCam.util.MyData;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * Created by sdhaker on 10-01-2015.
 */
public class MyGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer, View.OnTouchListener {

    private final static String LOG_TAG = "MyGLSurfaceView";
    private final Activity mContext;
    float[] mtx = new float[16];
    float[] projectionMatrix = new float[16];
    float[] modelMatrix = new float[16];
    float[] viewMatrix = new float[16];
    float[] temp = new float[16];
    float[] temp2 = new float[16];
    float cout = 0f;
    float picX = 0f, picZ = 0f, picAzimuth = 0.0f;
    private float[] mtxBack = new float[16];
    private MyCamera mCamera;
    private SurfaceTexture mSurface;
    private SurfaceTexture mBackSurface;
    private DirectVideo mDirectVideo;
    private DirectVideo mDirectVideoBack;
    private int width;
    private int height;
    private boolean capturing = false;
    private boolean capture = false;
    private boolean animate = false;
    private Bitmap snap;
    private boolean initialized = false;

    public MyGLSurfaceView(Context context, MyCamera camera) {
        super(context);
        mContext = (Activity) context;
        mCamera = camera;
        setEGLContextClientVersion(2);
        setOnTouchListener(this);
        setRenderer(this);
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(CameraActivity.TAG, "glview onPause");
        initialized = false;
        mCamera.stop();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        MyData.setGL10(gl);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.0f, 1f, 1f);
        setIdentityM(modelMatrix, 0);

        mSurface.getTransformMatrix(mtx);
        mBackSurface.getTransformMatrix(mtxBack);
        if (animate) {
            //rotateM(viewMatrix , 0 , 90 , 0 , 1 , 0 );
            //translateM(modelMatrix,0,0,0,cout);
            translateM(modelMatrix, 0, -(cout) * picX, 0f, (cout) * picZ);
            rotateM(modelMatrix, 0, -180 - picAzimuth, 0, 1, 0);
            cout = Math.min(15f, cout + .1f);
            Matrix.setLookAtM(viewMatrix, 0, 0, 0, 0, 0f, 0f, 3f, 0f, 1.0f, 0.0f);

            rotateM(viewMatrix, 0, (float) MyData.getAzimuth(), 0f, 1f, 0f);
            //rotateM(viewMatrix, 0, (float) -1f * Navigation.getPitch(), 1f, 0f, 0f);
            //rotateM(viewMatrix, 0, (float) -1f * Navigation.getRoll(), 0f, 0f, 1f);
            //  rotateM(modelMatrix ,0,(float) MyData.getDeviceOrientationAngle() , 0f , 0f , 1f);
            rotateM(modelMatrix, 0, -90, 0f, 0f, 1f);
            multiplyMM(temp2, 0, viewMatrix, 0, modelMatrix, 0);
            multiplyMM(temp, 0, projectionMatrix, 0, temp2, 0);
            //multiplyMM(temp2, 0, modelMatrix, 0, temp, 0);
            mBackSurface.updateTexImage();
            setIdentityM(temp2, 0);
            rotateM(temp2, 0, -90, 0f, 0f, 1f);
            mDirectVideoBack.draw(mtxBack, temp2);
        } else {
            rotateM(modelMatrix, 0, -90, 0f, 0f, 1f);
            mSurface.updateTexImage();
            multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        }

        mDirectVideo.draw(mtx, temp);
        // System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
        if (capture && !capturing) {
            capturing = true;
            Logger.d("Enroot", "Capturing...");
            GLES20.glFlush();
            picZ = (float) Math.cos(Math.toRadians(MyData.getAzimuth()));
            picX = (float) Math.sin(Math.toRadians(MyData.getAzimuth()));
            picAzimuth = MyData.getAzimuth();
            snap = createBitmapFromGLSurface(0, 0, width, height);

            MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 0f, 50f);
            mCamera.openCam(mBackSurface, Camera.CameraInfo.CAMERA_FACING_BACK);
            animate = true;
        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Logger.d(LOG_TAG, "Surface Changed");
        if (!initialized) {
            initialized = true;
            initializeTextures();
            this.width = width;
            this.height = height;
            GLES20.glViewport(0, 0, width, height);
            mCamera.setParameters(width, height);
            if (capture) {
                Logger.d(CameraActivity.TAG, "Back cam");
                mCamera.openCam(mSurface, Camera.CameraInfo.CAMERA_FACING_BACK);
            } else {
                Logger.d(CameraActivity.TAG, "Front cam");
                mCamera.openCam(mSurface, Camera.CameraInfo.CAMERA_FACING_FRONT);
            }

            setIdentityM(projectionMatrix, 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(CameraActivity.TAG, "glview onresume");

    }

    public void initializeTextures() {
        int texture = createTexture();
        int backTexture = createTexture();
        mDirectVideo = new DirectVideo(texture);
        mDirectVideoBack = new DirectVideo(backTexture);
        mSurface = new SurfaceTexture(texture);
        mBackSurface = new SurfaceTexture(backTexture);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Logger.d(CameraActivity.TAG, "Surface Created");
    }

    private int createTexture() {
        int[] textures = new int[1];

        // generate one texture pointer and bind it as an external texture.
        GLES20.glGenTextures(1, textures, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);

        // No mip-mapping with camera source.
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        // Clamp to edge is only option.
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        Logger.d("Enroot", "TextureId: " + textures[0]);
        return textures[0];
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!capture) {
            mCamera.stop();
            //((AugmentedReality)MyData.getContext()).openCamera();
            capture = true;
        }
        return true;
    }

    /*public void captureScreen() {
        if (!capturing) {
            capturing = true;
            int screenshotSize = width * height;
            ByteBuffer bb = ByteBuffer.allocateDirect(screenshotSize * 4);
            bb.order(ByteOrder.nativeOrder());
            GLES20.glReadPixels(0, 0, width, height, GL10.GL_RGBA,
                    GL10.GL_UNSIGNED_BYTE, bb);
            int pixelsBuffer[] = new int[screenshotSize];
            bb.position(0);
            bb.asIntBuffer().get(pixelsBuffer);
            bb = null;
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.RGB_565);
            bitmap.setPixels(pixelsBuffer, screenshotSize - width, -width, 0,
                    0, width, height);
            pixelsBuffer = null;

            short sBuffer[] = new short[screenshotSize];
            ShortBuffer sb = ShortBuffer.wrap(sBuffer);
            bitmap.copyPixelsToBuffer(sb);

            // Making created bitmap (from OpenGL points) compatible with
            // Android bitmap
            for (int i = 0; i < screenshotSize; ++i) {
                short v = sBuffer[i];
                sBuffer[i] = (short) (((v & 0x1f) << 11) | (v & 0x7e0) | ((v & 0xf800) >> 11));
            }
            sb.rewind();
            bitmap.copyPixelsFromBuffer(sb);
            Bitmap copiedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);
            BufferedOutputStream bos = null;
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test1234.jpeg");
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
            } catch (IOException ie) {
                Logger.d("Enroot", "captureScreen");
            }
            copiedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
            bitmap.recycle();
            copiedBitmap.recycle();
            Logger.d("Enroot", "Finished capture");
            animate = true;
        }
    }*/

    private Bitmap createBitmapFromGLSurface(int x, int y, int w, int h)
            throws OutOfMemoryError {
        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            GLES20.glReadPixels(x, y, w, h, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    int texturePixel = bitmapBuffer[offset1 + j];
                    int blue = (texturePixel >> 16) & 0xff;
                    int red = (texturePixel << 16) & 0x00ff0000;
                    int pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            return null;
        }
        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
    }
}
