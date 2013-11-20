package com.ipol.nativelevel3.gui;

import com.ipol.nativelevel3.R;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;

public class CircleDiagram extends View {

	private static int TEXT_SIZE;
	private static int NAME_MARGIN_BOTTOM;
	private static int CIRCLE_WIDTH;
	private static int VALUE_CIRCLE_WIDTH;
	private static int MARGIN_BETWEEN_CIRCLES;
	private static int VALUE_CURRENT_TEXT_SIZE;
	private static int VALUE_AVERAGE_TEXT_SIZE;
	private static int PERCENT_MARGIN;
	private static int VALUE_TEXT_OFFSET;

	private String name;
	private float valueCurrent;
	private float valueAverage;
	private float animationValueCurrent;
	private float animationValueAverage;
	private String valueCurrentString;
	private String valueAverageString;
	private String percent = "%";

	private RectF currentRect;
	private RectF averageRect;
	private float currentValueWidth;
	private float averageValueWidth;
	private int currentValuePosX;
	private int currentValuePosY;
	private int averageValuePosX;
	private int averageValuePosY;

	private Paint disabledPaint;
	private Paint bluePaint;
	private Paint grayPaint;
	private Paint clearPaint;
	private TextPaint namePaint;
	private TextPaint valuePaint;

	private boolean showBar;
	private boolean animationStarted;

	public CircleDiagram(Context context) {
		super(context);
		initView(context);
	}

