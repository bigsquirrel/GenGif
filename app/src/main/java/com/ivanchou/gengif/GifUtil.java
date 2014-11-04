package com.ivanchou.gengif;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by ivanchou on 14-9-30.
 */
public class GifUtil {
    public static final String TAG = "GifUtil";

    static {
        System.loadLibrary("gifflen");
    }

    /**
     * Init the gif file
     *
     * @param gifName    name
     * @param w          width
     * @param h          height
     * @param numColors  colors
     * @param quality
     * @param frameDelay times
     * @return
     */
    public native int Init(String gifName, int w, int h, int numColors, int quality,
                           int frameDelay);

    public native void Close();

    public native int AddFrame(int[] pixels);

    /**
     * encode the bitmaps to gif
     *
     * @param fileName
     * @param bitmaps
     * @param delay
     */
    public void Encode(String fileName, Bitmap[] bitmaps, int delay) {
        if (bitmaps == null || bitmaps.length == 0) {
            throw new NullPointerException("Bitmaps should have content!!!");

        }
        int width = bitmaps[0].getWidth();
        int height = bitmaps[0].getHeight();

        if (Init(fileName, width, height, 256, 100, delay) != 0) {
            Log.e(TAG, "GifUtil init failed");
            return;
        }

        for (Bitmap bp : bitmaps) {

            int pixels[] = new int[width * height];

            bp.getPixels(pixels, 0, width, 0, 0, width, height);
            AddFrame(pixels);
        }

        Close();

    }
}
