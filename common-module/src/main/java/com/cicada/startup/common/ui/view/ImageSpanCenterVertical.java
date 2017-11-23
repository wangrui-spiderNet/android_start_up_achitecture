/*
 * Copyright (c) 2013-2015, thinkjoy Inc. All Rights Reserved.
 * 
 * Project Name: Zhiliao
 * $Id: ImageSpanCenterVertical.java 2015年7月13日 上午10:10:45 $ 
 */
package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * 垂直居中的imageSpan
 * <p/>
 * create time: 2015年7月13日 上午10:10:45 <br/>
 * 
 * @author liuyun
 * @version
 * @since v0.1
 */
public class ImageSpanCenterVertical extends ImageSpan {
	public ImageSpanCenterVertical(Context arg0, int arg1) {
		super(arg0, arg1);
	}

	public ImageSpanCenterVertical(Drawable drawable) {
		super(drawable);
	}

	@Override
	public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fm) {
		Drawable d = getDrawable();
		Rect rect = d.getBounds();
		if (fm != null) {
			FontMetricsInt fmPaint = paint.getFontMetricsInt();
			int fontHeight = fmPaint.bottom - fmPaint.top;
			int drHeight = rect.bottom - rect.top;

			int top = drHeight / 2 - fontHeight / 4;
			int bottom = drHeight / 2 + fontHeight / 4;

			fm.ascent = -bottom;
			fm.top = -bottom;
			fm.bottom = top;
			fm.descent = top;
		}
		return rect.right;
	}

	@Override
	public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
		Drawable b = getDrawable();
		canvas.save();
		int transY = 0;
		transY = ((bottom - top) - b.getBounds().bottom) / 2 + top;
		canvas.translate(x, transY);
		b.draw(canvas);
		canvas.restore();
	}
}
