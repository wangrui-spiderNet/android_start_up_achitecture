package com.cicada.startup.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cicada.startup.R;
import com.cicada.startup.ui.fragment.GirlListFragment;
import com.cicada.startup.ui.fragment.ZhihuListFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * MainActivity.java
 * <p>
 * Created by lijiankun on 17/7/4.
 */
public class MainActivity extends BaseActivity {

    private List<Fragment> mFragmentList = new ArrayList<>();

    private ViewPager mViewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mFragmentList.add(new GirlListFragment());
        mFragmentList.add(new ZhihuListFragment());

        mViewPager = (ViewPager) findViewById(R.id.vp_home);
        mViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));

        BottomNavigationBar navigationBar = (BottomNavigationBar)findViewById(R.id.bottom_navigation_bar);
        navigationBar.setTabSelectedListener(new MainOnTabSelectedListener());
        navigationBar.addItem(new BottomNavigationItem(R.drawable.ic_favorite, "Girl"))
                .addItem(new BottomNavigationItem(R.drawable.ic_grade, "Zhihu"))
                .initialise();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        initToolbar(toolbar, false, R.string.app_name);
    }

    private class MainOnTabSelectedListener implements BottomNavigationBar.OnTabSelectedListener {

        @Override
        public void onTabSelected(int position) {
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onTabUnselected(int position) {
        }

        @Override
        public void onTabReselected(int position) {
        }
    }

    private class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        private MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
