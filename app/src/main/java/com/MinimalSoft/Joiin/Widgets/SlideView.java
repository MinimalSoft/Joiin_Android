package com.MinimalSoft.Joiin.Widgets;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MinimalSoft.Joiin.R;

public class SlideView extends RelativeLayout implements View.OnTouchListener {
    OnFinishListener onFinishListener;
    private Drawable mSlideBackground;
    private Drawable mSlideImage;
    private ImageView mSlideIcon;
    private TextView mSlideTextView;
    private int slideSrcMarginBottom;
    private int slideSrcMarginRight;
    private int slideSuccessPercent;
    private int slideSrcMarginLeft;
    private int slideSrcMarginTop;
    private int slideSrcMargin;
    private int slideTextSize;
    private int getPercent;
    private Integer slideTextColor;
    private Integer duration;
    private String slideText;
    private float storeX;
    private boolean isCanTouch = true;

    public SlideView(Context context) {
        super(context);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlideView, 0, 0);

        try {
            mSlideImage = array.getDrawable(R.styleable.SlideView_slideSrc);
            slideSrcMargin = array.getDimensionPixelSize(R.styleable.SlideView_slideSrcMargin, 0);
            slideSrcMarginLeft = array.getDimensionPixelSize(R.styleable.SlideView_slideSrcMarginLeft, 0);
            slideSrcMarginTop = array.getDimensionPixelSize(R.styleable.SlideView_slideSrcMarginTop, 0);
            slideSrcMarginBottom = array.getDimensionPixelSize(R.styleable.SlideView_slideSrcMarginBottom, 0);
            slideSrcMarginRight = array.getDimensionPixelSize(R.styleable.SlideView_slideSrcMarginRight, 0);
            slideSuccessPercent = array.getInteger(R.styleable.SlideView_slideSuccessPercent, 0);
            mSlideBackground = array.getDrawable(R.styleable.SlideView_slideBackground);
            duration = array.getInteger(R.styleable.SlideView_duration, 200);

            slideText = array.getString(R.styleable.SlideView_slideText);
            slideTextSize = array.getDimensionPixelSize(R.styleable.SlideView_slideTextSize, 16);
            slideTextColor = array.getColor(R.styleable.SlideView_slideTextColor, Color.WHITE);
        } finally {
            array.recycle();
        }

        if (mSlideBackground != null) {
            setBackground(mSlideBackground);
        }

        mSlideIcon = new ImageView(getContext());
        if (mSlideImage != null) {
            mSlideIcon.setImageDrawable(mSlideImage);
            mSlideIcon.setPadding(slideSrcMarginLeft, slideSrcMarginTop, slideSrcMarginRight, slideSrcMarginBottom);
        }

        if (slideText != null && !slideText.isEmpty()) {
            mSlideTextView = new TextView(getContext());

            mSlideTextView.setText(slideText);
            if (slideTextColor != null) {
                mSlideTextView.setTextColor(slideTextColor);
            }

            mSlideTextView.setTextSize(slideTextSize);
            addView(mSlideTextView);

            LayoutParams layoutParams = (LayoutParams) mSlideTextView.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mSlideTextView.setLayoutParams(layoutParams);
        }

        addView(mSlideIcon);
        LayoutParams layoutParams = (LayoutParams) mSlideIcon.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mSlideIcon.setLayoutParams(layoutParams);

        //Log.d(this.getClass().getName(), "getWidth()) : " + getWidth());
        //Log.d(this.getClass().getName(), "mSlideIcon()) : " + mSlideIcon.getWidth());

        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getPercent == 0) {
            if (slideSuccessPercent == 0) {
                getPercent = ((getWidth()) - mSlideIcon.getWidth()) / 2;
            } else {
                getPercent = (((getWidth() * slideSuccessPercent) / 100)) - (mSlideIcon.getWidth() / 2);
            }
        }

        if (isCanTouch) {
            float maxRight = (getWidth()) - mSlideIcon.getWidth();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    storeX = event.getRawX();
                    if (mSlideIcon.getTag() == null) {
                        mSlideIcon.setTag(mSlideIcon.getX());
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    mSlideTextView.setVisibility(INVISIBLE);
                    float sum = Math.abs(storeX - event.getRawX());

                    if (event.getRawX() < storeX) {
                        mSlideIcon.animate().setDuration(0).x((float) mSlideIcon.getTag()).start();
                    } else if (sum > maxRight) {
                        mSlideIcon.animate().setDuration(0).x(maxRight).start();
                    } else {
                        mSlideIcon.animate().setDuration(0).x(sum).start();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mSlideTextView.setVisibility(VISIBLE);
                    isCanTouch = false;

                    if (mSlideIcon.getX() < getPercent) {
                        mSlideIcon.animate().setDuration(duration).x((float) mSlideIcon.getTag()).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                isCanTouch = true;
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        }).start();
                    } else {
                        mSlideIcon.animate().setDuration(duration).x(maxRight).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (onFinishListener != null) {
                                    onFinishListener.onFinish();
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();
                    }
                    break;

                default:
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void reset() {
        if (mSlideIcon != null && mSlideIcon.getTag() != null) {
            mSlideIcon.animate().setListener(null);
            mSlideIcon.animate().setDuration(0).x((float) mSlideIcon.getTag()).start();
            isCanTouch = true;
        }
    }

    public void setOnFinishListener(OnFinishListener listener) {
        this.onFinishListener = listener;
    }

    public interface OnFinishListener {
        void onFinish();
    }
}