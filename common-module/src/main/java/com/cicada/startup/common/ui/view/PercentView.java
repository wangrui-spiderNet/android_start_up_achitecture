package com.cicada.startup.common.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.cicada.startup.common.R;
import com.cicada.startup.common.utils.DeviceUtils;
import com.cicada.startup.common.utils.ScreenUtils;

/**
 * Created by zyh on 2016/12/19 0019.
 */

public class PercentView extends View{
    private Context context;
    /**
     * 圆弧的中心点x坐标
     */
    private float centerX;
    /**
     * 圆弧的宽度
     */
    private float borderWidth = 38f;
    /**
     * 开始绘制圆弧的角度
     */
    private float startAngle = 0;
    /**
     * 所要绘制的当前百分比的红色圆弧终点到起点的夹角
     */
    private float currentAngleLength = 0;
    /**
     * 终点对应的角度和起始点对应的角度的夹角
     */
    private float angleLength = 360;
    /**
     * 画百分比的数值的字体大小
     */
    private float numberTextSize = 90;
    /**
     * 百分比
     */
    private String percentNumber = "0";
    /**
     * 动画时长
     */
    private int animationLength = 1000;
    private PathMeasure mPathMeasure;
    private float fraction = 0;
    private Paint circlePaint;
    private Path path, mDst;
    private int topMagin = 25, bottoMagain = 35;
    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public PercentView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PercentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    
    private void init(){
        setBorderWidth(ScreenUtils.dip2px(context, 20));
        initCirclePaint();
    }
    
    private void initCirclePaint(){
        circlePaint = new Paint();
        /** 默认画笔颜色*/
        circlePaint.setColor(getResources().getColor(R.color.percentview_default));
        /** 结合处为圆弧*/
        circlePaint.setStrokeJoin(Paint.Join.ROUND);
        /** 设置画笔的样式 Paint.Cap.Round ,Cap.SQUARE等分别为圆形、方形*/
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        /** 设置画笔的填充样式 Paint.Style.FILL  :填充内部;Paint.Style.FILL_AND_STROKE  ：填充内部和描边;  Paint.Style.STROKE  ：仅描边*/
        circlePaint.setStyle(Paint.Style.STROKE);
        /**抗锯齿功能*/
        circlePaint.setAntiAlias(true);
        /**设置画笔宽度*/
        circlePaint.setStrokeWidth(borderWidth);
        //path = new Path();
        //mPathMeasure = new PathMeasure();
        //mDst = new Path();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**中心点的x坐标*/
        centerX = (getWidth()) / 2;
        /**【第一步】绘制整体的灰色圆*/
        drawCircle(canvas);
        /**【第二步】绘制当前进度的渐变色圆弧*/
        RectF rectF = new RectF(borderWidth, borderWidth, 2 * centerX - borderWidth, 2 * centerX - borderWidth);
        drawArcRed(canvas, rectF);
        /**【第三步】绘制当前进度的蓝色数字*/
        drawTextNumber(canvas, centerX);
    }
    
    private void drawCircle(Canvas canvas){
        //path.addCircle(centerX, centerX, centerX - borderWidth, Path.Direction.CCW);
        //mPathMeasure.setPath(path, true);
        //mDst.reset();
        //mPathMeasure.getSegment(0, mPathMeasure.getLength() * fraction, mDst, true);
        canvas.drawCircle(centerX, centerX, centerX - borderWidth, circlePaint);
        //canvas.drawPath(mDst, circlePaint);
    }
    
    private void drawArcRed(Canvas canvas, RectF rectF){
        Paint paintCurrent = new Paint();
        paintCurrent.setStrokeJoin(Paint.Join.ROUND);
//        paintCurrent.setStrokeCap(Paint.Cap.SQUARE);//圆角弧度
        paintCurrent.setStyle(Paint.Style.STROKE);//设置填充样式
        paintCurrent.setAntiAlias(true);//抗锯齿功能
        paintCurrent.setStrokeWidth(borderWidth);//设置画笔宽度
        paintCurrent.setColor(getResources().getColor(R.color.percentview_arc));
        /**
         * 设置圆形渐变
         * 【第一个参数】：中心点x坐标
         * 【第二个参数】：中心点y坐标
         * 【第三个参数】：渐变的颜色数组
         * 【第四个参数】：渐变的颜色数组对应的相对位置
         */
//        paintCurrent.setShader(new SweepGradient(centerX, centerX,
//                new int[]{getResources().getColor(R.color.percentview_arc),
//                        getResources().getColor(R.color.percentview_arc),
//                        getResources().getColor(R.color.percentview_arc),
//                        getResources().getColor(R.color.percentview_arc)},
//                null));

        canvas.drawArc(rectF, startAngle, currentAngleLength, false, paintCurrent);
    }

