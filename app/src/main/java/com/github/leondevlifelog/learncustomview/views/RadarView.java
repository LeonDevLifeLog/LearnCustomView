package com.github.leondevlifelog.learncustomview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.github.leondevlifelog.learncustomview.R;

import java.util.ArrayList;

/**
 * Created by leon on 17-8-29.
 */

public class RadarView extends View {
    private Paint wangPaint;
    /**
     * 中心x坐标
     */
    private int center_x;
    /**
     * 中心y坐标
     */
    private int center_y;

    /**
     * 射线个数
     */
    private int xCount;

    /**
     * 层级个数
     */
    private int yLevel;
    private double radius;


    public RadarView(Context context) {
        super(context);
        init(null, 0);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RadarView, defStyle, 0);
        xCount = a.getInteger(R.styleable.RadarView_x, 5);
        yLevel = a.getInteger(R.styleable.RadarView_y, 5);
        wangPaint = new Paint();
        wangPaint.setStyle(Paint.Style.STROKE);
        wangPaint.setColor(Color.GRAY);
        wangPaint.setAntiAlias(true);
        a.recycle();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        center_x = w / 2;
        center_y = h / 2;
        radius = w / 2 * 0.8;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        drawWang(canvas);

        drawyLine(canvas);
        drawText(canvas);
        drawData(canvas);
    }

    private void drawText(Canvas canvas) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < xCount; i++) {
            strings.add("属性" + i);
        }
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(22);
        for (int i = 0; i < xCount; i++) {
            float x = (float) (1.05 * radius * Math.cos(Math.PI * 2 / xCount * i) + center_x);
            float y = (float) (1.05 * radius * Math.sin(Math.PI * 2 / xCount * i) + center_y);
            float textW = textPaint.measureText(strings.get(i));
            canvas.drawText(strings.get(i), x, y, textPaint);
        }

    }

    /**
     * 绘制数据
     *
     * @param canvas
     */
    private void drawData(Canvas canvas) {
        ArrayList<Integer> datas = new ArrayList<>();
        for (int i = 0; i < xCount; i++) {
            datas.add(i * 10 + i);
        }
        Paint paint = new Paint();
        Paint dianPaint = new Paint();
        dianPaint.setAntiAlias(true);
        dianPaint.setColor(Color.RED);
        dianPaint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth((float) 20.0);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAlpha(126);
        Path path = new Path();
        for (int i = 0; i < xCount; i++) {
            float x = (float) ((radius * Math.cos(Math.PI * 2 / xCount * i)) * datas.get(i) / 100 + center_x);
            float y = (float) (radius * Math.sin(Math.PI * 2 / xCount * i) * datas.get(i) / 100 + center_y);
            if (i == 0)
                path.moveTo(x, y);
            else
                path.lineTo(x, y);
            canvas.drawCircle(x, y, 3, dianPaint);
        }
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制射线
     *
     * @param canvas
     */
    private void drawyLine(Canvas canvas) {
        Path line = new Path();
        for (int i = 0; i < xCount; i++) {
            line.reset();
            line.moveTo(center_x, center_y);
            line.lineTo((float) (radius * Math.cos(2 * Math.PI / xCount * i) + center_x),
                    (float) (radius * Math.sin(2 * Math.PI / xCount * i) + center_y));
            canvas.drawPath(line, wangPaint);
        }
    }

    /**
     * 绘制多边形
     *
     * @param canvas
     */
    private void drawWang(Canvas canvas) {
        Path wangXian = new Path();
        double oneLevel = radius / (xCount - 1);
        for (int i = 1; i < yLevel; i++) {
            wangXian.reset();
            wangXian.moveTo((float) (oneLevel * i + center_x), center_y);
            for (int i1 = 0; i1 < xCount; i1++) {
                wangXian.lineTo((float) (oneLevel * i * Math.cos(2 * Math.PI / xCount * i1) + center_x),
                        (float) (oneLevel * i * Math.sin(2 * Math.PI / xCount * i1) + center_y));
            }
            wangXian.close();
            canvas.drawPath(wangXian, wangPaint);
        }
    }
}
