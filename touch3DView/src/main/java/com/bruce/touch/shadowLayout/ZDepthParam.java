package com.bruce.touch.shadowLayout;

import android.content.Context;

public class ZDepthParam {

    public float mOffsetYTopShadowPx; // px
    public float mOffsetYBottomShadowPx; // px
    public float mOffsetXLeftShadowPx; // px
    public float mOffsetXRightShadowPx; // px


    public void initZDepth(Context context, ZDepth zDepth) {
        mOffsetYTopShadowPx = zDepth.getOffsetYTopShadowPx(context);
        mOffsetYBottomShadowPx = zDepth.getOffsetYBottomShadowPx(context);
        mOffsetXLeftShadowPx = zDepth.getOffsetXLeftShadow(context);
        mOffsetXRightShadowPx = zDepth.getOffsetXRightShadow(context);
    }

}
