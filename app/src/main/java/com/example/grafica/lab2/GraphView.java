package com.example.grafica.lab2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Sex_predator on 01.03.2016.
 */
public class GraphView extends View {

    private int mPointCount;
    private int mWidth, mHeight;
    private Paint mPaint;

    private Graph                mInterpolator;
    private ScaleGestureDetector mScaleDetector;
    private RotateListener       mRotateListener;

    private double[] mX, mY;
    private double mMaxX, mMaxY, mMinX, mMinY;
    private double mScale, mMaxScale, mMinScale;
    private float mTranslateX, mTranslateY, mRotateDegree;

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setStrokeWidth(1f);
        mPaint.setAntiAlias(true);

        setOnTouchListener(new MoveListener());
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mRotateListener = new RotateListener();
    }

    public void setGraph(Graph interpolator) {
        mInterpolator = interpolator;
        mPointCount = interpolator.getPointCount();

        double minT = interpolator.getStartInterval();
        double maxT = interpolator.getEndInterval();
        mX = new double[mPointCount];
        mY = new double[mPointCount];

        mMaxX = mMaxY = Long.MIN_VALUE;
        mMinX = mMinY = Long.MAX_VALUE;

        for (int i = 0; i < mPointCount; i++) {
            double t = minT + maxT * i / (mPointCount - 1);
            mX[i] = interpolator.interpolateX(t);
            mY[i] = interpolator.interpolateY(t);

            mMaxX = Math.max(mMaxX, mX[i]);
            mMaxY = Math.max(mMaxY, mY[i]);
            mMinX = Math.min(mMinX, mX[i]);
            mMinY = Math.min(mMinY, mY[i]);
        }

        recalculateValues();
        invalidate();
    }

    public void resetGraph() {
        recalculateValues();
        invalidate();
    }

    private void recalculateValues() {
        double graphWidth = mMaxX - mMinX;
        double graphHeight = mMaxY - mMinY;

        double scaleWidth = mWidth / graphWidth;
        double scaleHeight = mHeight / graphHeight;

        mScale = Math.min(scaleWidth, scaleHeight) * 0.95;
        mMaxScale = mScale * 5;
        mMinScale = mScale / 5;

        mTranslateX = (float) ((mWidth - graphWidth * mScale) / 2 - mMinX * mScale);
        mTranslateY = (float) ((mHeight - graphHeight * mScale) / 2 - mMinY * mScale);

        mRotateDegree = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;

        recalculateValues();

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mInterpolator == null)
            return;

        canvas.translate(mTranslateX, mTranslateY);
        canvas.rotate(mRotateDegree);

        float lastX = -1, lastY = -1;

        for (int i = 0; i < mPointCount; i++) {
            double x = mX[i] * mScale;
            double y = mY[i] * mScale;

            if (i != 0)
                canvas.drawLine(lastX, lastY, (float) x, (float) y, mPaint);

            lastX = (float) x;
            lastY = (float) y;
        }

    }

    public interface Graph {

        int getPointCount();

        double getStartInterval();

        double getEndInterval();

        double interpolateX(double t);

        double interpolateY(double t);

    }

    private class MoveListener implements OnTouchListener {

        private float mTouchX;
        private float mTouchY;
        private int   mTouchId;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mScaleDetector.onTouchEvent(event) | mRotateListener.onTouch(v, event))
                invalidate();

            if (event.getPointerCount() > 1) {
                mTouchId = -1;
                return true;
            }

            float x = event.getX(0);
            float y = event.getY(0);

            int id = event.getPointerId(0);
            if (id != mTouchId) {
                mTouchX = x;
                mTouchY = y;
                mTouchId = id;
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTouchX = x;
                    mTouchY = y;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    mTranslateX += x - mTouchX;
                    mTranslateY += y - mTouchY;
                    mTouchX = x;
                    mTouchY = y;
                    invalidate();
                    return true;
            }

            return false;
        }

    }

    private class RotateListener implements OnTouchListener {

        private int mFirstTouchId, mSecondTouchId;
        private double mLastRadians;

        public RotateListener() {
            mFirstTouchId = mSecondTouchId = -1;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getPointerCount() == 1) {
                mFirstTouchId = -1;
                mSecondTouchId = -1;
                return false;
            }

            int firstIndex = event.findPointerIndex(mFirstTouchId);
            int secondIndex = event.findPointerIndex(mSecondTouchId);

            boolean firstTouch = false;

            if (firstIndex == -1 || secondIndex == -1) {
                mFirstTouchId = event.getPointerId(0);
                mSecondTouchId = event.getPointerId(1);
                firstIndex = 0;
                secondIndex = 1;
                firstTouch = true;
            }

            double deltaX = (event.getX(firstIndex) - event.getX(secondIndex));
            double deltaY = (event.getY(firstIndex) - event.getY(secondIndex));
            double radians = Math.atan2(deltaY, deltaX);

            if (firstTouch) {
                mLastRadians = radians;
                return false;
            }
            //else
            mRotateDegree += (float) Math.toDegrees(radians - mLastRadians);
            mLastRadians = radians;
            return true;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            if (scaleFactor == 1)
                return false;

            mScale *= scaleFactor;
            mScale = Math.max(mMinScale, Math.min(mScale, mMaxScale));

            return true;
        }
    }

}
