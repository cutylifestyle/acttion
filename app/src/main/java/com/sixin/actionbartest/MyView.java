package com.sixin.actionbartest;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class MyView extends View {

    private static final String TAG = MyView.class.getName();

    private int mDownX;
    private int mDownY;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "MyView");
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        Log.d(TAG, "layout");
        super.layout(l, t, r, b);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG, "draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x ;
                mDownY = y ;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mDownX;
                int offsetY = y - mDownY;
//                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layout(getLeft() + offsetX,getTop() + offsetY , getRight() + offsetX , getBottom() + offsetY);
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);
//                lp.leftMargin = getLeft() + offsetX;
//                lp.topMargin = getTop() + offsetY;
//                setLayoutParams(lp);
                scrollBy(offsetX, offsetY);
                break;

        }
        return true;
    }
}
