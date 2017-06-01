package com.blackhole.blackhole.util;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Liam on 2017/6/1
 */

public class CustomDivider extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int mWindowWidth;

    public CustomDivider(Drawable divider, int windowWidth) {
        mDivider = divider;
        mWindowWidth = windowWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }

        outRect.top = mDivider.getIntrinsicHeight();
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int divideLeft = parent.getPaddingLeft() + mWindowWidth / 3;
        int divideRight = parent.getWidth() - parent.getPaddingRight() - mWindowWidth / 3;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int divideTop = child.getBottom() + params.bottomMargin;
            int divideBottom = divideTop + mDivider.getIntrinsicHeight();

            mDivider.setBounds(divideLeft, divideTop, divideRight, divideBottom);
            mDivider.draw(canvas);
        }
    }
}
