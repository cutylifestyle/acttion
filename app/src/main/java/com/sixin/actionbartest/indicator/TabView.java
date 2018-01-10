package com.sixin.actionbartest.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.sixin.actionbartest.R;

/**
 * @author zhou
 */

public class TabView extends android.support.v7.widget.AppCompatTextView {

    private Drawable mBottomDrawable;

    public TabView(Context context) {
        super(context);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBottomDrawable = context.getResources().getDrawable(R.drawable.line);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBottomDrawable.setBounds(0,0,getWidth(),15);
        setCompoundDrawables(null,null,null, mBottomDrawable);
        super.onDraw(canvas);
    }

}
