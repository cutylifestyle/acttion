package com.sixin.actionbartest;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


public class MyTextView extends AppCompatTextView {

    private static final String TAG = MyTextView.class.getName();

    private int mDownX;
    private int mDownY;


    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
