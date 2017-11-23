/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 * 
 * Project Name: ZhiLiao
 * $Id: AnimationsUtils.java 2014-7-18 下午1:49:38 $ 
 */
package com.cicada.startup.common.utils;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

/**
 * 动画效果集合，可以共用的函数集合
 * <p/>
 * Create time: 16/7/6 10:48
 *
 * @author liuyun.
 */
public class AnimationsUtils {
    @SuppressWarnings("unused")
    private Context mContext;
    /**
     * 翻转动画方式：true——上下翻转；false——左右翻转
     */
    private static boolean FLAGTOTATEWAY = false;

    /**
     * 电视开发板ANDROID系统ID TVA = 83804bbb0540d824 TVA = 6edb229a81a59b5e
     */
    public AnimationsUtils(Context context) {
        this.mContext = context;
    }

    /**
     * 给view设置平移动画,x,y坐标均是相对父布局而言的
     *
     * @param view
     * @param pivotFromX    X点坐标值,0.0代表(父布局的左边),也可设置大于1的任意值
     * @param pivotToX      X点坐标值,1.0代表(父布局的右边),也可设置大于1的任意值
     * @param pivotFromY    Y点坐标值,0.0代表(父布局的上边),也可设置大于1的任意值
     * @param pivotToY      Y点坐标值,1.0代表(父布局的下边),也可设置大于1的任意值
     * @param intDuration   动画执行的时间
     * @param boolFillAfter 执行完动画之后是否保持最后的位置状态
     */
    public static void setTranslateAnimationToParent(View view, float pivotFromX, float pivotToX, float pivotFromY, float pivotToY, int intDuration, boolean boolFillAfter) {
        TranslateAnimation mAnmation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, pivotFromX, Animation.RELATIVE_TO_PARENT, pivotToX, Animation.RELATIVE_TO_PARENT, pivotFromY, Animation.RELATIVE_TO_PARENT, pivotToY);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        // mAnmation.setRepeatMode(Animation.RESTART);
        // mAnmation.setRepeatCount(intRepeatCount);
        // mAnmation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        view.startAnimation(mAnmation);
    }

    /**
     * 创建一个相对父布局平移的动画的对象
     *
     * @param pivotFromX    X点坐标值,0.0代表(父布局的左边),也可设置大于1的任意值
     * @param pivotToX      X点坐标值,1.0代表(父布局的右边),也可设置大于1的任意值
     * @param pivotFromY    Y点坐标值,0.0代表(父布局的上边),也可设置大于1的任意值
     * @param pivotToY      Y点坐标值,1.0代表(父布局的下边),也可设置大于1的任意值
     * @param intDuration   动画执行的时间
     * @param boolFillAfter 执行完动画之后是否保持最后的位置状态
     */
    public static TranslateAnimation getTranslateAnimationToParent(float pivotFromX, float pivotToX, float pivotFromY, float pivotToY, int intDuration, boolean boolFillAfter) {
        TranslateAnimation mAnmation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, pivotFromX, Animation.RELATIVE_TO_PARENT, pivotToX, Animation.RELATIVE_TO_PARENT, pivotFromY, Animation.RELATIVE_TO_PARENT, pivotToY);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        // mAnmation.setRepeatMode(Animation.RESTART);
        // mAnmation.setRepeatCount(intRepeatCount);
        // mAnmation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        return mAnmation;
    }

    /**
     * 给view设置平移动画,x,y坐标均是相对自己而言的
     *
     * @param view
     * @param pivotFromX    X点坐标值,0.0代表(自己的左边),也可设置大于1的任意值
     * @param pivotToX      X点坐标值,1.0代表(自己的右边),也可设置大于1的任意值
     * @param pivotFromY    Y点坐标值,0.0代表(自己的上边),也可设置大于1的任意值
     * @param pivotToY      Y点坐标值,1.0代表(自己的下边),也可设置大于1的任意值
     * @param intDuration   动画执行的时间
     * @param boolFillAfter 执行完动画之后是否保持最后的位置状态
     */
    public static void setTranslateAnimationToSelf(View view, float pivotFromX, float pivotToX, float pivotFromY, float pivotToY, int intDuration, boolean boolFillAfter) {
        TranslateAnimation mAnmation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, pivotFromX, Animation.RELATIVE_TO_SELF, pivotToX, Animation.RELATIVE_TO_SELF, pivotFromY, Animation.RELATIVE_TO_SELF, pivotToY);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        // mAnmation.setRepeatMode(Animation.RESTART);
        // mAnmation.setRepeatCount(intRepeatCount);
        // mAnmation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        view.startAnimation(mAnmation);
    }

    /**
     * 创建一个相对自身布局平移的动画的对象
     *
     * @param pivotFromX    X点坐标值,0.0代表(自己的左边),也可设置大于1的任意值
     * @param pivotToX      X点坐标值,1.0代表(自己的右边),也可设置大于1的任意值
     * @param pivotFromY    Y点坐标值,0.0代表(自己的上边),也可设置大于1的任意值
     * @param pivotToY      Y点坐标值,1.0代表(自己的下边),也可设置大于1的任意值
     * @param intDuration   动画执行的时间
     * @param boolFillAfter 执行完动画之后是否保持最后的位置状态
     */
    public static TranslateAnimation getTranslateAnimationToSelf(float pivotFromX, float pivotToX, float pivotFromY, float pivotToY, int intDuration, boolean boolFillAfter) {
        TranslateAnimation mAnmation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, pivotFromX, Animation.RELATIVE_TO_SELF, pivotToX, Animation.RELATIVE_TO_SELF, pivotFromY, Animation.RELATIVE_TO_SELF, pivotToY);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        // mAnmation.setRepeatMode(Animation.RESTART);
        // mAnmation.setRepeatCount(intRepeatCount);
        // mAnmation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        return mAnmation;
    }

    /**
     * 给view设置透明度改变动画
     *
     * @param view          要设置动画的view
     * @param fromAlpha     开始的透明度(从0.0到1.0),0.0为透明,1.0为不透明
     * @param toAlpha       结束的透明度(从0.0到1.0),0.0为透明,1.0为不透明
     * @param intDuration   动画执行的时间
     * @param boolFillAfter 执行完动画之后是否保持最后的位置状态
     */
    public static void setAlphaAnimation(View view, float fromAlpha, float toAlpha, int intDuration, boolean boolFillAfter) {
        AlphaAnimation mAnmation = new AlphaAnimation(fromAlpha, toAlpha);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        // mAnmation.setRepeatMode(Animation.RESTART);
        // mAnmation.setRepeatCount(intRepeatCount);
        // mAnmation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        view.startAnimation(mAnmation);
    }

    /**
     * 创建一个透明度动画的对象
     *
     * @param fromAlpha     开始的透明度(从0.0到1.0),0.0为透明,1.0为不透明
     * @param toAlpha       结束的透明度(从0.0到1.0),0.0为透明,1.0为不透明
     * @param intDuration   动画执行的时间
     * @param boolFillAfter 执行完动画之后是否保持最后的位置状态
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, int intDuration, boolean boolFillAfter) {
        AlphaAnimation mAnmation = new AlphaAnimation(fromAlpha, toAlpha);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        // mAnmation.setRepeatMode(Animation.RESTART);
        // mAnmation.setRepeatCount(intRepeatCount);
        // mAnmation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        return mAnmation;
    }

    /**
     * 给view设置旋转动画
     *
     * @param view           要设置动画的view
     * @param fromAngle      开始角度,范围从0~360
     * @param toAngle        结束角度,范围从0~360
     * @param pivotX         X点坐标值,0.0代表(左边),也可以设置具体的像素值
     * @param pivotY         Y点坐标值,1.0代表(上方),也可以设置具体的像素值
     * @param intDuration    动画执行的时间
     * @param intRepeatCount 动画重复次数,-1重复动画下去
     * @param boolFillAfter  执行完动画之后是否保持最后的位置状态
     */
    public static void setRotateAnimation(View view, float fromAngle, float toAngle, float pivotX, float pivotY, int intDuration, int intRepeatCount, boolean boolFillAfter) {
        RotateAnimation mAnmation = new RotateAnimation(fromAngle, toAngle, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        if (intRepeatCount != 1) {
            mAnmation.setRepeatMode(Animation.RESTART);
            mAnmation.setRepeatCount(intRepeatCount);
        }
        // mAnmation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        mAnmation.setInterpolator(new LinearInterpolator());
        view.startAnimation(mAnmation);
    }

    /**
     * 创建一个旋转动画的对象
     *
     * @param fromAngle      开始角度,范围从0~360
     * @param toAngle        结束角度,范围从0~360
     * @param pivotX         X点坐标值,0.0代表(左边),也可以设置具体的像素值
     * @param pivotY         Y点坐标值,1.0代表(上方),也可以设置具体的像素值
     * @param intDuration    动画执行的时间
     * @param intRepeatCount 动画重复次数,-1重复动画下去
     * @param boolFillAfter  执行完动画之后是否保持最后的位置状态
     */
    public static RotateAnimation getRotateAnimation(float fromAngle, float toAngle, float pivotX, float pivotY, int intDuration, int intRepeatCount, boolean boolFillAfter) {
        RotateAnimation mAnmation = new RotateAnimation(fromAngle, toAngle, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        if (intRepeatCount != 1) {
            mAnmation.setRepeatMode(Animation.RESTART);
            mAnmation.setRepeatCount(intRepeatCount);
        }
        // mAnmation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        mAnmation.setInterpolator(new LinearInterpolator());
        return mAnmation;
    }

    /**
     * 启动一个缩放动画效果
     *
     * @param view           要设置动画的view
     * @param ScaleFromX     X点坐标值,0.0代表(隐藏自己),也可设置大于1的任意值
     * @param ScaleToX       X点坐标值,1.0代表(原始大小),也可设置大于1的任意值
     * @param ScaleFromY     Y点坐标值,0.0代表(隐藏自己),也可设置大于1的任意值
     * @param ScaleToY       Y点坐标值,1.0代表(原始大小),也可设置大于1的任意值
     * @param pivotX         X点坐标值,0.0代表(左边),也可以设置具体的像素值
     * @param pivotY         Y点坐标值,1.0代表(上方),也可以设置具体的像素值
     * @param intDuration    动画执行的时间
     * @param intRepeatCount 动画重复次数,-1重复动画下去
     * @param boolFillAfter  执行完动画之后是否保持最后的位置状态
     */
    public static void setScaleAnimation(View view, float ScaleFromX, float ScaleToX, float ScaleFromY, float ScaleToY, float pivotX, float pivotY, int intDuration, int intRepeatCount, boolean boolFillAfter) {
        ScaleAnimation mAnmation = new ScaleAnimation(ScaleFromX, ScaleToX, ScaleFromY, ScaleToY, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        if (intRepeatCount != 1) {
            mAnmation.setRepeatMode(Animation.RESTART);
            mAnmation.setRepeatCount(intRepeatCount);
        }
        // translateAnimation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        view.startAnimation(mAnmation);
    }

    /**
     * 创建一个缩放动画的对象
     *
     * @param ScaleFromX     X点坐标值,0.0代表(隐藏自己),也可设置大于1的任意值
     * @param ScaleToX       X点坐标值,1.0代表(原始大小),也可设置大于1的任意值
     * @param ScaleFromY     Y点坐标值,0.0代表(隐藏自己),也可设置大于1的任意值
     * @param ScaleToY       Y点坐标值,1.0代表(原始大小),也可设置大于1的任意值
     * @param pivotX         X点坐标值,0.0代表(左边),也可以设置具体的像素值
     * @param pivotY         Y点坐标值,1.0代表(上方),也可以设置具体的像素值
     * @param intDuration    动画执行的时间
     * @param intRepeatCount 动画重复次数,-1重复动画下去
     * @param boolFillAfter  执行完动画之后是否保持最后的位置状态
     */
    public static ScaleAnimation getScaleAnimation(float ScaleFromX, float ScaleToX, float ScaleFromY, float ScaleToY, float pivotX, float pivotY, int intDuration, int intRepeatCount, boolean boolFillAfter) {
        ScaleAnimation mAnmation = new ScaleAnimation(ScaleFromX, ScaleToX, ScaleFromY, ScaleToY, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
        mAnmation.setDuration(intDuration);
        mAnmation.setFillAfter(boolFillAfter);
        if (intRepeatCount != 1) {
            mAnmation.setRepeatMode(Animation.RESTART);
            mAnmation.setRepeatCount(intRepeatCount);
        }
        // translateAnimation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
        // com.webclient.R.anim.bounce_interpolator));
        return mAnmation;
    }

    /**
     * 翻转动画接口函数
     * <p>
     * 使用示例： <br>
     * StoneAnimations mStoneAnimations = new StoneAnimations(mContext);<br>
     * mStoneAnimations.applyRotation(0, 0, 90, view, true);
     * </p>
     *
     * @param position 位置
     * @param start    开始翻转角度
     * @param end      结束翻转角度
     * @param myView   需要执行翻转动画的View
     * @param flag     true：上下翻转； false：左右翻转
     */
    public void applyRotation(int position, float start, float end, View myView, boolean flag) {
        /** 翻转中心: X坐标 */
        final float CENTER_X = myView.getWidth() / 2.0f;
        /** 翻转中心: Y坐标 */
        final float CENTER_Y = myView.getHeight() / 2.0f;

        final setRotate3DAnimation rotation = new setRotate3DAnimation(start, end, CENTER_X, CENTER_Y, 200.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(position, myView));
        FLAGTOTATEWAY = flag;

        myView.startAnimation(rotation);
    }

    /**
     * 翻转动画监听类
     *
     * @author Stone
     */
    public final class DisplayNextView implements Animation.AnimationListener {
        /**
         * 位置：根据该位置判断View从0度选择到90度后还需要旋转的角度
         */
        private final int POSITION;
        /**
         * 执行动画效果的View
         */
        private final View myView;

        private DisplayNextView(int position, View myView) {
            POSITION = position;
            this.myView = myView;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            myView.post(new SwapViews(POSITION, myView));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    /**
     * 翻转动画类
     *
     * @author Stone
     */
    public final class SwapViews implements Runnable {
        /**
         * 位置
         */
        private final int POSITION;
        /**
         * 执行动画效果的View
         */
        private final View myView;

        public SwapViews(int position, View myView) {
            POSITION = position;
            this.myView = myView;
        }

        @Override
        public void run() {
            final float centerX = myView.getWidth() / 2.0f;
            final float centerY = myView.getHeight() / 2.0f;
            setRotate3DAnimation rotation;

            if (POSITION > -1) {
                myView.setVisibility(View.VISIBLE);
                myView.requestFocus();
                rotation = new setRotate3DAnimation(270, 360, centerX, centerY, 200.0f, false);
            } else {
                myView.setVisibility(View.GONE);
                rotation = new setRotate3DAnimation(90, 0, centerX, centerY, 200.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());

            myView.startAnimation(rotation);
        }
    }

    /**
     * 翻转动画基础类
     */
    public class setRotate3DAnimation extends Animation {
        /**
         * 翻转前角度
         */
        private final float FROM_DEGREES;
        /**
         * 翻转后角度
         */
        private final float TO_DEGREES;
        /**
         * 翻转中心: X坐标
         */
        private final float CENTER_X;
        /**
         * 翻转中心: Y坐标
         */
        private final float CENTER_Y;
        /**
         * 翻转深度
         */
        private final float DEPTH_Z;
        /**
         * 是否翻转
         */
        private final boolean REVERSE;
        private Camera mCamera;

        public setRotate3DAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, boolean reverse) {
            FROM_DEGREES = fromDegrees;
            TO_DEGREES = toDegrees;
            CENTER_X = centerX;
            CENTER_Y = centerY;
            DEPTH_Z = depthZ;
            REVERSE = reverse;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float fromDegrees = FROM_DEGREES;
            float degrees = fromDegrees + ((TO_DEGREES - fromDegrees) * interpolatedTime);

            final float centerX = CENTER_X;
            final float centerY = CENTER_Y;
            final Camera camera = mCamera;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (REVERSE) {
                camera.translate(0.0f, 0.0f, DEPTH_Z * interpolatedTime);
            } else {
                camera.translate(0.0f, 0.0f, DEPTH_Z * (1.0f - interpolatedTime));
            }
            if (FLAGTOTATEWAY) { // 上下翻转
                camera.rotateX(degrees);
            } else { // 左右翻转
                camera.rotateY(degrees);
            }
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

    /**
     * 调用3D翻转动画的代码
     * 将以下代码Copy到要用的Activity中、然后在用的地方使用applyRotation(0,0,90,mWebViewList);调用
     */
    public void set3DAnimation() {

        // /*****************************************************************
        // 功能函数部分
        // ***************************************************************************/
        // private void applyRotation(int position, float start, float end,View
        // myView) {
        // // Find the center of the container
        // final float centerX = myView.getWidth() / 2.0f;
        // final float centerY = myView.getHeight() / 2.0f;
        //
        // final Lomowall_myphoto_Rotate3D rotation =new
        // Lomowall_myphoto_Rotate3D(start, end, centerX, centerY, 310.0f,
        // true);
        // rotation.setDuration(500);
        // rotation.setFillAfter(true);
        // rotation.setInterpolator(new AccelerateInterpolator());
        // rotation.setAnimationListener(new DisplayNextView(position,myView));
        //
        // myView.startAnimation(rotation);
        // }
        //
        // private final class DisplayNextView implements
        // Animation.AnimationListener {
        // private final int mPosition;
        // private final View myView;
        // private DisplayNextView(int position,View myView) {
        // mPosition = position;
        // this.myView=myView;
        // }
        //
        // public void onAnimationStart(Animation animation) {
        //
        // }
        //
        // public void onAnimationEnd(Animation animation) {
        // myView.post(new SwapViews(mPosition,myView));
        // }
        //
        // public void onAnimationRepeat(Animation animation) {
        // }
        // }
        // private final class SwapViews implements Runnable {
        // private final int mPosition;
        // private final View myView;
        // public SwapViews(int position,View myView) {
        // mPosition = position;
        // this.myView=myView;
        // }
        // public void run() {
        // final float centerX = myView.getWidth() / 2.0f;
        // final float centerY = myView.getHeight() / 2.0f;
        // Lomowall_myphoto_Rotate3D rotation;
        //
        // if (mPosition > -1) {
        // myView.setVisibility(View.VISIBLE);
        // myView.requestFocus();
        //
        // rotation = new Lomowall_myphoto_Rotate3D(270, 360, centerX, centerY,
        // 310.0f, false);
        // } else {
        // myView.setVisibility(View.GONE);
        // rotation = new Lomowall_myphoto_Rotate3D(90, 0, centerX, centerY,
        // 310.0f, false);
        // }
        //
        // rotation.setDuration(500);
        // rotation.setFillAfter(true);
        // rotation.setInterpolator(new DecelerateInterpolator());
        //
        // myView.startAnimation(rotation);
        // }
        // }
    }
}
