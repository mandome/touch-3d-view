package com.bruce.touch.shadow;

import android.graphics.Canvas;

import com.bruce.touch.shadowLayout.ZDepthParam;

public interface Shadow {
    public void setParameter(ZDepthParam parameter, int left, int top, int right, int bottom, int color);
    public void onDraw(Canvas canvas);
}
