package com.bruce.touch.utils;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Bruce on 2017/10/19.
 */

public class RotateAnimation extends Animation {

    private float mOldX;
    private float mOldY;
    private float mTouchX;
    private float mTouchY;
    private float mCenterX;
    private float mCenterY;
    private float mDepthZ;
    private boolean mReverse;
    private Camera mCamera;
    private float scale = 1;
    private float mOldDegreesX;
    private float mOldDegreesY;
    private float mDegreesX;
    private float mDegreesY;
    private int mTouchLevel;

    public void setTouchX(float mTouchX) {
        this.mTouchX = mTouchX;
    }

    public void setTouchY(float mTouchY) {
        this.mTouchY = mTouchY;
    }

    public void setOldX(float mOldX) {
        this.mOldX = mOldX;
    }

    public void setOldY(float mOldY) {
        this.mOldY = mOldY;
    }

    public RotateAnimation(Context context, float oldX, float oldY, float touchX, float touchY,
                           float centerX, float centerY, float depthZ, int touchLevel) {
        mOldX = oldX;
        mOldY = oldY;
        mTouchX = touchX;
        mTouchY = touchY;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mTouchLevel = touchLevel;
        scale = context.getResources().getDisplayMetrics().density;
    }

    private void initData() {
        mOldDegreesX = (mOldX - mCenterX) / mCenterX * (3f + (float) mTouchLevel);
        mOldDegreesY = (mOldY - mCenterY) / mCenterY * (5f + (float) mTouchLevel);
        mDegreesX = (mTouchX - mCenterX) / mCenterX * (3f + (float) mTouchLevel) - mOldDegreesX;
        mDegreesY = (mTouchY - mCenterY) / mCenterY * (5f + (float) mTouchLevel) - mOldDegreesY;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        initData();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float lastDegreesX = mOldDegreesX + mDegreesX * interpolatedTime;
        float lastDegreesY = mOldDegreesY + mDegreesY * interpolatedTime;
        final float centerX = mCenterX;
        final float centerY = mCenterY;

        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();
        camera.save();

        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }

        camera.rotateX(-1f * lastDegreesY);
        camera.rotateY(lastDegreesX);

        camera.getMatrix(matrix);
        camera.restore();

        float[] mValues = new float[9];
        matrix.getValues(mValues);              //获取数值
        mValues[6] = mValues[6] / scale;          //数值修正
        mValues[7] = mValues[7] / scale;          //数值修正
        matrix.setValues(mValues);              //重新赋值

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
