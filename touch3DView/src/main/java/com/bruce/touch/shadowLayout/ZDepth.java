package com.bruce.touch.shadowLayout;

import android.content.Context;

import com.bruce.touch.utils.DisplayUtils;

public class ZDepth {

    public static ZDepth getZDepthWithAttributeValue(int zDepthValue) {
        switch (zDepthValue) {
            case 0: return new ZDepth(0,0,0,0);
            case 1: return new ZDepth(1,3,1,2);
            case 2: return new ZDepth(2,4,2,3);
            case 3: return new ZDepth(3,6,3,5);
            case 4: return new ZDepth(5,8,5,7);
            case 5: return new ZDepth(8,12,8,10);
            default: throw new IllegalArgumentException("unknown zDepth value.");
        }
    }

    public float mOffsetYTopShadow; // dp
    public float mOffsetYBottomShadow; // dp
    public float mOffsetXLeftShadow; // dp
    public float mOffsetXRightShadow; // dp

    public ZDepth() {
    }

    public float getOffsetYTopShadow() {
        return mOffsetYTopShadow;
    }

    public float getOffsetYBottomShadow() {
        return mOffsetYBottomShadow;
    }

    public float getOffsetXLeftShadow() {
        return mOffsetXLeftShadow;
    }

    public float getOffsetXRightShadow() {
        return mOffsetXRightShadow;
    }

    public ZDepth(float mOffsetYTopShadow, float mOffsetYBottomShadow, float mOffsetXLeftShadow, float mOffsetXRightShadow) {
        this.mOffsetYTopShadow = mOffsetYTopShadow;
        this.mOffsetYBottomShadow = mOffsetYBottomShadow;
        this.mOffsetXLeftShadow = mOffsetXLeftShadow;
        this.mOffsetXRightShadow = mOffsetXRightShadow;
    }

    public void setParams(float mOffsetYTopShadow, float mOffsetYBottomShadow, float mOffsetXLeftShadow, float mOffsetXRightShadow) {
        this.mOffsetYTopShadow = mOffsetYTopShadow;
        this.mOffsetYBottomShadow = mOffsetYBottomShadow;
        this.mOffsetXLeftShadow = mOffsetXLeftShadow;
        this.mOffsetXRightShadow = mOffsetXRightShadow;
    }



    public float getOffsetYTopShadowPx(Context context) {
        return DisplayUtils.convertDpToPx(context, mOffsetYTopShadow);
    }

    public float getOffsetYBottomShadowPx(Context context) {
        return DisplayUtils.convertDpToPx(context, mOffsetYBottomShadow);
    }

    public float getOffsetXLeftShadow(Context context) {
        return DisplayUtils.convertDpToPx(context, mOffsetXLeftShadow);
    }

    public float getOffsetXRightShadow(Context context) {
        return DisplayUtils.convertDpToPx(context, mOffsetXRightShadow);
    }

}
