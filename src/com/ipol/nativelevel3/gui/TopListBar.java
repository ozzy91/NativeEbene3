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
import android.view.animation.DecelerateInterpolator;

import com.ipol.nativelevel3.R;

public class TopListBar extends View {

	private static int TEXT_SIZE;
	private static int VALUES_TEXT_SIZE;
	private static int BAR_HEIGHT;
	private static int VALUE_MARGIN_TOP;
	private static int VALUE_MARGIN_SIDE;
	private static int LABEL_WIDTH;
	private static int LABEL_MARGIN_LEFT;
	private static int NAME_MARGIN_LEFT;
	private static int POSITION_MARGIN_LEFT;
	private static int TEAMNAME_MARGIN_TOP;

	private String name;
	private String teamname;
	private String position;
	private String firstValue;
	private float firstRelation;
	private String secondValue;
	private float secondRelation;
	private String total;
	private float animationFirstRelation;
	private float animationSecondRelation;
	private boolean even;
	private BarStyle style = BarStyle.FULL_WIDTH;

	private float firstValueWidth;
	private float secondValueWidth;
	private float totalWidth;
	private float barLeft;

	private Paint evenBluePaint;
	private Paint oddBluePaint;
	private Paint labelPaint;
	private TextPaint namePaint;
	private TextPaint valuePaint;

	private boolean showBar;
	private boolean animationStarted;

	public TopListBar(Context context) {
		super(context);
		initView(context);
	}

	public TopListBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TopListBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public void initView(Context context) {
		initValues();
		initPaints();
	}

	public void initValues() {
		Resources res = getResources();
		TEXT_SIZE = (int) res.getDimension(R.dimen.top_list_bar_name_size);
		VALUES_TEXT_SIZE = (int) res
				.getDimension(R.dimen.top_list_bar_value_text_size);
		BAR_HEIGHT = (int) res.getDimension(R.dimen.top_list_bar_height);
		VALUE_MARGIN_TOP = (int) res
				.getDimension(R.dimen.top_list_bar_value_margin_top);
		VALUE_MARGIN_SIDE = (int) res
				.getDimension(R.dimen.top_list_bar_value_margin_side);
		LABEL_WIDTH = (int) res.getDimension(R.dimen.top_list_bar_label_width);
		LABEL_MARGIN_LEFT = (int) res
				.getDimension(R.dimen.top_list_bar_label_margin_left);
		NAME_MARGIN_LEFT = (int) res
				.getDimension(R.dimen.top_list_bar_name_margin_left);
		POSITION_MARGIN_LEFT = (int) res
				.getDimension(R.dimen.top_list_bar_position_margin_left);
		TEAMNAME_MARGIN_TOP = (int) res
				.getDimension(R.dimen.top_list_bar_teamname_margin_top);
	}

