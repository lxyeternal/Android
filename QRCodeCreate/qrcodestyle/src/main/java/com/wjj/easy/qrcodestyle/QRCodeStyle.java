package com.wjj.easy.qrcodestyle;

import android.graphics.Bitmap;

/**
 * QRCode  Style
 *
 * @author wujiajun
 */
public class QRCodeStyle {

    private Bitmap qrBitmap;
    private Bitmap logoBitmap;
    private Bitmap targetBitmap;
    private int radius;
    private boolean isCircle;
    private int space;
    private Bitmap maskBitmap;
    private Bitmap bgBitmap;

    Builder builder;

    private QRCodeStyle(Builder builder) {
        this.builder = builder;
        logoBitmap = builder.logoBitmap;
        radius = builder.radius;
        isCircle = builder.isCircle;
        space = builder.space;
        qrBitmap = builder.qrBitmap;
        maskBitmap = builder.maskBitmap;
        bgBitmap = builder.bgBitmap;
        if (qrBitmap == null) {
            throw new RuntimeException("must provide qr code bitmap!");
        }
        if (logoBitmap != null && radius != 0) {
            logoBitmap = QRCodeStyleUtils.zoom(logoBitmap, radius);
        }
        if (logoBitmap != null && isCircle) {
            logoBitmap = QRCodeStyleUtils.setCircle(logoBitmap);
        }
        if (logoBitmap != null && isCircle && space != 0) {
            logoBitmap = QRCodeStyleUtils.setCircleSpace(logoBitmap, space);
        }
        if (logoBitmap != null) {
            targetBitmap = QRCodeStyleUtils.mergeBitmap(logoBitmap, qrBitmap);
        }
        if (maskBitmap != null) {
            targetBitmap = QRCodeStyleUtils.mask(maskBitmap, qrBitmap);
        }
        if (bgBitmap != null) {
            targetBitmap = QRCodeStyleUtils.mergeBitmap(targetBitmap == null ? qrBitmap : targetBitmap, bgBitmap);
        }
    }

    /**
     * Get target bitmap
     *
     * @return Bitmap
     */
    public Bitmap get() {
        return targetBitmap;
    }

    public static class Builder {
        private Bitmap logoBitmap;
        private int radius;
        private boolean isCircle;
        private int space;
        private Bitmap qrBitmap;
        private Bitmap maskBitmap;
        private Bitmap bgBitmap;

        private Builder() {
        }

        /**
         * get a Builder instance
         *
         * @return Builder
         */
        public static Builder builder() {
            return new Builder();
        }

        /**
         * return the QRCodeStyle instance
         *
         * @return QRCodeStyle
         */
        public QRCodeStyle build() {
            return new QRCodeStyle(this);
        }

        /**
         * set logo bitmap
         *
         * @param logoBitmap
         * @return
         */
        public Builder setLogo(Bitmap logoBitmap) {
            this.logoBitmap = logoBitmap;
            return this;
        }

        /**
         * Set radius of logo
         *
         * @param radius
         * @return
         */
        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        /**
         * Set whether logo is circle
         *
         * @param circle
         * @return
         */
        public Builder setCircle(boolean circle) {
            isCircle = circle;
            return this;
        }

        /**
         * Set the space of the outer circle of the logo
         *
         * @param space
         * @return
         */
        public Builder setSpace(int space) {
            this.space = space;
            return this;
        }

        /**
         * set QR code bitmap
         *
         * @param qrBitmap
         * @return
         */
        public Builder setQr(Bitmap qrBitmap) {
            this.qrBitmap = qrBitmap;
            return this;
        }

        /**
         * set mask bitmap
         *
         * @param maskBitmap
         * @return
         */
        public Builder setMask(Bitmap maskBitmap) {
            this.maskBitmap = maskBitmap;
            return this;
        }

        /**
         * set background bitmap
         *
         * @param bgBitmap
         * @return
         */
        public Builder setBg(Bitmap bgBitmap) {
            this.bgBitmap = bgBitmap;
            return this;
        }
    }
}
