package com.ipol.nativelevel3.gui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;

import com.ipol.nativelevel3.R;

public class AverageBars extends View {

	private static int TEXT_SIZE;
	private static int VALUES_TEXT_SIZE;
	private static int BAR_HEIGHT;
	private static int BLUE_BAR_MARGIN_TOP;
	private static int GRAY_BAR_MARGIN_TOP;
	private static int VALUE_MARGIN_TOP;
	private static int VALUE_MARGIN_SIDE;

	private String name;
	private String valueCurrent;
	private String valueAverage;
	private float relationCurrent;
	private float relationAverage;
	private float animationRelationCurrent;
	private float animationRelationAverage;

	private int namePosX;
	private int blueBarPosY;
	private int grayBarPosY;
	private float currentValueWidth;
	private float averageValueWidth;

	private Paint bluePaint;
	private Paint grayPaint;
	private TextPaint namePaint;
	private TextPaint valuePaint;

	private boolean showBar;
	private boolean animationStarted;

	public AverageBars(Context context) {
		super(context);
		initView(context);
	}

	public AverageBars(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public AverageBars(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public void initView(Context context) {
		initValues();
		initPaints();
	}

	public void initValues() {
		Resources res = getResources();
		TEXT_SIZE = (int) res.getDimension(R.dimen.statistics_name_text_size);
		VALUES_TEXT_SIZE = (int) res
				.getDimension(R.dimen.average_bars_values_text_size);
		BAR_HEIGHT = (int) res.getDimension(R.dimen.average_bars_height);
		BLUE_BAR_MARGIN_TOP = (int) res
				.getDimension(R.dimen.average_bars_blue_bar_margin_top);
		GRAY_BAR_MARGIN_TOP = (int) res
				.getDimension(R.dimen.average_bars_gray_bar_margin_top);
		VALUE_MARGIN_TOP = (int) res
				.getDimension(R.dimen.average_bars_value_margin_top);
		VALUE_MARGIN_SIDE = (int) res
				.getDimension(R.dimen.average_bars_value_margin_side);

		blueBarPosY = TEXT_SIZE + BLUE_BAR_MARGIN_TOP;
		grayBarPosY = blueBarPosY + BAR_HEIGHT + GRAY_BAR_MARGIN_TOP;
	}

	public void initPaints() {
		bluePaint = new Paint();
		bluePaint.setShader(new LinearGradient(0, 0, 0, BAR_HEIGHT, Color
				.parseColor("#4f6d89"), Color.parseColor("#7baae2"),
				Shader.TileMode.MIRROR));

		grayPaint = new Paint();
		grayPaint.setShader(new LinearGradient(0, 0, 0, BAR_HEIGHT, Color
				.parseColor("#696969"), Color.parseColor("#3d3d3d"),
				Shader.TileMode.MIRROR));

		namePaint = new TextPaint();
		namePaint.setColor(Color.WHITE);
		namePaint.setTextSize(TEXT_SIZE);
		namePaint.setAntiAlias(true);

		valuePaint = new TextPaint();
		valuePaint.setColor(Color.WHITE);
		valuePaint.setTextSize(VALUES_TEXT_SIZE);
		valuePaint.setTypeface(Typeface.DEFAULT_BOLD);
		valuePaint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// draw name
		if (name != null)
			canvas.drawText(name, namePosX, TEXT_SIZE, namePaint);

		if (showBar) {
			if (!animationStarted) {
				ObjectAnimator currentAnimator = ObjectAnimator.ofFloat(this,
						"animationRelationCurrent", relationCurrent);
				currentAnimator.setDuration(800);
				currentAnimator.setInterpolator(new DecelerateInterpolator());
				ObjectAnimator averageAnimator = ObjectAnimator.ofFloat(this,
						"animationRelationAverage", relationAverage);
				averageAnimator.setDuration(800);
				averageAnimator.setInterpolator(new DecelerateInterpolator());
				currentAnimator.start();
				averageAnimator.start();
				animationStarted = true;
			}
			canvas.drawRect(0, blueBarPosY, (getWidth() / 100)
					* animationRelationCurrent, blueBarPosY + BAR_HEIGHT,
					bluePaint);
			canvas.drawRect(0, grayBarPosY, (getWidth() / 100)
					* animationRelationAverage, grayBarPosY + BAR_HEIGHT,
					grayPaint);

			if (valueCurrent != null)
				canvas.drawText(valueCurrent, (getWidth() / 100)
						* animationRelationCurrent - currentValueWidth
						- VALUE_MARGIN_SIDE, blueBarPosY + VALUE_MARGIN_TOP
						+ VALUES_TEXT_SIZE, valuePaint);
			if (valueAverage != null)
				canvas.drawText(valueAverage, (getWidth() / 100)
						* animationRelationAverage - averageValueWidth
						- VALUE_MARGIN_SIDE, grayBarPosY + VALUE_MARGIN_TOP
						+ VALUES_TEXT_SIZE, valuePaint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int desiredWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 200, getResources()
						.getDisplayMetrics());

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		int width;
		int height = TEXT_SIZE + BLUE_BAR_MARGIN_TOP + GRAY_BAR_MARGIN_TOP + 2
				* BAR_HEIGHT;

		// Measure Width
		if (widthMode == MeasureSpec.EXACTLY) {
			// Must be this size
			width = widthSize;
		} else if (widthMode == MeasureSpec.AT_MOST) {
			// Can't be bigger than...
			width = Math.min(desiredWidth, widthSize);
		} else {
			// Be whatever you want
			width = desiredWidth;
		}

		// MUST CALL THIS
		setMeasuredDimension(width, height);
	}

	public void showBar() {
		showBar = true;
		invalidate();
	}

	public void hideBar() {
		showBar = false;
		animationRelationCurrent = 0f;
		animationRelationAverage = 0f;
		animationStarted = false;
		invalidate();
	}

	public void setData(final String name, final String valueCurrent,
			final String valueAverage, float relationCurrent,
			float relationAverage) {
		this.name = name;
		this.valueCurrent = valueCurrent;
		this.valueAverage = valueAverage;
		this.relationCurrent = relationCurrent;
		this.relationAverage = relationAverage;

		currentValueWidth = valuePaint.measureText(valueCurrent);
		averageValueWidth = valuePaint.measureText(valueAverage);

		getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
	}

	public float getAnimationRelationCurrent() {
		return animationRelationCurrent;
	}

	public void setAnimationRelationCurrent(float animationRelationCurrent) {
		this.animationRelationCurrent = animationRelationCurrent;
		invalidate();
	}

	public float getAnimationRelationAverage() {
		return animationRelationAverage;
	}

	public void setAnimationRelationAverage(float animationRelationAverage) {
		this.animationRelationAverage = animationRelationAverage;
		invalidate();
	}
}
