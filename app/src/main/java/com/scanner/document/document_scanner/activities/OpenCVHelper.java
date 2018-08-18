package com.scanner.document.document_scanner.activities;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class OpenCVHelper {
    static {
        System.loadLibrary("OpenCV");
    }

    public static native int[] gray(int[] buf, int w, int h);

    public static native int[] getBoxPoints(int[] buf, int w, int h);

    public static native int[] perspective(int[] buf, int[] points, int w, int h);

    public static native Bitmap getMagicColorBitmap(Bitmap bitmap);

    public static native Bitmap getGrayBitmap(Bitmap bitmap);

    public static native Bitmap getMagicBitmapp(Bitmap bitmap);

    public static native Bitmap getGrayBitmapp(Bitmap bitmap);

    public static native Bitmap getLightedBitmapp(Bitmap bitmap);

    public static native Bitmap getBlackWhiteBitmapp(Bitmap bitmap);
}
