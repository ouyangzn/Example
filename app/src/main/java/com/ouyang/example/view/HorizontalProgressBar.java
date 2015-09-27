package com.ouyang.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ouyang.example.R;
import com.ouyang.example.utils.Log;

/**
 * @author ouyangzn
 * 水平进度条
 */
public class HorizontalProgressBar extends View {

    private final String TAG = "HorizontalProgressBar";

    private static final int DEFAULT_PROGRESS_COLOR = 0xff3F51B5;
    private static final int DEFAULT_PRIMARY_COLOR = 0x00000000;
    private static final int DEFAULT_MAX_PROGRESS = 100;
    private static final int DEFAULT_CURR_PROGRESS = 0;

    private int mProgressColor = DEFAULT_PROGRESS_COLOR;
    private int mPrimaryColor = DEFAULT_PRIMARY_COLOR;
    private int mMaxProgress = DEFAULT_MAX_PROGRESS;
    private int mCurrProgress = DEFAULT_CURR_PROGRESS;

    public HorizontalProgressBar(Context context) {
        super(context);
        init(null, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HorizontalProgressBar, defStyle, 0);
        mProgressColor = a.getColor(R.styleable.HorizontalProgressBar_progressColor, DEFAULT_PROGRESS_COLOR);
        mPrimaryColor = a.getColor(R.styleable.HorizontalProgressBar_primaryColor, DEFAULT_PRIMARY_COLOR);
        mMaxProgress = a.getInt(R.styleable.HorizontalProgressBar_maxProgress, 100);
        mCurrProgress = a.getInt(R.styleable.HorizontalProgressBar_currProgress, 0);
        a.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "mProgressColor:" + mProgressColor + "  mPrimaryColor:" + mPrimaryColor
            + "  mMaxProgress:" + mMaxProgress + "  mCurrProgress:" + mCurrProgress);
        // 最大进度值必须大于0
        if (mMaxProgress <= 0) return;

        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        Log.d(TAG, "width="+getWidth() +"  height=" + getHeight()
                + "  paddingLeft="+paddingLeft + "  paddingTop="+paddingTop
                + "  paddingRight="+paddingRight + "  paddingBottom="+paddingBottom
                + "  contentWidth="+contentWidth +"  contentHeight="+contentHeight);
        Paint paint = new Paint();
        paint.setStrokeWidth(contentHeight);
        // 如果有设置底色，画出底色
        if (mPrimaryColor != DEFAULT_PRIMARY_COLOR) {
            paint.setColor(mPrimaryColor);
            canvas.drawLine(paddingLeft, 0, contentWidth+paddingLeft, 0, paint);
        }
        // 计算进度条的长度
        int progressWidth = (int)((float)mCurrProgress / (float)mMaxProgress * contentWidth);
        // 画进度条
        Log.d(TAG, "线条长:"+progressWidth);
        paint.setColor(mProgressColor);
        canvas.drawLine(paddingLeft, 0, progressWidth+paddingLeft, 0, paint);


    }

}
