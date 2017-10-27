package com.bruce.touch.shadowLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.bruce.touch.R;


public class ZDepthShadowLayout extends RelativeLayout {
    public static final String TAG = "Touch3DView";

    protected static final int DEFAULT_ATTR_SHAPE = 0;
    protected static final int DEFAULT_ATTR_ZDEPTH = 1;
    protected static final int DEFAULT_ATTR_TOUCH_LEVEL = 2;
    protected static final int DEFAULT_ATTR_ZDEPTH_ANIM_DURATION = 150;
    protected static final boolean DEFAULT_ATTR_ZDEPTH_DO_ANIMATION = true;

    protected static final int SHAPE_RECT = 0;
    protected static final int SHAPE_OVAL = 1;

    protected ShadowView mShadowView;

    protected int mAttrShape;
    protected int mAttrZDepth;
    protected long mAttrZDepthAnimDuration;
    protected boolean mAttrZDepthDoAnimation;
    protected int mShadowColor;
    protected int mTouchLevel;
    protected ZDepth mZDepth;

    public ZDepthShadowLayout(Context context) {
        this(context, null);
    }

    public ZDepthShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZDepthShadowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    protected void init(AttributeSet attrs, int defStyle) {
        setClipToPadding(false);

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Touch3DView, defStyle, 0);
        mAttrShape = typedArray.getInt(R.styleable.Touch3DView_z_depth_shape, DEFAULT_ATTR_SHAPE);
        mAttrZDepth = typedArray.getInt(R.styleable.Touch3DView_z_depth, DEFAULT_ATTR_ZDEPTH);
        mAttrZDepthAnimDuration = typedArray.getInt(R.styleable.Touch3DView_anim_duration, DEFAULT_ATTR_ZDEPTH_ANIM_DURATION);
        mAttrZDepthDoAnimation = typedArray.getBoolean(R.styleable.Touch3DView_do_anim, DEFAULT_ATTR_ZDEPTH_DO_ANIMATION);
        mShadowColor = typedArray.getInt(R.styleable.Touch3DView_shadow_color, Color.BLACK);
        mTouchLevel = typedArray.getInt(R.styleable.Touch3DView_touch_level, DEFAULT_ATTR_TOUCH_LEVEL);

        typedArray.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mShadowView = new ShadowView(getContext());
        mShadowView.setShape(mAttrShape);
        mShadowView.setZDepth(mAttrZDepth);
        mShadowView.setZDepthAnimDuration(mAttrZDepthAnimDuration);
        mShadowView.setZDepthDoAnimation(mAttrZDepthDoAnimation);
        mShadowView.setShadowColor(mShadowColor);
        mShadowView.setTouchLevel(mTouchLevel);
        mShadowView.setZDepthPadding(mAttrZDepth);
        if(mZDepth != null){
            mShadowView.changeZDepth(mZDepth);
        }
        addView(mShadowView, 0);

        int paddingLeft = mShadowView.getZDepthPaddingLeft();
        int paddingTop = mShadowView.getZDepthPaddingTop();
        int paddingRight = mShadowView.getZDepthPaddingRight();
        int paddingBottom = mShadowView.getZDepthPaddingBottom();
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int childCount = getChildCount();

        int maxChildViewWidth = 0;
        int maxChildViewHeight = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (maxChildViewWidth  < child.getMeasuredWidth())  maxChildViewWidth  = child.getMeasuredWidth();
            if (maxChildViewHeight < child.getMeasuredHeight()) maxChildViewHeight = child.getMeasuredHeight();
        }

        int paddingLeft = mShadowView.getZDepthPaddingLeft();
        int paddingTop = mShadowView.getZDepthPaddingTop();
        int paddingRight = mShadowView.getZDepthPaddingRight();
        int paddingBottom = mShadowView.getZDepthPaddingBottom();
        maxChildViewWidth  += paddingLeft + paddingRight;
        maxChildViewHeight += paddingTop + paddingBottom;
        mShadowView.measure(
                MeasureSpec.makeMeasureSpec(maxChildViewWidth,  MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(maxChildViewHeight, MeasureSpec.EXACTLY)
        );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int width  = right - left;
        int height = bottom - top;
        mShadowView.layout(0, 0, width, height);
    }

    public int getWidthExceptShadow() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public int getHeightExceptShadow() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public void changeZDepth(ZDepth zDepth) {
        mShadowView.changeZDepth(zDepth);
    }

    public void setAttrZDepthAnimDuration(long mAttrZDepthAnimDuration) {
        this.mAttrZDepthAnimDuration = mAttrZDepthAnimDuration;
        mShadowView.setZDepthAnimDuration(mAttrZDepthAnimDuration);
    }

    public int getTouchLevel() {
        return mTouchLevel;
    }

    public void setTouchLevel(int touchLevel) {
        this.mTouchLevel = touchLevel;
    }

    public void setShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
    }

    public void setZDepth(int depth) {
        mAttrZDepth = depth;
    }

    public void setZDepth(ZDepth zDepth) {
        mZDepth = zDepth;
    }
}
