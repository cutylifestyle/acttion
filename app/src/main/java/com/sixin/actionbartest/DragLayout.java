package com.sixin.actionbartest;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;



public class DragLayout extends LinearLayout {

    private static final String TAG = DragLayout.class.getName();

    private ViewDragHelper mViewDragHelper;

    private View dragView1;
    private View dragView2;
    private View dragView3;

    private Point point = new Point();

    public DragLayout(Context context) {
        this(context,null);
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new DragCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

       @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        dragView1 = getChildAt(0);
        dragView2 = getChildAt(1);
        dragView3 = getChildAt(2);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        point.x = dragView2.getLeft();
        point.y = dragView2.getTop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    private class DragCallback extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == dragView1 || child == dragView2;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if(releasedChild == dragView2){
                mViewDragHelper.settleCapturedViewAt(point.x, point.y);
                invalidate();
            }
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mViewDragHelper.captureChildView(dragView3,pointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getWidth() - child.getWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getHeight() - child.getHeight();
        }
    }
}
