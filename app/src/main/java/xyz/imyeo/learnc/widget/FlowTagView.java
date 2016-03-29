package xyz.imyeo.learnc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xyz.imyeo.learnc.R;

public class FlowTagView extends View {

    private static final String TAG = "Widget.FlowTagView";

    private List<CharSequence> mTags;

    private float mTagPadding;

    private float mTagGap;

    private float mTagTextSize;

    private float mTagTextBaseline;

    private int mTagHeight;

    private Paint mTextPaint;

    private Drawable mTagBackground;

    public FlowTagView(Context context) {
        this(context, null);
    }

    public FlowTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowTagView, defStyleAttr, 0);
        mTagBackground = a.getDrawable(R.styleable.FlowTagView_ftv_tagBackground);
        mTagPadding = a.getDimension(R.styleable.FlowTagView_ftv_tagPadding, 10);
        mTagGap = a.getDimension(R.styleable.FlowTagView_ftv_tagGap, 5);
        setTagTextSize(a.getDimensionPixelSize(R.styleable.FlowTagView_ftv_tagTextSize, 24));
        a.recycle();

        mTags = new ArrayList<>();
    }

    public void setTags(List<CharSequence> tags) {
        mTags = tags;
        invalidate();
    }

    private void setTagTextSize(float textSize) {
        mTextPaint.setTextSize(textSize);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTagHeight = (int) (fontMetrics.bottom - fontMetrics.top + mTagPadding);
        mTagTextBaseline = -fontMetrics.top + mTagPadding / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float left = getPaddingLeft();
        for (CharSequence tag : mTags) {
            float wid = mTextPaint.measureText(tag, 0, tag.length());
            float right = left + mTagPadding * 2 + wid;
            if (right > getWidth() - getPaddingRight()) {
                break;
            }
            mTagBackground.setBounds((int) left, 0, (int) right, mTagHeight);
            mTagBackground.draw(canvas);
            canvas.drawText(tag, 0, tag.length(), left + mTagPadding, mTagTextBaseline, mTextPaint);
            left = right + mTagGap;
        }
    }
}