	public void initPaints() {
		evenBluePaint = new Paint();
		evenBluePaint.setShader(new LinearGradient(0, 0, 0, BAR_HEIGHT, Color
				.parseColor("#5576a1"), Color.parseColor("#374a62"),
				Shader.TileMode.MIRROR));

		oddBluePaint = new Paint();
		oddBluePaint.setShader(new LinearGradient(0, 0, 0, BAR_HEIGHT, Color
				.parseColor("#7baae2"), Color.parseColor("#4f6d89"),
				Shader.TileMode.MIRROR));

		labelPaint = new Paint();
		labelPaint.setColor(Color.parseColor("#A61E1E1E"));

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

		if (showBar) {
			if (!animationStarted) {
				ObjectAnimator barAnimator = ObjectAnimator.ofFloat(this,
						"firstAnimationRelation", firstRelation);
				barAnimator.setDuration(700);
				barAnimator.setInterpolator(new DecelerateInterpolator());
				barAnimator.start();
				if (style == BarStyle.TWO_VALUES) {
					ObjectAnimator secondBarAnimator = ObjectAnimator.ofFloat(
							this, "secondAnimationRelation", secondRelation);
					secondBarAnimator.setDuration(700);
					secondBarAnimator
							.setInterpolator(new DecelerateInterpolator());
					secondBarAnimator.start();
				}
				animationStarted = true;
			}
			if (style == BarStyle.TWO_VALUES) {
				canvas.drawRect(barLeft, 0, barLeft
						+ ((getWidth() - barLeft) / 100)
						* animationSecondRelation, BAR_HEIGHT, oddBluePaint);
				canvas.drawRect(barLeft, 0, barLeft
						+ ((getWidth() - barLeft) / 100)
						* animationFirstRelation, BAR_HEIGHT, evenBluePaint);

				if (secondValue != null) {
					float valueLeft = barLeft + ((getWidth() - barLeft) / 100)
							* animationSecondRelation - secondValueWidth
							- VALUE_MARGIN_SIDE;
					if (valueLeft > barLeft)
						canvas.drawText(secondValue, valueLeft,
								VALUE_MARGIN_TOP + VALUES_TEXT_SIZE, valuePaint);
				}
			} else {
				if (even)
					canvas.drawRect(barLeft, 0, barLeft
							+ ((getWidth() - barLeft) / 100)
							* animationFirstRelation, BAR_HEIGHT, evenBluePaint);
				else
					canvas.drawRect(barLeft, 0, barLeft
							+ ((getWidth() - barLeft) / 100)
							* animationFirstRelation, BAR_HEIGHT, oddBluePaint);
			}

			if (firstValue != null) {
				float valueLeft = barLeft + ((getWidth() - barLeft) / 100)
						* animationFirstRelation - firstValueWidth
						- VALUE_MARGIN_SIDE;
				if (valueLeft > barLeft)
					canvas.drawText(firstValue, valueLeft, VALUE_MARGIN_TOP
							+ VALUES_TEXT_SIZE, valuePaint);
			}

			if (style != BarStyle.FULL_WIDTH
					|| animationFirstRelation == firstRelation) {
				canvas.drawRect(LABEL_MARGIN_LEFT, 0, LABEL_MARGIN_LEFT
						+ LABEL_WIDTH, BAR_HEIGHT, labelPaint);

				if (position != null)
					canvas.drawText(position, POSITION_MARGIN_LEFT, TEXT_SIZE,
							namePaint);
				if (name != null)
					canvas.drawText(name, NAME_MARGIN_LEFT, TEXT_SIZE,
							namePaint);
				if (teamname != null) {
					namePaint.setColor(Color.parseColor("#999999"));
					canvas.drawText(teamname, NAME_MARGIN_LEFT,
							TEAMNAME_MARGIN_TOP + 2 * TEXT_SIZE, namePaint);
					namePaint.setColor(Color.WHITE);
				}
				if (total != null) {
					if (style == BarStyle.TWO_VALUES) {
						valuePaint.setColor(Color.parseColor("#57779b"));
						canvas.drawText(total, LABEL_MARGIN_LEFT + LABEL_WIDTH
								- VALUE_MARGIN_SIDE - totalWidth,
								VALUE_MARGIN_TOP + VALUES_TEXT_SIZE, valuePaint);
						valuePaint.setColor(Color.WHITE);
					} else {
						namePaint.setTextSize(VALUES_TEXT_SIZE);
						canvas.drawText(total, LABEL_MARGIN_LEFT + LABEL_WIDTH
								- VALUE_MARGIN_SIDE - totalWidth,
								VALUE_MARGIN_TOP + VALUES_TEXT_SIZE, namePaint);
						namePaint.setTextSize(TEXT_SIZE);
					}
				}
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int desiredWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 250, getResources()
						.getDisplayMetrics());

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		int width;
		int height = BAR_HEIGHT;

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
		animationFirstRelation = 0f;
		animationSecondRelation = 0f;
		animationStarted = false;
		invalidate();
	}

	public void setData(final String name, String position, String teamname,
			String value, float relation, String total) {
		this.name = name;
		this.position = position;
		this.teamname = teamname;
		this.firstValue = value;
		this.firstRelation = relation;
		this.total = total;

		firstValueWidth = valuePaint.measureText(value);
		if (total != null) {
			namePaint.setTextSize(VALUES_TEXT_SIZE);
			totalWidth = namePaint.measureText(total);
			namePaint.setTextSize(TEXT_SIZE);
		}
	}

	public void setData(final String name, String position, String teamname,
			String firstValue, float firstRelation, String secondValue,
			float secondRelation, String total) {
		setStyle(BarStyle.TWO_VALUES);
		this.name = name;
		this.position = position;
		this.teamname = teamname;
		this.firstValue = firstValue;
		this.firstRelation = firstRelation;
		this.secondValue = secondValue;
		this.secondRelation = secondRelation;
		this.total = total;

		firstValueWidth = valuePaint.measureText(firstValue);
		secondValueWidth = valuePaint.measureText(secondValue);
		totalWidth = valuePaint.measureText(total);
	}

	public void setEven(boolean even) {
		this.even = even;
		if (even) {
			if (style == BarStyle.FULL_WIDTH)
				labelPaint.setColor(Color.parseColor("#CC1E1E1E"));
			else
				labelPaint.setShader(new LinearGradient(0, 0, 0, BAR_HEIGHT,
						Color.parseColor("#0fffffff"), Color
								.parseColor("#05ffffff"),
						Shader.TileMode.MIRROR));
		}
	}

	public void setStyle(BarStyle style) {
		this.style = style;
		if (style == BarStyle.FULL_WIDTH) {
			barLeft = 0;
			labelPaint.setShader(null);
			if (even)
				labelPaint.setColor(Color.parseColor("#CC1E1E1E"));
			else
				labelPaint.setColor(Color.parseColor("#A61E1E1E"));
		} else {
			barLeft = LABEL_WIDTH + 2 * LABEL_MARGIN_LEFT;
			labelPaint.setColor(Color.parseColor("#000000"));
			if (even)
				labelPaint.setShader(new LinearGradient(0, 0, 0, BAR_HEIGHT,
						Color.parseColor("#0fffffff"), Color
								.parseColor("#05ffffff"),
						Shader.TileMode.MIRROR));
			else
				labelPaint.setShader(new LinearGradient(0, 0, 0, BAR_HEIGHT,
						Color.parseColor("#24ffffff"), Color
								.parseColor("#14ffffff"),
						Shader.TileMode.MIRROR));
		}
		invalidate();
	}

	public float getFirstAnimationRelation() {
		return animationFirstRelation;
	}

	public void setFirstAnimationRelation(float animationRelation) {
		this.animationFirstRelation = animationRelation;
		invalidate();
	}

	public float getSecondAnimationRelation() {
		return animationSecondRelation;
	}

	public void setSecondAnimationRelation(float animationRelation) {
		this.animationSecondRelation = animationRelation;
		invalidate();
	}

	public enum BarStyle {
		FULL_WIDTH, HALF_WIDTH, TWO_VALUES
	}
}