	public CircleDiagram(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public CircleDiagram(Context context, AttributeSet attrs, int defStyleAttr) {
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
		NAME_MARGIN_BOTTOM = (int) res
				.getDimension(R.dimen.circle_diagram_name_margin_bottom);
		CIRCLE_WIDTH = (int) res
				.getDimension(R.dimen.circle_diagram_circle_width);
		VALUE_CIRCLE_WIDTH = (int) res
				.getDimension(R.dimen.circle_diagram_value_circle_width);
		MARGIN_BETWEEN_CIRCLES = (int) res
				.getDimension(R.dimen.circle_diagram_margin_between_circles);
		VALUE_CURRENT_TEXT_SIZE = (int) res
				.getDimension(R.dimen.circle_diagram_value_current_text_size);
		VALUE_AVERAGE_TEXT_SIZE = (int) res
				.getDimension(R.dimen.circle_diagram_value_average_text_size);
		PERCENT_MARGIN = (int) res
				.getDimension(R.dimen.circle_diagram_percent_margin);
		VALUE_TEXT_OFFSET = (int) res
				.getDimension(R.dimen.circle_diagram_value_text_offset);

	}

	public void initPaints() {
		namePaint = new TextPaint();
		namePaint.setColor(Color.WHITE);
		namePaint.setTextSize(TEXT_SIZE);
		namePaint.setAntiAlias(true);
		
		valuePaint = new TextPaint();
		valuePaint.setAntiAlias(true);
		valuePaint.setTypeface(Typeface.DEFAULT_BOLD);

		disabledPaint = new Paint();
		disabledPaint.setColor(Color.parseColor("#44aaaaaa"));
		disabledPaint.setAntiAlias(true);
		disabledPaint.setStrokeWidth(CIRCLE_WIDTH);
		disabledPaint.setStyle(Paint.Style.STROKE);

		bluePaint = new Paint();
		bluePaint.setColor(Color.parseColor("#57779b"));
		bluePaint.setAntiAlias(true);
		bluePaint.setStrokeWidth(VALUE_CIRCLE_WIDTH);
		bluePaint.setStyle(Paint.Style.STROKE);

		grayPaint = new Paint();
		grayPaint.setColor(Color.parseColor("#676767"));
		grayPaint.setAntiAlias(true);
		grayPaint.setStrokeWidth(VALUE_CIRCLE_WIDTH);
		grayPaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// draw name
		if (name != null)
			canvas.drawText(name, 0, TEXT_SIZE, namePaint);

		canvas.drawCircle(getWidth() / 2, getWidth() / 2 + TEXT_SIZE
				+ NAME_MARGIN_BOTTOM, getWidth() / 2 - CIRCLE_WIDTH / 2,
				disabledPaint);
		
		valuePaint.setTextSize(VALUE_CURRENT_TEXT_SIZE);
		valuePaint.setColor(Color.parseColor("#57779b"));
		canvas.drawText(valueCurrentString, currentValuePosX, currentValuePosY, valuePaint);
		valuePaint.setTextSize(VALUE_AVERAGE_TEXT_SIZE);
		canvas.drawText(percent, currentValuePosX + currentValueWidth + PERCENT_MARGIN, currentValuePosY, valuePaint);
		
		
		valuePaint.setColor(Color.parseColor("#676767"));
		canvas.drawText(valueAverageString, averageValuePosX, averageValuePosY, valuePaint);
		canvas.drawText(percent, averageValuePosX + averageValueWidth + PERCENT_MARGIN, averageValuePosY, valuePaint);

		if (showBar) {
			if (!animationStarted) {
				ObjectAnimator currentAnimator = ObjectAnimator.ofFloat(this,
						"animationValueCurrent", valueCurrent);
				currentAnimator.setDuration(1200);
				currentAnimator.setInterpolator(new DecelerateInterpolator());
				ObjectAnimator averageAnimator = ObjectAnimator.ofFloat(this,
						"animationValueAverage", valueAverage);
				averageAnimator.setDuration(1200);
				averageAnimator.setInterpolator(new DecelerateInterpolator());
				currentAnimator.start();
				averageAnimator.start();
				animationStarted = true;
			}

			canvas.drawArc(currentRect, -90, 3.6f * animationValueCurrent,
					false, bluePaint);
			canvas.drawArc(averageRect, -90, 3.6f * animationValueAverage,
					false, grayPaint);

			// if (valueCurrent != null)
			// canvas.drawText(valueCurrent, (getWidth() / 100)
			// * animationRelationCurrent - currentValueWidth
			// - VALUE_MARGIN_SIDE, blueBarPosY + VALUE_MARGIN_TOP
			// + VALUES_TEXT_SIZE, valuePaint);
			// if (valueAverage != null)
			// canvas.drawText(valueAverage, (getWidth() / 100)
			// * animationRelationAverage - averageValueWidth
			// - VALUE_MARGIN_SIDE, grayBarPosY + VALUE_MARGIN_TOP
			// + VALUES_TEXT_SIZE, valuePaint);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int desiredWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 150, getResources()
						.getDisplayMetrics());

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		int width;
		int height;

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

		height = width + TEXT_SIZE + NAME_MARGIN_BOTTOM;

		// MUST CALL THIS
		setMeasuredDimension(width, height);
	}

	public void showBar() {
		showBar = true;
		invalidate();
	}

	public void hideBar() {
		showBar = false;
		animationValueCurrent = 0f;
		animationValueAverage = 0f;
		animationStarted = false;
		invalidate();
	}

	public void setData(final String name, final float valueCurrent,
			final float valueAverage) {
		this.name = name;
		this.valueCurrent = valueCurrent;
		this.valueAverage = valueAverage;

		valueCurrentString = "" + (int) valueCurrent;
		valueAverageString = "" + (int) valueAverage;
		
		valuePaint.setTextSize(VALUE_CURRENT_TEXT_SIZE);
		currentValueWidth = valuePaint.measureText(valueCurrentString);
		
		valuePaint.setTextSize(VALUE_AVERAGE_TEXT_SIZE);
		averageValueWidth = valuePaint.measureText(valueAverageString);
		
		getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);

						float halfValueCircleWidth = VALUE_CIRCLE_WIDTH / 2;
						averageRect = new RectF(halfValueCircleWidth, TEXT_SIZE
								+ NAME_MARGIN_BOTTOM + halfValueCircleWidth,
								getWidth() - halfValueCircleWidth, getHeight()
										- halfValueCircleWidth);
						float difference = VALUE_CIRCLE_WIDTH
								+ MARGIN_BETWEEN_CIRCLES;
						currentRect = new RectF(averageRect.left + difference,
								averageRect.top + difference, averageRect.right
										- difference, averageRect.bottom
										- difference);

						currentValuePosX = (int) (getWidth() / 2 - currentValueWidth + VALUE_TEXT_OFFSET);
						currentValuePosY = getWidth() / 2 + TEXT_SIZE + NAME_MARGIN_BOTTOM;
						averageValuePosX = (int) (getWidth() / 2 - averageValueWidth + VALUE_TEXT_OFFSET);
						averageValuePosY = getWidth() / 2 + TEXT_SIZE + NAME_MARGIN_BOTTOM + VALUE_AVERAGE_TEXT_SIZE;
					}
				});
	}

	public float getAnimationValueCurrent() {
		return animationValueCurrent;
	}
	public void setAnimationValueCurrent(float animationValueCurrent) {
		this.animationValueCurrent = animationValueCurrent;
		invalidate();
	}
	public float getAnimationValueAverage() {
		return animationValueAverage;
	}
	public void setAnimationValueAverage(float animationValueAverage) {
		this.animationValueAverage = animationValueAverage;
		invalidate();
	}
}
