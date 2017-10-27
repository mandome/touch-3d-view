package com.bruce.touch.shadowLayout;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bruce.touch.shadow.Shadow;
import com.bruce.touch.shadow.ShadowOval;
import com.bruce.touch.shadow.ShadowRect;
import com.bruce.touch.utils.DisplayUtils;


public class ShadowView extends View {
    protected static final String TAG = "ShadowView";

    protected static final String ANIM_PROPERTY_OFFSET_TOP_SHADOW = "offsetTopShadow";
    protected static final String ANIM_PROPERTY_OFFSET_BOTTOM_SHADOW = "offsetBottomShadow";
    protected static final String ANIM_PROPERTY_OFFSET_LEFT_SHADOW = "offsetLeftShadow";
    protected static final String ANIM_PROPERTY_OFFSET_RIGHT_SHADOW = "offsetRightShadow";

    protected Shadow mShadow;
    protected ZDepthParam mZDepthParam;
    protected int mZDepthPaddingLeft;
    protected int mZDepthPaddingTop;
    protected int mZDepthPaddingRight;
    protected int mZDepthPaddingBottom;
    protected long mZDepthAnimDuration;
    protected boolean mZDepthDoAnimation;
    protected int mShadowColor;
    protected int mTouchLevel;
    protected int mZDepthPaddingLeftValue;

    protected ShadowView(Context context) {
        this(context, null);
        init();
    }

    protected ShadowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    protected ShadowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    protected void setZDepthDoAnimation(boolean doAnimation) {
        mZDepthDoAnimation = doAnimation;
    }

    protected void setZDepthAnimDuration(long duration) {
        mZDepthAnimDuration = duration;
    }

