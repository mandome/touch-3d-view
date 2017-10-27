package com.bruce.touch.utils;

import android.content.Context;

public class DisplayUtils {

    public static int convertDpToPx(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static float convertPxToDp(Context context, float px)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (px / scale);
    }
}
