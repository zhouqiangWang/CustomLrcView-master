package com.zhouq.widget.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/3/1.
 */
public class GradientTextView extends TextView{
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mTranslate = 0;
    final private int UNITIME = 100;

    public void setmTime(long mTime) {
        this.mDuration = mTime;
    }

    private long mDuration = 3000;

    private boolean mAnimating = true;

    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private final String TAG = GradientTextView.class.getSimpleName()+"-wang";
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG,"onSizeChanged  :: w = "+w+", h = "+h+", oldw = "+oldw+", oldh = "+oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
                        new int[] {Color.YELLOW, Color.YELLOW, Color.WHITE },
                        new float[] { 0, 0.9f, 1 }, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int times = (int) (mDuration / UNITIME);
        Log.d(TAG+"-wang","onDraw : n = "+times+", mTranslate = "+mTranslate+", width = "+mViewWidth);
        if (mAnimating && mGradientMatrix != null) {
            mTranslate += mViewWidth / times;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(UNITIME);
        }
    }
}
