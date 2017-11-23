package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.cicada.startup.common.utils.DeviceUtils;

public class CircleProgressView extends View {

    private Paint mOutSidePaint;
    private Paint mInsidePaint;
    private int mMax = 1;
    private int mProgress;
    private float mCenterX;
    private float mCenterY;
    private RectF mOutSideCircleRectF = new RectF();
    private RectF mInSideCircleRectF = new RectF();
    private int mOutSideRadius = DeviceUtils.dip2px(getContext(), 30);
    private int mInSideRadius = DeviceUtils.dip2px(getContext(), 28);

    public CircleProgressView(Context context){
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);  
        initPaint();
    }

    private void initPaint() {
        // TODO Auto-generated method stub
        mOutSidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutSidePaint.setColor(Color.LTGRAY);
        mOutSidePaint.setStrokeWidth(2.0f);
        mOutSidePaint.setStyle(Paint.Style.STROKE);

        mInsidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInsidePaint.setColor(Color.LTGRAY);
        mInsidePaint.setStyle(Paint.Style.FILL);
    }

    /* (non-Javadoc)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//      super.measure(widthMeasureSpec, heightMeasureSpec);
//      setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
        calculateCircleCenter();
        calculateDrawRectF();

    }
    //
/*  private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight()
                : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }*/

    private void calculateCircleCenter() {
        mCenterX = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2.0f
                + getPaddingLeft();
        mCenterY = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2.0f
                + getPaddingTop();
    }


    private void calculateDrawRectF() {
        mOutSideCircleRectF.left = mCenterX - mOutSideRadius;
        mOutSideCircleRectF.top = mCenterY - mOutSideRadius;
        mOutSideCircleRectF.right = mCenterX + mOutSideRadius;
        mOutSideCircleRectF.bottom = mCenterY + mOutSideRadius;

        mInSideCircleRectF.left = mCenterX - mInSideRadius;
        mInSideCircleRectF.top = mCenterY - mInSideRadius;
        mInSideCircleRectF.right = mCenterX + mInSideRadius;
        mInSideCircleRectF.bottom = mCenterY + mInSideRadius;
    }
    /* 
     * 
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //画外圈
        canvas.drawCircle(mCenterX, mCenterY, mOutSideRadius, mOutSidePaint);
//      //画内圈
        canvas.drawArc(mInSideCircleRectF, -90,
                mProgress * 360 / mMax, true, mInsidePaint);
        canvas.restore();
    }

    public void setMax(int max){
        this.mMax = max;
    }

    public void setProgress(int progress){
        this.mProgress = progress;
        invalidate();
    }
}