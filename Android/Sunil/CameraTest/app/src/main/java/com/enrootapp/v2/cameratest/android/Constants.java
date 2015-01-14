/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 ***/
package com.enrootapp.v2.cameratest.android;

public class Constants {
    public static final int BYTES_PER_FLOAT = 4;
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