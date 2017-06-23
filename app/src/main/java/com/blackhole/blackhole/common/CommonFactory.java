package com.blackhole.blackhole.common;

/**
 * Created by YZQ on 2017/5/31.
 */

import android.content.Context;

/**
 * add constant here
 */
public class CommonFactory {
    public static final String EDITOR_EMPTY_TEXT = "Please Input Text";
    public static final String MESSAGE_SEND_FAIL = "Message send fail";
    public static final String MESSAGE_SEND_SUCCESS = "Message send successfully";

    public static final int ColorPalette[] = new int[]{
            0xFF000000, 0xFFF44336, 0xFFE91E63, 0xFF9C27B0, 0xFF673AB7, 0xFF3F51B5,
            0xFF2196F3, 0xFF03A9F4, 0xFF00BCD4, 0xFF009688, 0xFF4CAF50, 0xFF8BC34A,
            0xFFCDDC39, 0xFFFFEB3B, 0xFFFFC107, 0xFFFF9800, 0xFFFF5722, 0xFF795548
    };

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
