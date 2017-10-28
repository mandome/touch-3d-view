package com.bruce.simple;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Bruce on 2017/7/4.
 */

public class MyRecyclerView extends RecyclerView {

    private static final int SCROLL_MODE_INIT = -1;
    private static final int SCROLL_MODE_DOWN = 0;
    private static final int SCROLL_MODE_NOT_INTERCEPT = 2;
    private static final int SCROLL_MODE_INTERCEPT = 3;
    private int mInitialTouchX, mInitialTouchY;
    private int mTouchSlop;
    private int startScroll = SCROLL_MODE_INIT;
    private boolean isScrollTop;
    private boolean isScrollBottom;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public void setScrollingTouchSlop(int slopConstant) {
        super.setScrollingTouchSlop(slopConstant);
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        switch (slopConstant) {
            case TOUCH_SLOP_DEFAULT:
                mTouchSlop = vc.getScaledTouchSlop();
                break;
            case TOUCH_SLOP_PAGING:
                mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(vc);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startScroll = SCROLL_MODE_DOWN;
                mInitialTouchX = (int) e.getX();
                mInitialTouchY = (int) e.getY();
                isScrollTop = !canScrollVertically(-1);
                isScrollBottom = !canScrollVertically(1);
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_MOVE: {
                if (startScroll == SCROLL_MODE_NOT_INTERCEPT) {
                    return false;
                } else if (startScroll == SCROLL_MODE_INTERCEPT) {
                    return true;
                }
                final int x = (int) e.getX();
                final int y = (int) e.getY();
                if (getScrollState() != SCROLL_STATE_DRAGGING) {
                    final int dx = x - mInitialTouchX;
                    final int dy = y - mInitialTouchY;
                    if (Math.abs(dy) > mTouchSlop && (Math.abs(dy) >= Math.abs(dx))) {
                        if (dy > 0 && isScrollTop) {
                            startScroll = SCROLL_MODE_NOT_INTERCEPT;
                        } else if (dy < 0 && isScrollBottom) {
                            startScroll = SCROLL_MODE_NOT_INTERCEPT;
                        } else
                            startScroll = SCROLL_MODE_INTERCEPT;
                    } else if (Math.abs(dx) > mTouchSlop && (Math.abs(dx) >= Math.abs(dy))) {
                        startScroll = SCROLL_MODE_NOT_INTERCEPT;
                    } else {
                        return super.onInterceptTouchEvent(e);
                    }
                    return startScroll != SCROLL_MODE_NOT_INTERCEPT;
                }
                return super.onInterceptTouchEvent(e);
            }
            default:
                return super.onInterceptTouchEvent(e);
        }
    }
}