    protected void setZDepthPadding(int zDepthPaddingLeftValue) {
        mZDepthPaddingLeftValue = zDepthPaddingLeftValue;
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthPaddingLeftValue);
        mZDepthPaddingLeft = measureZDepthPadding(zDepth);
        mZDepthPaddingTop = measureZDepthPadding(zDepth);
        mZDepthPaddingRight = measureZDepthPadding(zDepth);
        mZDepthPaddingBottom = measureZDepthPadding(zDepth);
    }

    public void setTouchLevel(int mTouchLevel) {
        this.mTouchLevel = mTouchLevel;
    }

    public void setShadowColor(int shadowColor) {
        this.mShadowColor = shadowColor;
    }

    protected int measureZDepthPadding(ZDepth zDepth) {
        float maxAboveOffset = zDepth.getOffsetYTopShadowPx(getContext());
        float maxBelowOffset = zDepth.getOffsetYBottomShadowPx(getContext());
        float maxLeftOffset = zDepth.getOffsetXLeftShadow(getContext());
        float maxRightOffset = zDepth.getOffsetXRightShadow(getContext());

        float maxAboveSize = maxAboveOffset * 2 + DisplayUtils.convertDpToPx(getContext(), (float) mTouchLevel) * 2;
        float maxBelowSize = maxBelowOffset * 2 + DisplayUtils.convertDpToPx(getContext(), (float) mTouchLevel) * 2;
        float maxLeftSize = maxLeftOffset * 2 + DisplayUtils.convertDpToPx(getContext(), (float) mTouchLevel) * 2;
        float maxRightSize = maxRightOffset * 2 + DisplayUtils.convertDpToPx(getContext(), (float) mTouchLevel) * 2;

        return (int) Math.max(Math.max(maxAboveSize, maxBelowSize), Math.max(maxLeftSize, maxRightSize));
    }

    protected int getZDepthPaddingLeft() {
        return mZDepthPaddingLeft;
    }

    protected int getZDepthPaddingTop() {
        return mZDepthPaddingTop;
    }

    protected int getZDepthPaddingRight() {
        return mZDepthPaddingRight;
    }

    protected int getZDepthPaddingBottom() {
        return mZDepthPaddingBottom;
    }

    protected void setShape(int shape) {
        switch (shape) {
            case ZDepthShadowLayout.SHAPE_RECT:
                mShadow = new ShadowRect();
                break;

            case ZDepthShadowLayout.SHAPE_OVAL:
                mShadow = new ShadowOval();
                break;

            default:
                throw new IllegalArgumentException("unknown shape value.");
        }
    }

    protected void setZDepth(int zDepthValue) {
        ZDepth zDepth = getZDepthWithAttributeValue(zDepthValue);
        setZDepth(zDepth);
    }

    protected void setZDepth(ZDepth zDepth) {
        mZDepthParam = new ZDepthParam();
        mZDepthParam.initZDepth(getContext(), zDepth);
    }

    private ZDepth getZDepthWithAttributeValue(int zDepthValue) {
        return ZDepth.getZDepthWithAttributeValue(zDepthValue);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (wMode) {
            case MeasureSpec.EXACTLY:
                // NOP
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                wSize = 0;
                break;
        }

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                // NOP
                break;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                hSize = 0;
                break;
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(wSize, wMode),
                MeasureSpec.makeMeasureSpec(hSize, hMode));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int parentWidth = (right - left);
        int parentHeight = (bottom - top);

        mShadow.setParameter(mZDepthParam,
                mZDepthPaddingLeft,
                mZDepthPaddingTop,
                parentWidth - mZDepthPaddingRight,
                parentHeight - mZDepthPaddingBottom, mShadowColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mShadow.onDraw(canvas);
    }

    protected void changeZDepth(ZDepth zDepth) {

        float newOffsetYTopShadow = zDepth.getOffsetYTopShadowPx(getContext());
        float newOffsetYBottomShadow = zDepth.getOffsetYBottomShadowPx(getContext());
        float newOffsetXLeftShadow = zDepth.getOffsetXLeftShadow(getContext());
        float newOffsetXRightShadow = zDepth.getOffsetXRightShadow(getContext());

        if (!mZDepthDoAnimation) {
            mZDepthParam.mOffsetYTopShadowPx = newOffsetYTopShadow;
            mZDepthParam.mOffsetYBottomShadowPx = newOffsetYBottomShadow;
            mZDepthParam.mOffsetXLeftShadowPx = newOffsetXLeftShadow;
            mZDepthParam.mOffsetXRightShadowPx = newOffsetXRightShadow;

            mShadow.setParameter(mZDepthParam,
                    mZDepthPaddingLeft,
                    mZDepthPaddingTop,
                    getWidth() - mZDepthPaddingRight,
                    getHeight() - mZDepthPaddingBottom, mShadowColor);
            invalidate();
            return;
        }

        float nowOffsetYTopShadow = mZDepthParam.mOffsetYTopShadowPx;
        float nowOffsetYBottomShadow = mZDepthParam.mOffsetYBottomShadowPx;
        float nowOffsetXLeftShadow = mZDepthParam.mOffsetXLeftShadowPx;
        float nowOffsetXRightShadow = mZDepthParam.mOffsetXRightShadowPx;

        PropertyValuesHolder offsetTopShadowHolder = PropertyValuesHolder.ofFloat(ANIM_PROPERTY_OFFSET_TOP_SHADOW,
                nowOffsetYTopShadow,
                newOffsetYTopShadow);
        PropertyValuesHolder offsetBottomShadowHolder = PropertyValuesHolder.ofFloat(ANIM_PROPERTY_OFFSET_BOTTOM_SHADOW,
                nowOffsetYBottomShadow,
                newOffsetYBottomShadow);
        PropertyValuesHolder offsetLeftShadowHolder = PropertyValuesHolder.ofFloat(ANIM_PROPERTY_OFFSET_LEFT_SHADOW,
                nowOffsetXLeftShadow,
                newOffsetXLeftShadow);
        PropertyValuesHolder offsetRightShadowHolder = PropertyValuesHolder.ofFloat(ANIM_PROPERTY_OFFSET_RIGHT_SHADOW,
                nowOffsetXRightShadow,
                newOffsetXRightShadow);

        ValueAnimator anim = ValueAnimator
                .ofPropertyValuesHolder(
                        offsetTopShadowHolder,
                        offsetBottomShadowHolder,
                        offsetLeftShadowHolder,
                        offsetRightShadowHolder);
        anim.setDuration(mZDepthAnimDuration);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float offsetTopShadow = (Float) animation.getAnimatedValue(ANIM_PROPERTY_OFFSET_TOP_SHADOW);
                float offsetBottomShadow = (Float) animation.getAnimatedValue(ANIM_PROPERTY_OFFSET_BOTTOM_SHADOW);
                float offsetLeftShadow = (Float) animation.getAnimatedValue(ANIM_PROPERTY_OFFSET_LEFT_SHADOW);
                float offsetRightShadow = (Float) animation.getAnimatedValue(ANIM_PROPERTY_OFFSET_RIGHT_SHADOW);

                mZDepthParam.mOffsetYTopShadowPx = offsetTopShadow;
                mZDepthParam.mOffsetYBottomShadowPx = offsetBottomShadow;
                mZDepthParam.mOffsetXLeftShadowPx = offsetLeftShadow;
                mZDepthParam.mOffsetXRightShadowPx = offsetRightShadow;

                mShadow.setParameter(mZDepthParam,
                        mZDepthPaddingLeft,
                        mZDepthPaddingTop,
                        getWidth() - mZDepthPaddingRight,
                        getHeight() - mZDepthPaddingBottom, mShadowColor);

                invalidate();
            }
        });
        anim.start();
    }
}
