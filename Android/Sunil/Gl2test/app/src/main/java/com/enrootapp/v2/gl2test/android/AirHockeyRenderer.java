/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 ***/
package com.enrootapp.v2.gl2test.android;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.enrootapp.v2.gl2test.R;
import com.enrootapp.v2.gl2test.Square;
import com.enrootapp.v2.gl2test.android.util.ColorShaderProgram;
import com.enrootapp.v2.gl2test.android.util.MatrixHelper;
import com.enrootapp.v2.gl2test.android.util.TextureHelper;
import com.enrootapp.v2.gl2test.android.util.TextureShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;

public class AirHockeyRenderer implements Renderer {
    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] invertedViewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    private Square table;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private int texture;

    public AirHockeyRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Square();

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.ic_launcher);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                / (float) height, 1f, 10f);
        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);


//        setIdentityM(modelMatrix, 0);
//        translateM(modelMatrix, 0, 0f, 0f, -2.5f);
//        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
//
//        final float[] temp = new float[16];
//        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
//        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }
float c =0;
    @Override
    public void onDrawFrame(GL10 glUnused) {
        c = c +.5f ;
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);
        rotateM(viewMatrix, 0,0.1f, 1f, 0f, 0f);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0,
                viewMatrix, 0);

        // Draw the table.
        setIdentityM(modelMatrix, 0);
        //rotateM(modelMatrix, 0,.1f, 1f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();


    }
}