package com.sixin.actionbartest;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zhou
 */

public class LeftDrawerLayout extends ViewGroup {


    private static final int MIN_DRAWER_MARGIN = 64;//dp
    private int mMinDrawerMargin;

    private ViewDragHelper mViewDragHepler;

    private View leftMenuView;
    private View contentView;

    public LeftDrawerLayout(Context context) {
        super(context);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        float density = getResources().getDisplayMetrics().density;
        mMinDrawerMargin = (int) (MIN_DRAWER_MARGIN * density +0.5f);

        mViewDragHepler = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return false;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize,heightSize);

        leftMenuView = getChildAt(1);
        MarginLayoutParams lp = (MarginLayoutParams) leftMenuView.getLayoutParams();
        int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec, mMinDrawerMargin + lp.leftMargin + lp.rightMargin, lp.width);
        int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec, mMinDrawerMargin + lp.topMargin + lp.bottomMargin, lp.height);
        leftMenuView.measure(drawerWidthSpec, drawerHeightSpec);

        contentView = getChildAt(0);
        MarginLayoutParams lp1 = (MarginLayoutParams) contentView.getLayoutParams();
        int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSize - lp1.leftMargin - lp1.rightMargin, MeasureSpec.EXACTLY);
        int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize - lp1.topMargin - lp1.bottomMargin, MeasureSpec.EXACTLY);
        contentView.measure(contentWidthSpec,contentHeightSpec);

    }

    public LeftDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHepler.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        MarginLayoutParams mp = (MarginLayoutParams) contentView.getLayoutParams();
        contentView.layout(mp.leftMargin,mp.topMargin,
                mp.leftMargin+contentView.getMeasuredWidth(),
                mp.topMargin + contentView.getMeasuredHeight());


        MarginLayoutParams lp = (MarginLayoutParams) leftMenuView.getLayoutParams();
        int menuWidth = leftMenuView.getMeasuredWidth();

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
