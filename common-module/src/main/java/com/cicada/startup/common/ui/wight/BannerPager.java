package com.cicada.startup.common.ui.wight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.utils.ScreenUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 循环滚动ViewPager
 *
 * @author zyh
 */

@SuppressLint("ClickableViewAccessibility")
public class BannerPager extends ViewPager {
    private long mScrollTime;// 滚动间隔时间
    private Timer mTimer;
    private boolean canScroll;// 是否能自动滚动
    OnSingleTouchListener onSingleTouchListener;
    /**
     * 触摸时按下的点
     **/
    PointF downP = new PointF();
    /**
     * 触摸时当前的点
     **/
    PointF curP = new PointF();
    PointF movep = new PointF();
    private boolean isIntercept = true;
    private boolean autoScroll = true;

    /**
     * 创建点击事件接口
     * <p>
     * 因为在ViewPager的onTouchEvent中我对onDown进行了操作，
     * 进行了操作后就无法将touch事件继续往下传给onClick和其内部控件的任何事件，
     * 所以自己做了判断，做了个singleTouch来实现点击的事件
     */
    public interface OnSingleTouchListener {
        public void onSingleTouch(int position);
    }

    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }


    public BannerPager(Context context) {
        super(context);
        initParams();
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams();
    }

    public BannerPager(Context context, long mScrollTime, boolean canScroll) {
        super(context);
        this.mScrollTime = mScrollTime;
        this.canScroll = canScroll;
    }

    /**
     * 初始化成员变量参数
     */
    private void initParams() {
        mScrollTime = 4000;
        canScroll = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // 当拦截触摸事件到达此位置的时候，返回true，
        // 说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent

        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // 每次进行onTouch事件都记录当前的按下的坐标
        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            // 记录按下时候的坐标
            // 切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            //            getParent().requestDisallowInterceptTouchEvent(true);
        }

        //		 if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
        //			 // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
        //			 movep.x = arg0.getX();
        //			 movep.y = arg0.getY();
        //			 float deltaX = Math.abs(downP.x - movep.x);
        //			 float deltaY = Math.abs(downP.y - movep.y);
        //			 if(deltaX>deltaY){
        //				 getParent().requestDisallowInterceptTouchEvent(true);
        //				 return true;
        //			 }
        //		 }

        if (arg0.getAction() == MotionEvent.ACTION_OUTSIDE) {

        }
        if (arg0.getAction() == MotionEvent.ACTION_CANCEL) {

        }
        if (arg0.getAction() == MotionEvent.ACTION_UP) {
            // 在up时判断是否按下和松手的坐标为一个点
            // 如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
            curP.x = arg0.getX();
            curP.y = arg0.getY();
            float deltaX = Math.abs(downP.x - curP.x);
            float deltaY = Math.abs(downP.y - curP.y);

            if (deltaX < 5 && deltaY < 5) {
                int position = getCurrentItem();
                if (isIntercept) {
                    onSingleTouch(position);
                    return true;
                }
            }
        }

        return super.onTouchEvent(arg0);
    }

    /**
     * 单击
     */
    public void onSingleTouch(int item) {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch(item);
        }
    }

    /**
     * 添加指示标布局
     *
     * @param act
     * @param parent 指示标所在布局
     * @param size   指示标个数
     * @param drawId 图标资源图片，必须用Selector，进行图片的切换
     */
    @SuppressLint("ClickableViewAccessibility")
    public void setOvalLayout(final Activity act, final ViewGroup parent, final int size, int drawId) {
        if (parent == null || size == 0) {
            return;
        }
        // 防止多次调用产生过多的小点
        parent.removeAllViews();

        // 设置图标布局
        int w = ScreenUtils.dip2px(AppContext.getContext(), 5);
        int padding = ScreenUtils.dip2px(AppContext.getContext(), 2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w + padding * 2, w);
        // 初始化所有指示标图片
        if (size > 0) {
            if(size != 1) {
                for (int i = 0; i < size; i++) {
                    ImageView iv = new ImageView(act);
                    iv.setPadding(padding, 0, padding, 0);
                    iv.setLayoutParams(lp);
                    iv.setImageResource(drawId);
                    iv.setSelected(false);
                    parent.addView(iv);
                }
                // 选中第一个图标
                parent.getChildAt(0).setSelected(true);
            }
        }


        // 设置切换
        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (size > 1) {
                    for (int i = 0; i < size; i++) {
                        parent.getChildAt(i).setSelected(false);
                    }
                    parent.getChildAt(arg0 % size).setSelected(true);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (!canScroll) {
                    return false;
                }
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    if (autoScroll) {
                        startScroll(act);
                    } else {
                        setCurrentItem(getCurrentItem() + 1);
                    }
                } else {
                    stopScroll();
                }
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOvalLayout(final Activity act,
                              final int size, final TextView tv) {
        if (size == 0) {
            return;
        }
        tv.setText(1 + "/" + size);
        // 设置切换
        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                tv.setText((arg0 % size + 1) + "/" + size);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (!canScroll) {
                    return false;
                }
                if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    if (autoScroll) {
                        startScroll(act);
                    } else {
                        setCurrentItem(getCurrentItem() + 1);
                    }
                } else {
                    stopScroll();
                }
                return false;
            }
        });
    }

    /**
     * 开始滚动
     *
     * @param act BannerPager所在的页面
     */
    public void startScroll(final Activity act) {
        if (!isCanScroll()) {// 能否滚动
            return;
        }
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            public void run() {
                act.runOnUiThread(new Runnable() {
                    public void run() {
                        setCurrentItem(getCurrentItem() + 1);
                    }
                });
            }
        }, mScrollTime, mScrollTime);
    }

    /**
     * 停止滚动
     */
    public void stopScroll() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 是否能自动滚动
     *
     * @return
     */
    public boolean isCanScroll() {
        return canScroll;
    }

    /**
     * 设置是否自动滚动
     *
     * @param canScroll 是否自动滚动
     */
    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    /**
     * 获取自动滚动间隔时间
     *
     * @return
     */
    public long getmScrollTime() {
        return mScrollTime;
    }

    /**
     * 设置自动滚动间隔时间
     *
     * @param mScrollTime 自动滚动间隔时间
     */
    public void setmScrollTime(long mScrollTime) {
        this.mScrollTime = mScrollTime;
    }


    public boolean isIntercept() {
        return isIntercept;
    }

    public void setIntercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }

    public boolean isAutoScroll() {
        return autoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public void setisScroll(boolean flag) {
        getParent().requestDisallowInterceptTouchEvent(flag);
    }
}
