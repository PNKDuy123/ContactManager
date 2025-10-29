package com.example.contactmanager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class AlphabetIndexer extends View {

    private static final String[] ALPHABET = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
    };

    private Paint paint;
    private Paint selectedPaint;
    private OnLetterSelectedListener listener;
    private int selectedIndex = -1;

    public interface OnLetterSelectedListener {
        void onLetterSelected(String letter);
    }

    public AlphabetIndexer(Context context) {
        super(context);
        init();
    }

    public AlphabetIndexer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#999999"));
        paint.setTextSize(32);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        selectedPaint = new Paint(paint);
        selectedPaint.setColor(Color.parseColor("#2196F3"));
        selectedPaint.setTextSize(40);
    }

    public void setOnLetterSelectedListener(OnLetterSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();
        int letterHeight = height / ALPHABET.length;

        for (int i = 0; i < ALPHABET.length; i++) {
            float x = width / 2f;
            float y = letterHeight * i + letterHeight / 2f + 10;

            Paint currentPaint = (i == selectedIndex) ? selectedPaint : paint;
            canvas.drawText(ALPHABET[i], x, y, currentPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            int index = (int) (y / getHeight() * ALPHABET.length);

            if (index >= 0 && index < ALPHABET.length) {
                selectedIndex = index;
                if (listener != null) {
                    listener.onLetterSelected(ALPHABET[index]);
                }
                invalidate();
            }
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            selectedIndex = -1;
            invalidate();
        }

        return super.onTouchEvent(event);
    }
}