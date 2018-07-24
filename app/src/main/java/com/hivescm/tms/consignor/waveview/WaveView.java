package com.hivescm.tms.consignor.waveview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by mayong on 2018/7/24.
 * 浪花控件
 */

public class WaveView extends View {
    private int waveWidth;
    private Paint topPaint;
    private double degree = Math.PI * 2.5;
    private Path topPath, bottomPath;
    private Paint bottomPaint;
    private double peerDegree;//每次路径增加变化的角度值
    private int peerX;//每刷新一次x轴的变化值
    private int waveHeight = 30;//浪花的高度
    private float currentY = 200;//当前的浪花y高度
    private double degreeStep = Math.PI / 30;//新的浪结束后，下一次浪角度的变化
    private double yDegree;
    private int wLeft, wRight, wBottom;

    public WaveView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        topPaint = new Paint(Color.BLUE);
        bottomPaint = new Paint(Color.parseColor("#00ffff"));
        topPath = new Path();
        bottomPath = new Path();
        peerX = 20;
        postDelayed(new WaveRunable(), 30);
        setPaintStyle(topPaint);
        setPaintStyle(bottomPaint);
        topPaint.setColor(Color.parseColor("#aa303F9F"));
        bottomPaint.setColor(Color.parseColor("#6600ffff"));
        Random random = new Random();
    }

    private void calculateOffset() {
        topPath.reset();
        bottomPath.reset();
        float topY = 0;
        yDegree += degreeStep;
        for (int x = 0; x < waveWidth; x += peerX) {
            topY = (float) (Math.sin(x * peerDegree + yDegree) * waveHeight) + currentY;
            if (x == 0) {
                topPath.moveTo(wLeft, wBottom);
            }
            topPath.lineTo(x, topY + currentY);
        }
        topPath.lineTo(wRight, wBottom);


        float bottomY = 0;
        bottomPath.moveTo(wLeft, wBottom);
        for (int x = 0; x < waveWidth; x += peerX) {
            bottomY = (float) (Math.sin(x * peerDegree + yDegree+Math.PI/2) * waveHeight) + currentY;
            bottomPath.lineTo(x,bottomY+currentY);
        }
        bottomPath.lineTo(wRight, wBottom);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        waveWidth = MeasureSpec.getSize(heightMeasureSpec);
        peerDegree = degree / waveWidth;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        wLeft = left;
        wRight = right;
        wBottom = bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(topPath, topPaint);
        canvas.drawPath(bottomPath, bottomPaint);
    }

    public void setPaintStyle(Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    public void setWaveHeight(int height){
        this.waveHeight=height;
    }

    public int getWaveHeight() {
        return waveHeight;
    }

    class WaveRunable implements Runnable {

        @Override
        public void run() {
            calculateOffset();
            invalidate();
            postDelayed(this, 30);
        }
    }
}
