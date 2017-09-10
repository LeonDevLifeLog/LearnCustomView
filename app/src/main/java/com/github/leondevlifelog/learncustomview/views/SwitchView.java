package com.github.leondevlifelog.learncustomview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.leondevlifelog.learncustomview.R;

/**
 *
 */
public class SwitchView extends View {

    private int centerY;
    private int centerX;
    private int cx;
    private Paint roundRect;
    private Paint btnPaint;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    private int contentWidth;
    private int contentHeight;
    private int leftCenterX;
    private int rightCenterX;
    private int radiusX;

    public SwitchView(Context context) {
        super(context);
        init(null, 0);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        //禁用硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SwitchView, defStyle, 0);
        a.recycle();
        roundRect = new Paint();
        roundRect.setColor(Color.WHITE);
        roundRect.setAntiAlias(true);
        roundRect.setStyle(Paint.Style.FILL);
        roundRect.setShadowLayer(5, 0, 5, Color.GRAY);
        btnPaint = new Paint();
        btnPaint.setColor(Color.BLUE);
        btnPaint.setStyle(Paint.Style.FILL);
        btnPaint.setAntiAlias(true);
        //<editor-fold desc="设置阴影">
        btnPaint.setShadowLayer(5, 0, 5, Color.GRAY);
        //</editor-fold>
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // allocations per draw cycle.
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        contentWidth = getWidth() - paddingLeft - paddingRight;
        contentHeight = getHeight() - paddingTop - paddingBottom;
        centerY = (h / 2);
        centerX = w / 2;
        cx = paddingLeft + (contentHeight / 2);
        radiusX = contentHeight / 2;
        leftCenterX = paddingLeft + (contentHeight / 2);
        rightCenterX = w - paddingRight - (contentHeight / 2);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(paddingLeft, paddingTop, contentWidth + paddingLeft, contentHeight + paddingTop, radiusX, radiusX, roundRect);
        canvas.drawCircle(cx, centerY, (contentHeight / 2) * 0.9f, btnPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        cx = Math.round(event.getX());
        if (cx < leftCenterX)
            cx = leftCenterX;
        if (cx > rightCenterX)
            cx = rightCenterX;
        postInvalidate();
        return true;
    }

}