    /**
     * 3.圆环中心的字
     */
    private void drawTextNumber(Canvas canvas, float centerX) {
        //百分比
        Paint vTextPaint = new Paint();
        vTextPaint.setTextAlign(Paint.Align.CENTER);
        vTextPaint.setAntiAlias(true);//抗锯齿功能
        vTextPaint.setTextSize(numberTextSize);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        vTextPaint.setTypeface(font);//字体风格
        vTextPaint.setColor(getResources().getColor(R.color.percentview_arc));
        Rect bounds_Number = new Rect();
        vTextPaint.getTextBounds(percentNumber, 0, percentNumber.length(), bounds_Number);
        canvas.drawText(percentNumber, centerX-13, (getHeight() / 2 + bounds_Number.height() / 2) - topMagin, vTextPaint);
        //百分号
        Paint vPaint = new Paint();
        vPaint.setTextAlign(Paint.Align.CENTER);
        vPaint.setAntiAlias(true);//抗锯齿功能
        vPaint.setTextSize(30);
        Typeface vfont = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        vPaint.setTypeface(vfont);//字体风格
        vPaint.setColor(getResources().getColor(R.color.percentview_arc));
        Rect bounds_per = new Rect();
        String percent = "%";
        vPaint.getTextBounds(percent, 0, percent.length(), bounds_per);
        canvas.drawText(percent, centerX+bounds_Number.width()/2, (getHeight() / 2 + bounds_per.height() / 2) - 5, vPaint);
        //百分比下面的字
        Paint blackTxtPaint = new Paint();
        blackTxtPaint.setTextAlign(Paint.Align.CENTER);
        blackTxtPaint.setAntiAlias(true);//抗锯齿功能
        blackTxtPaint.setTextSize(30);
        Typeface blackTxtFont = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        blackTxtPaint.setTypeface(blackTxtFont);//字体风格
        blackTxtPaint.setColor(Color.parseColor("#666666"));
        Rect bounds_txt = new Rect();
        String txt = "全勤宝宝占比";
        blackTxtPaint.getTextBounds(txt, 0, txt.length(), bounds_txt);
        canvas.drawText(txt, centerX, (getHeight() / 2 + bounds_txt.height() / 2) + bottoMagain, blackTxtPaint);
    }
    
    /**
     * 为进度设置动画
     * ValueAnimator是整个属性动画机制当中最核心的一个类，属性动画的运行机制是通过不断地对值进行操作来实现的，
     * 而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。
     * 它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，
     * 我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，
     * 那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。
     *
     * @param last
     * @param current
     */
    private void setAnimation(float last, float current, int length) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngleLength);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**每次要绘制的圆弧角度**/
                currentAngleLength = (float) animation.getAnimatedValue();
                //Log.d(TAG, "currentAngleLength=" + currentAngleLength);
                invalidate();
            }
        });
        progressAnimator.start();
    }
    
    private void setCircleAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(animationLength);
        animator.setTarget(currentAngleLength);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    /**
     * 设置文本大小,防止步数特别大之后放不下，将字体大小动态设置
     *
     * @param num
     */
    public void setTextSize(String num) {
        int length = num.length();
        if (length <= 2) {
            numberTextSize = 90;
            topMagin = 25;
            bottoMagain = 35;
        } else if (length > 2 && length <= 4){
            numberTextSize = 80;
            topMagin = 20;
            bottoMagain = 35;
        } else if (length > 4 && length <= 6){
            numberTextSize = 70;
            topMagin = 15;
            bottoMagain = 30;
        } else {
            numberTextSize = 50;
            topMagin = 10;
            bottoMagain = 20;
        }
    }

    /**
     * 百分比进度
     * @param currentPercent 当前的百分比
     */
    public void setCurrentPercent(String currentPercent) {
        if(TextUtils.equals("100.0", currentPercent) || TextUtils.equals("100.00", currentPercent)) {
            currentPercent = "100";
        }
        percentNumber = currentPercent;
        setTextSize(currentPercent);
        /**当前的百分比*/
        float scale = Float.valueOf(currentPercent) / 100;
        /**换算成弧度最后要到达的角度的长度-->弧长*/
        currentAngleLength = scale * angleLength;
        /**开始执行最外层圆绘制动画*/
        //setCircleAnimation();
        /**开始执行彩色圆弧绘制动画*/
        setAnimation(0, currentAngleLength, animationLength);
    }

}
