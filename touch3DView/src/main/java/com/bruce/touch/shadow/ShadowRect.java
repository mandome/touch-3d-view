package com.bruce.touch.shadow;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;

import com.bruce.touch.shadowLayout.ZDepthParam;

public class ShadowRect implements Shadow {

    private ShapeDrawable mTopShadow;
    private ShapeDrawable mBottomShadow;
    private ShapeDrawable mLeftShadow;
    private ShapeDrawable mRightShadow;

    private Rect mRectTopShadow;
    private Rect mRectBottomShadow;
    private Rect mRectLeftShadow;
    private Rect mRectRightShadow;

    public ShadowRect() {
        mRectTopShadow = new Rect();
        mRectBottomShadow = new Rect();
        mRectLeftShadow = new Rect();
        mRectRightShadow = new Rect();
        mTopShadow = new ShapeDrawable(new RectShape());
        mBottomShadow = new ShapeDrawable(new RectShape());
        mLeftShadow = new ShapeDrawable(new RectShape());
        mRightShadow = new ShapeDrawable(new RectShape());
    }

    @Override
    public void setParameter(ZDepthParam param, int left, int top, int right, int bottom, int color) {
        mRectTopShadow.left = left;
        mRectTopShadow.top = (int) (top - param.mOffsetYTopShadowPx);
        mRectTopShadow.right = right;
        mRectTopShadow.bottom = bottom;

        mRectBottomShadow.left = left;
        mRectBottomShadow.top = top;
        mRectBottomShadow.right = right;
        mRectBottomShadow.bottom = (int) (bottom + param.mOffsetYBottomShadowPx);

        mRectLeftShadow.left = (int) (left - param.mOffsetXLeftShadowPx);
        mRectLeftShadow.top = top;
        mRectLeftShadow.right = right;
        mRectLeftShadow.bottom = bottom;

        mRectRightShadow.left = left;
        mRectRightShadow.top = top;
        mRectRightShadow.right = (int) (right + param.mOffsetXRightShadowPx);
        mRectRightShadow.bottom = bottom;

        mTopShadow.getPaint().setColor(Color.argb((int) (Math.min(150, Math.abs(param.mOffsetYTopShadowPx) * 5f)), (color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, (color & 0x0000ff)));
        if (0 < param.mOffsetYTopShadowPx) {
            mTopShadow.getPaint().setMaskFilter(new BlurMaskFilter(param.mOffsetYTopShadowPx * 0.8f, BlurMaskFilter.Blur.NORMAL));
        } else {
            mTopShadow.getPaint().setMaskFilter(null);
        }

        mBottomShadow.getPaint().setColor(Color.argb((int) (Math.min(150, Math.abs(param.mOffsetYBottomShadowPx) * 5f)), (color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, (color & 0x0000ff)));
        if (0 < param.mOffsetYBottomShadowPx) {
            mBottomShadow.getPaint().setMaskFilter(new BlurMaskFilter(param.mOffsetYBottomShadowPx * 0.8f, BlurMaskFilter.Blur.NORMAL));
        } else {
            mBottomShadow.getPaint().setMaskFilter(null);
        }

        mLeftShadow.getPaint().setColor(Color.argb((int) (Math.min(150, Math.abs(param.mOffsetXLeftShadowPx) * 5f)), (color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, (color & 0x0000ff)));
        if (0 < param.mOffsetXLeftShadowPx) {
            mLeftShadow.getPaint().setMaskFilter(new BlurMaskFilter(param.mOffsetXLeftShadowPx * 0.8f, BlurMaskFilter.Blur.NORMAL));
        } else {
            mLeftShadow.getPaint().setMaskFilter(null);
        }

        mRightShadow.getPaint().setColor(Color.argb((int) (Math.min(150, Math.abs(param.mOffsetXRightShadowPx) * 5f)), (color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, (color & 0x0000ff)));
        if (0 < param.mOffsetXRightShadowPx) {
            mRightShadow.getPaint().setMaskFilter(new BlurMaskFilter(param.mOffsetXRightShadowPx * 0.8f, BlurMaskFilter.Blur.NORMAL));
        } else {
            mRightShadow.getPaint().setMaskFilter(null);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawRect(mRectBottomShadow, mBottomShadow.getPaint());
        canvas.drawRect(mRectTopShadow, mTopShadow.getPaint());
        canvas.drawRect(mRectLeftShadow, mLeftShadow.getPaint());
        canvas.drawRect(mRectRightShadow, mRightShadow.getPaint());
    }
}
