package com.sixin.actionbartest.indicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
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
    // TODO: 2018/1/11 修改linearLayout的宽高  移动卡顿的问题
    private static final String TAG = TabPageIndicator.class.getName();
    private ViewPager mViewPager;

    private static final float DEFAULT_TEXT_SIZE = 16.0f;
    private static final int CRITICAL_VALUE = 4;

    private final int mScreenWidth = DisplayUtil.getDisplayWidth(getContext());
    private final int mTabViewWidth = mScreenWidth / 4;

    public TabPageIndicator(Context context) {
        this(context, null);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        initChildView(context);
    }

    // TODO: 2018/1/11 view中的post方法的作用  removeCallBacks()方法
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        resetTabViewLayoutParams();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setViewPager(@NonNull ViewPager viewPager) {
        final PagerAdapter pagerAdapter = viewPager.getAdapter();
        if(pagerAdapter == null){
           throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        if(mViewPager == viewPager){
            return;
        }
        if(mViewPager == null){
            mViewPager = viewPager;
        }else{
            mViewPager.addOnPageChangeListener(null);
            mViewPager = viewPager;
        }
        mViewPager.addOnPageChangeListener(this);
        addTabViews();
    }

    @Override
    public void notifyDataSetChanged() {
        //scrollView的子view：linearLayout
        LinearLayout childView = (LinearLayout) getChildAt(0);
        childView.removeAllViews();
        addTabViews();
        // TODO: 2018/1/11 requestLayout的作用 有没有必要写
        requestLayout();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        changeCurrentItem(position);
        scrollToTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initChildView(Context context) {
        TabLayout childView = new TabLayout(context);
        childView.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        addView(childView);
    }

    private void addTabViews(){
        //scrollView的子view：linearLayout
        TabLayout childView = (TabLayout) getChildAt(0);

        int viewPagerChildCount = mViewPager.getAdapter().getCount();
        for(int i = 0 ; i < viewPagerChildCount ; i++){
            TabView tabView = new TabView(getContext());
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
        String title = (String) mViewPager.getAdapter().getPageTitle(i);
        if(title == null || "".equals(title)){
            throw new IllegalArgumentException("ViewPager must have title");
        }
        tabView.setText(mViewPager.getAdapter().getPageTitle(i));

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(i,true);
            }
        });
    }


    private void resetTabViewLayoutParams() {
        //scrollView的子view：linearLayout
        TabLayout childView = (TabLayout) getChildAt(0);
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

    private void changeCurrentItem(int position) {
        TabLayout childView = (TabLayout) getChildAt(0);
        int tabViewCount = childView.getChildCount();
        for(int i = 0 ; i < tabViewCount ; i++){
            TabView tabView = (TabView) childView.getChildAt(i);
            if(i == position){
                tabView.switchBottomDrawable(true);
            }else{
                tabView.switchBottomDrawable(false);
            }
        }
    }

    private void scrollToTab(int position) {
        TabLayout childView = (TabLayout) getChildAt(0);
        View tabView = childView.getChildAt(position);
        int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth())/2;
        smoothScrollTo(scrollPos,0);
    }

}
