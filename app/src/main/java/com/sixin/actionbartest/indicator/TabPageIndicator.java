package com.sixin.actionbartest.indicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixin.actionbartest.DisplayUtil;

/**
 * @author zhou
 */

public class TabPageIndicator extends HorizontalScrollView implements PageIndicator {

    private ViewPager mViewPager;

    private static final float DEFAULT_TEXT_SIZE = 16.0f;
    private static final int CRITICAL_VALUE = 4;

    private final int mScreenWidth = DisplayUtil.getDisplayWidth(getContext());
    private final int mTabViewWidth = mScreenWidth / 4;

    public TabPageIndicator(Context context) {
        super(context);
        config();
        initChildView(context);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        config();
        initChildView(context);
    }

    public TabPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        config();
        initChildView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        resetTabViewLayoutParams();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // TODO: 2018/1/10 这个方法需要修改 ，可能存在漏洞
    @Override
    public void setViewPager(@NonNull ViewPager viewPager) {
        if(mViewPager != viewPager){
            mViewPager = viewPager;
        }
        addTabViews();
    }

    @Override
    public void setViewPager(@NonNull ViewPager viewPager, int initialPosition) {

    }

    @Override
    public void setCurrentItem(int item) {

    }

    @Override
    public void setOnPageChangeListener(@NonNull ViewPager.OnPageChangeListener listener) {

    }

    @Override
    public void notifyDataSetChanged() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void config(){
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
    }

    private void initChildView(Context context) {
        LinearLayout childView = new LinearLayout(context);
        childView.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        addView(childView);
    }

    private void addTabViews(){
        //scrollView的子view：linearLayout
        LinearLayout childView = (LinearLayout) getChildAt(0);

        int viewPagerChildCount = mViewPager.getAdapter().getCount();
        for(int i = 0 ; i < viewPagerChildCount ; i++){
            TextView tabView = new TextView(getContext());
            initTabView(tabView, i);
            childView.addView(tabView,i);
        }
    }

    // TODO: 2018/1/10 有些设置提供自定义属性  考虑内容过长的处理方式
    private void initTabView(TextView tabView, final int i) {
        tabView.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        tabView.setGravity(Gravity.CENTER);
        tabView.setTextSize(DEFAULT_TEXT_SIZE);
//        tabView.setEllipsize(TextUtils.TruncateAt.END);
        tabView.setSingleLine(true);
        tabView.setText(mViewPager.getAdapter().getPageTitle(i));
    }


    private void resetTabViewLayoutParams() {
        //scrollView的子view：linearLayout
        LinearLayout childView = (LinearLayout) getChildAt(0);
        int tabViewCount = childView.getChildCount();
        if(tabViewCount < CRITICAL_VALUE){
            for(int i = 0 ; i < tabViewCount ; i++){
                View tabView = childView.getChildAt(i);
                tabView.setLayoutParams(new LinearLayout.LayoutParams(
                        mScreenWidth / tabViewCount,
                        LayoutParams.WRAP_CONTENT));
            }
        }else{
            for(int i = 0 ; i < tabViewCount ; i++){
                View tabView = childView.getChildAt(i);
                tabView.setLayoutParams(new LinearLayout.LayoutParams(
                        mTabViewWidth,
                        LayoutParams.WRAP_CONTENT));
            }
        }
    }
}
