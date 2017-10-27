package com.bruce.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.bruce.touch.shadowLayout.ZDepth;
import com.bruce.touch.shadowLayout.ZDepthShadowLayout;
import com.bruce.touch.utils.RotateAnimation;

/**
 * Created by Bruce on 2017/10/19.
 */

public class Touch3DView extends ZDepthShadowLayout {

    private RotateAnimation rotation;
    private int centerX;
    private int centerY;
    private int oldX;
    private int oldY;
    private ZDepth zDepth;
    private boolean down = false;
    private int mTouchSlop;
    private int mInitialTouchX, mInitialTouchY;
    private boolean canMove;
    private ZDepth initZDepth;

    public Touch3DView(Context context) {
        super(context);
    }

    public Touch3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Touch3DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        oldX = centerX;
        oldY = centerY;
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();
        initZDepth = ZDepth.getZDepthWithAttributeValue(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (x < 0)
            x = 0;
        if (x > centerX * 2)
            x = centerX * 2;
        if (y < 0)
            y = 0;
        if (y > centerY * 2)
            y = centerY * 2;
        if (event.getAction() != MotionEvent.ACTION_UP && oldX == x && oldY == y)
            return true;
        long duration = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                down = false;
                canMove = false;
                rotation = null;
                mInitialTouchX = (int) event.getX();
                mInitialTouchY = (int) event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int dx = (int) (event.getX() - mInitialTouchX);
                final int dy = (int) (event.getY() - mInitialTouchY);
                if (!canMove) {
                    canMove = (Math.abs(dx) > mTouchSlop && (Math.abs(dx) >= Math.abs(dy))) || (Math.abs(dy) > mTouchSlop && (Math.abs(dy) >= Math.abs(dx)));
                } else {
                    setHapticFeedbackEnabled(false);
                    if (!down) {
                        down = true;
                        duration = (long) ((float) (Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) / (float) (Math.pow(centerX, 2) + Math.pow(centerY, 2)) * 150f);
                        clearAnimation();
                        rotation = new RotateAnimation(getContext(), oldX, oldY, x, y, centerX, centerY, 0, mTouchLevel);
                        rotation.setFillAfter(true);
                        rotation.setDuration(duration);
                        startAnimation(rotation);
                        setAttrZDepthAnimDuration(duration);
                        changeZDepth(getZDepth(x, y));
                    } else {
                        clearAnimation();
                        rotation.setTouchX(x);
                        rotation.setTouchY(y);
                        rotation.setOldX(oldX);
                        rotation.setOldY(oldY);
                        rotation.setDuration(0);
                        startAnimation(rotation);
                        setAttrZDepthAnimDuration(0);
                        changeZDepth(getZDepth(x, y));
                    }
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if (rotation == null)
                    return true;
                clearAnimation();
                duration = (long) ((float) (Math.pow(centerX - oldX, 2) + Math.pow(centerY - oldY, 2)) / (float) (Math.pow(centerX, 2) + Math.pow(centerY, 2)) * 300f);
                rotation.setTouchX(centerX);
                rotation.setTouchY(centerY);
                rotation.setOldX(oldX);
                rotation.setOldY(oldY);
                rotation.setDuration(duration);
                startAnimation(rotation);
                setAttrZDepthAnimDuration(duration);
                changeZDepth(initZDepth);
                setHapticFeedbackEnabled(true);
                break;
            }
            default:
                break;
        }
        oldX = x;
        oldY = y;
        return true;
    }

    private ZDepth getZDepth(int x, int y) {
        if (zDepth == null)
            zDepth = new ZDepth();
        float offsetYTop;
        float offsetYBottom;
        float offsetXLeft;
        float offsetXRight;
        offsetYTop = initZDepth.getOffsetYTopShadow() + (float) (y - centerY) / (float) centerY * (float) mTouchLevel;
        offsetYBottom = initZDepth.getOffsetYBottomShadow() - (float) (y - centerY) / (float) centerY * (float) mTouchLevel;
        offsetXLeft = initZDepth.getOffsetXLeftShadow() + (float) (x - centerX) / (float) centerX * (float) mTouchLevel;
        offsetXRight = initZDepth.getOffsetXRightShadow() - (float) (x - centerX) / (float) centerX * (float) mTouchLevel;
        zDepth.setParams(Math.max(0, offsetYTop), Math.max(0, offsetYBottom), Math.max(0, offsetXLeft), Math.max(0, offsetXRight));
        return zDepth;
    }
}
