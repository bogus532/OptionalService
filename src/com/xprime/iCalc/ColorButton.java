package com.xprime.iCalc;

import com.xprime.OptionalService.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by IntelliJ IDEA.
 * User: izahid
 * Date: May 28, 2010
 * Time: 1:18:02 PM
 * To change this template use File | Settings | File Templates.
 */
class ColorButton extends Button implements View.OnClickListener {
    int CLICK_FEEDBACK_COLOR;
    static final int CLICK_FEEDBACK_INTERVAL = 10;
    static final int CLICK_FEEDBACK_DURATION = 350;

    float mTextX;
    float mTextY;
    long mAnimStart;
    OnClickListener mListener;
    Paint mFeedbackPaint;
    Color color;

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        iCalc calc = (iCalc) context;
        init(calc);
        mListener = calc.mListener;
        setOnClickListener(this);
    }

    private void init(iCalc calc) {
        Resources res = getResources();

        CLICK_FEEDBACK_COLOR = res.getColor(R.color.magic_flame);
        mFeedbackPaint = new Paint();
        mFeedbackPaint.setStyle(Paint.Style.STROKE);
        mFeedbackPaint.setStrokeWidth(2);
        //getPaint().setColor(res.getColor(R.color.button_text));
        getPaint().setColor(color.argb(255, 255, 0, 0));

        mAnimStart = -1;

        calc.adjustFontSize(this);
    }

    public void onClick(View view) {
        mListener.onClick(this);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        measureText();
    }
    private void measureText() {
        Paint paint = getPaint();
        mTextX = (getWidth() - paint.measureText(getText().toString())) / 2;
        mTextY = (getHeight() - paint.ascent() - paint.descent()) / 2;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        measureText();
    }

    private void drawMagicFlame(int duration, Canvas canvas) {
        int alpha = 255 - 255 * duration / CLICK_FEEDBACK_DURATION;
        int color = CLICK_FEEDBACK_COLOR | (alpha << 24);

        mFeedbackPaint.setColor(color);
        canvas.drawRect(1, 1, getWidth() - 1, getHeight() - 1, mFeedbackPaint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mAnimStart != -1) {
            int animDuration = (int) (System.currentTimeMillis() - mAnimStart);

            if (animDuration >= CLICK_FEEDBACK_DURATION) {
                mAnimStart = -1;
            } else {
                drawMagicFlame(animDuration, canvas);
                postInvalidateDelayed(CLICK_FEEDBACK_INTERVAL);
            }
        } else if (isPressed()) {
            drawMagicFlame(0, canvas);
        }

        CharSequence text = getText();
        canvas.drawText(text, 0, text.length(), mTextX, mTextY, getPaint());
    }

    public void animateClickFeedback() {
        mAnimStart = System.currentTimeMillis();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                animateClickFeedback();
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_CANCEL:
                invalidate();
                break;
        }

        return result;
    }

}
