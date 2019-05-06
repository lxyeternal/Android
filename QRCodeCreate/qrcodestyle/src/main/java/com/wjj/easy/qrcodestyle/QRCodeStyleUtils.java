package com.wjj.easy.qrcodestyle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

/**
 * QRCode  Style Utils
 *
 * @author wujiajun
 */

public class QRCodeStyleUtils {

    /**
     * Zoom bitmap
     *
     * @param bitmap bitmap
     * @param radius radius
     * @return Bitmap A new processed image
     */
    public static Bitmap zoom(Bitmap bitmap, int radius) {
        Matrix m = new Matrix();
        float sx = (float) 2 * radius / bitmap.getWidth();
        float sy = (float) 2 * radius / bitmap.getHeight();
        m.setScale(sx, sy);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
    }

    /**
     * Cut image is round
     *
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap setCircle(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(width / 2, height / 2, (width <= height ? width : height) / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return target;
    }

    /**
     * Set the space of the outer circle of the logo
     *
     * @param bitmap
     * @param space
     * @return Bitmap
     */
    public static Bitmap setCircleSpace(Bitmap bitmap, int space) {
        int width = bitmap.getWidth() + space * 2;
        int height = bitmap.getHeight() + space * 2;
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.save();
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, height, paint);
        paint.setXfermode(null);
        canvas.restore();
        canvas.drawBitmap(bitmap, space, space, paint);
        return target;
    }

    /**
     * merge bitmap
     *
     * @param small
     * @param big
     * @return Bitmap
     */
    public static Bitmap mergeBitmap(Bitmap small, Bitmap big) {
        Bitmap target = Bitmap.createBitmap(big.getWidth(), big.getHeight(), big.getConfig());
        Canvas canvas = new Canvas(target);
        canvas.drawBitmap(big, new Matrix(), null);

        int left = (big.getWidth() - small.getWidth()) / 2;
        int top = (big.getHeight() - small.getHeight()) / 2;
        canvas.drawBitmap(small, left, top, null);
        return target;
    }

    /**
     * Open the colorful world
     *
     * @param mask background bitmap ,as mask bitmap
     * @param qr qr
     * @return Bitmap
     */
    public static Bitmap mask(Bitmap mask, Bitmap qr) {
        int width = qr.getWidth();
        int height = qr.getHeight();
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.save();
        canvas.drawBitmap(qr, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mask, new Rect(0, 0, mask.getWidth(), mask.getHeight()), new Rect(0, 0, width, height), paint);
        paint.setXfermode(null);
        canvas.restore();
        return target;
    }
}
