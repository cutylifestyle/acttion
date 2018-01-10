package com.sixin.actionbartest.indicator;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

/**
 * @author zhou
 * 指示器的职责在于显示所有的标签以及当前标签下对应的视图
 */

public interface PageIndicator extends ViewPager.OnPageChangeListener{

    /**
     * 设置与指示器关联的viewPager
     * @param viewPager {@link ViewPager}
     * */
    void setViewPager(@NonNull ViewPager viewPager);

    /**
     * 设置与指示器关联的viewPager
     * @param viewPager {@link ViewPager}
     * @param initialPosition 初始化标签的位置
     * */
    void setViewPager(@NonNull ViewPager viewPager, int initialPosition);

    /**
     *设置当前的标签
     * @param item 目标标签
     * */
    void setCurrentItem(int item);

    /**
     * 当ViewPager的数据发生变化，更新标签
     * */
    void notifyDataSetChanged();

}
