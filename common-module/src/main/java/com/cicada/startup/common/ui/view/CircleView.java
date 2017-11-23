package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.cicada.startup.common.R;

import java.text.DecimalFormat;

/**
 * Created by zyh on 2017/4/19.
 */
public class CircleView extends View implements View.OnClickListener{
    //m  member，类的成员属性
    private DecimalFormat df = new DecimalFormat("00.00");
    //绘制文本的专用笔
    private Paint mTextPaint;
    //文本边界
    private Rect mBound;
    //普通绘图的笔(图形和文本需要同时绘制，没办法公共一支笔)
    private Paint mPaint;
    //屏幕宽度
    private int mScreenWidth;
    //扇形百分比
    private float precent = 0;
    //是否被点击
    private boolean isClick;
    //默认大小
    private int defaultRadius = 60;
    //外环的半径
    private int mOutCircleRadius;
    //外环的粗细
    private int mOutCircleWidth;
    //外环的颜色
    private int mOutCircleColor;
    //内圆的半径
    private int mInnerCircleRadius;
    //内圆的背景
    private int mInnerCircleColor;
    //扇形的颜色
    private int mSectorColor;
    //默认图片资源
    private int defaultRes;
    //默认资源bitmap
    private Bitmap defaultBitmap;
    //是否显示数字文本
    private boolean isTextShow;
    //文本大小
    private int textSize = 16;
    //文本颜色
    private int textColor;

    private OnProgressCompleteListener onProgressCompleteListener;

    private OnCircleClickListener onCircleClickListener;

    public void setOnProgressListener(OnProgressCompleteListener l) {
        this.onProgressCompleteListener = l;
    }

    public void setOnCircleClickListener(OnCircleClickListener l) {
        this.onCircleClickListener = l;
    }

    /**
     * 是否正在绘制更新
     *
     * @return
     */
    public boolean isStartIng() {
        return precent > 0 && precent < 100;
    }

    /**
     * 更新view
     *
     * @param precent
     */
    public void setPrecent(float precent) {
        this.precent = Math.min(precent, 100);
        this.precent = Math.max(precent, 0);
        if (this.precent >= 100 && this.onProgressCompleteListener != null)
            onProgressCompleteListener.onComplete(this.precent);
        postInvalidate();
    }

    /**
     * 重置
     */
    public void reset() {
        if (this.precent >= 100) {
            //显示默认图标
            this.isClick = false;
            //不绘制扇形
            this.precent = 0;
            postInvalidate();
        }
    }

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        int size = typedArray.getIndexCount();
        for (int i = 0; i < size; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.CircleView_outCircleColor) {//外环的颜色
                mOutCircleColor = typedArray.getColor(attr, Color.parseColor("#80FFFFFF"));
            } else if (attr == R.styleable.CircleView_outCircleWidth) {//外环的粗细
                mOutCircleWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.CircleView_innerCircleColor) {//内圆的背景 #80808080
                mInnerCircleColor = typedArray.getColor(attr, Color.TRANSPARENT);
            } else if (attr == R.styleable.CircleView_sectorColor) {//扇形的颜色
                mSectorColor = typedArray.getColor(attr, Color.parseColor("#80FFFFFF"));
            } else if (attr == R.styleable.CircleView_defaultRes) {//默认图片资源
                defaultRes = typedArray.getResourceId(attr, -1);
                if (defaultRes > 0)
                    defaultBitmap = BitmapFactory.decodeResource(context.getResources(), defaultRes);
            } else if (attr == R.styleable.CircleView_isTextShow) {//是否显示数字文本
                isTextShow = typedArray.getBoolean(attr, true);
            } /*else if (attr == R.styleable.CircleView_textSize) {//文本大小
                textSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.CircleView_textColor) {//文本颜色
                textColor = typedArray.getColor(attr, Color.BLUE);
            }*/
        }
        typedArray.recycle();
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(textSize);
        //mTextPaint.setColor(textColor);
        mBound = new Rect();
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //用户把宽高都设置成包裹内容
            widthSize = heightSize = defaultRadius;
        } else {
            widthSize = heightSize = Math.min(widthSize, heightSize);
            if (widthSize <= 0)
                widthSize = heightSize = defaultRadius;
        }
        //保存宽高
        setMeasuredDimension(widthSize, heightSize);
        //外圆半径
        mOutCircleRadius = (widthSize >> 1) - (mOutCircleWidth >> 1);
        //内圆半径
        mInnerCircleRadius = mOutCircleRadius - (mOutCircleWidth >> 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 画外圆
         */
        mPaint.reset();
        mPaint.setColor(mOutCircleColor);
        //非填充
        mPaint.setStyle(Paint.Style.STROKE);
        //外圆环的大小
        mPaint.setStrokeWidth(mOutCircleWidth);
        canvas.drawCircle(getMeasuredWidth() >> 1, getMeasuredHeight() >> 1, mOutCircleRadius, mPaint);

        /**
         * 画内部圆
         */
        mPaint.reset();
        //填充
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mInnerCircleColor);
        canvas.drawCircle(getMeasuredWidth() >> 1, getMeasuredHeight() >> 1, mInnerCircleRadius+5, mPaint);

        /**
         * 判断是否需要绘制默认图片
         */
        if (!isClick && defaultBitmap != null && (defaultBitmap = reSize(defaultBitmap)) != null) {
            canvas.drawBitmap(defaultBitmap, (getMeasuredWidth() >> 1) - (defaultBitmap.getWidth() >> 1),
                    (getMeasuredHeight() >> 1) - (defaultBitmap.getHeight() >> 1), null);
        }

        /**
         * 画扇形了
         */
        mPaint.reset();
        mPaint.setColor(mSectorColor);
        int gap = mOutCircleWidth << 1;
        RectF rectF = new RectF(gap, gap, getMeasuredWidth() - gap, getMeasuredHeight() - gap);
        canvas.drawArc(rectF, -90, 360 * precent / 100, true, mPaint);

        /**
         * 判断是否需要绘制文本进度数字
         */
        if (isTextShow && precent > 0) {
            String text = df.format(precent) + "%";
            //测量文本边界
            mTextPaint.getTextBounds(text, 0, text.length(), mBound);
            canvas.drawText(text, 0, text.length(), (getWidth() >> 1) - (mBound.width() >> 1),
                    (getHeight() >> 1) + (mBound.height()), mTextPaint);
        }
    }

    /**
     * 调整bitmap大小
     *
     * @param bitmap
     * @return
     */
    private Bitmap reSize(Bitmap bitmap) {
        //扇形可得的内接正方形边长
        double tempWidth = Math.sqrt(2 * Math.pow((getMeasuredWidth() - mOutCircleWidth * 4) >> 1, 2));
        double tempHeight = tempWidth;
        float widthScale = (float) tempWidth * 1.0f / bitmap.getWidth();
        float heightScale = (float) tempHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    public void onClick(View v) {
        if (CircleView.this.precent <= 0 && !isClick)
            isClick = true;
        if (CircleView.this.onCircleClickListener != null) {
            onCircleClickListener.onClick(v);
        }
    }

    public interface OnProgressCompleteListener {
        void onComplete(float progress);
    }

    public interface OnCircleClickListener {
        void onClick(View v);
    }
}
