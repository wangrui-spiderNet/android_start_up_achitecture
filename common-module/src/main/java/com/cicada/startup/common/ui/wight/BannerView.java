package com.cicada.startup.common.ui.wight;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cicada.startup.common.R;
import com.cicada.startup.common.domain.Banner;
import com.cicada.startup.common.ui.wight.indicator.CirclePagerIndicator;
import com.cicada.startup.common.utils.DeviceUtils;
import com.cicada.startup.common.utils.ListUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 轮播view
 * <p/>
 * Create time: 16/10/13 14:32
 *
 * @author liuyun.
 */
public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private Context context;
    private List<Banner> bannerList = new ArrayList<>();

    private Handler handler;

    private ViewPager viewPager;

    private TextView titleView;

    private CirclePagerIndicator indicator;

    private ScheduledExecutorService schedule;
    private BannerPagerAdapter pagerAdapter;
    private int currentItem;
    private boolean isMoving = false;
    private boolean isScroll = false;
    //    private ImageLoader mImageLoader;
//    private DisplayImageOptions mDisplayImageOptions;
    private BannerOnClickInterface bannerOnClickInterface;

    public void setBannerOnClickInterface(BannerOnClickInterface bannerOnClickInterface) {
        this.bannerOnClickInterface = bannerOnClickInterface;
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public BannerView(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        //mImageLoader = ImageLoader.getInstance();
        //mDisplayImageOptions = ImageLoaderUtils.initDisplayImageOptions(R.drawable.default_image_show_square);
        View rootView = LayoutInflater.from(context).inflate(R.layout.banner_item, this, true);
        viewPager = (ViewPager) rootView.findViewById(R.id.vp_banner);
        titleView = (TextView) rootView.findViewById(R.id.tv_title);
        indicator = (CirclePagerIndicator) rootView.findViewById(R.id.indicator);
        pagerAdapter = new BannerPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        indicator.bindViewPager(viewPager);
        RelativeLayout rlBanner = (RelativeLayout) rootView.findViewById(R.id.rl_banner);
        int width = DeviceUtils.getScreenWidth(context);
        int hight = width * 352 / 750;

        LayoutParams layoutParams = (LayoutParams) rlBanner.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = hight;
        rlBanner.setLayoutParams(layoutParams);

        new SmoothScroller(context).bingViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                viewPager.setCurrentItem(currentItem);
            }
        };
        schedule = Executors.newSingleThreadScheduledExecutor();
        schedule.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (!isMoving && !isScroll) {
                    currentItem = (currentItem + 1) % bannerList.size();
                    handler.obtainMessage().sendToTarget();
                }
            }
        }, 2, 4, TimeUnit.SECONDS);

    }

    public void loadBanner(List<Banner> banners) {
        if (ListUtils.isNotEmpty(banners)) {
            this.bannerList = banners;
            pagerAdapter.notifyDataSetChanged();
            indicator.notifyDataSetChanged();
        }

    }

    public void release() {
        if (!schedule.isShutdown()) {
            schedule.shutdown();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        isMoving = currentItem != position;
    }

    @Override
    public void onPageSelected(int position) {
        isMoving = false;
        currentItem = position;
        isScroll = false;
        Banner banner = bannerList.get(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        isMoving = state != ViewPager.SCROLL_STATE_IDLE;
        isScroll = state != ViewPager.SCROLL_STATE_IDLE;
    }


    private class BannerPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return bannerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = View.inflate(getContext(), R.layout.pager_banner, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_banner);
            final Banner banner = bannerList.get(position);
            Glide.with(context).load(banner.getImage()).error(R.drawable.default_image).into(imageView);
            container.addView(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != bannerOnClickInterface) {
                        bannerOnClickInterface.onBannerClick(banner);
                    }
                }
            });
            return view;
        }
    }


    private class SmoothScroller extends Scroller {
        private int mDuration = 1200; //

        public SmoothScroller(Context context) {
            super(context);
        }

        public SmoothScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void bingViewPager(ViewPager viewPager) {
            try {
                Field mScroller = null;
                mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                mScroller.set(viewPager, this);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public interface BannerOnClickInterface {
        void onBannerClick(Banner banner);
    }
}
