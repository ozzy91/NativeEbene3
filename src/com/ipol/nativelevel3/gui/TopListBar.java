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
	private String value;
	private float relation;
	private float animationRelation;

	private float valueWidth;

	private Paint bluePaint;
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
		bluePaint = new Paint();
		bluePaint.setShader(new LinearGradient(0, 0, 0, BAR_HEIGHT, Color
				.parseColor("#4f6d89"), Color.parseColor("#7baae2"),
				Shader.TileMode.MIRROR));

		labelPaint = new Paint();
		labelPaint.setColor(Color.parseColor("#88000000"));

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
						"animationRelation", relation);
				barAnimator.setDuration(800);
				barAnimator.setInterpolator(new DecelerateInterpolator());
				barAnimator.start();
				animationStarted = true;
			}
			canvas.drawRect(0, 0, (getWidth() / 100) * animationRelation,
					BAR_HEIGHT, bluePaint);

			if (value != null)
				canvas.drawText(value, (getWidth() / 100) * animationRelation
						- valueWidth - VALUE_MARGIN_SIDE, VALUE_MARGIN_TOP
						+ VALUES_TEXT_SIZE, valuePaint);

			if (animationRelation == relation) {
				canvas.drawRect(LABEL_MARGIN_LEFT, 0, LABEL_MARGIN_LEFT
						+ LABEL_WIDTH, BAR_HEIGHT, labelPaint);

				namePaint.setColor(Color.WHITE);
				if (position != null)
					canvas.drawText(position, POSITION_MARGIN_LEFT, TEXT_SIZE, namePaint);
				if (name != null)
					canvas.drawText(name, NAME_MARGIN_LEFT, TEXT_SIZE, namePaint);
				namePaint.setColor(Color.parseColor("#aaaaaa"));
				if (teamname != null)
					canvas.drawText(teamname, NAME_MARGIN_LEFT, TEAMNAME_MARGIN_TOP + 2 * TEXT_SIZE, namePaint);
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
		animationRelation = 0f;
		animationStarted = false;
		invalidate();
	}

	public void setData(final String name, String position, String teamname, String value,
			float relation) {
		this.name = name;
		this.position = position;
		this.teamname = teamname;
		this.value = value;
		this.relation = relation;

		valueWidth = valuePaint.measureText(value);
	}

	public float getAnimationRelation() {
		return animationRelation;
	}

	public void setAnimationRelation(float animationRelation) {
		this.animationRelation = animationRelation;
		invalidate();
	}
}
