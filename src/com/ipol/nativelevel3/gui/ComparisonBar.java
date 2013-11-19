package com.ipol.nativelevel3.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.ipol.nativelevel3.R;

public class ComparisonBar extends View {
	
	private static int BAR_HEIGHT;
	private static int BAR_MARGIN_TOP;
	private static int TEXT_SIZE;
	private static int VALUE_MARGIN_SIDE;
	private static int VALUE_MARGIN_TOP;
	private static int MIDDLE_BAR_WIDTH;
	
	private String name;
	private String valueLeft;
	private String valueRight;
	private float relationHome;
	
	private int namePosX;
	private int barPosY;
	private int rightValuePosX;
	
	private Paint brightPaint;
	private Paint darkPaint;
	private TextPaint namePaint;
	private TextPaint valuePaint;

	public ComparisonBar(Context context) {
		super(context);
		initView(context);
	}
	public ComparisonBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	public ComparisonBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public void initView(Context contetx) {
		initValue();
		initPaints();
//		setBackgroundColor(Color.parseColor("#5500ff00"));
	}

	public void initPaints() {
		brightPaint = new Paint();
		brightPaint.setColor(Color.parseColor("#7baae2"));
		
		darkPaint = new Paint();
		darkPaint.setColor(Color.parseColor("#4f6d89"));
		
		namePaint = new TextPaint();
		namePaint.setColor(Color.WHITE);
		namePaint.setTextSize(TEXT_SIZE);
		namePaint.setAntiAlias(true);
		
		valuePaint = new TextPaint();
		valuePaint.setColor(Color.WHITE);
		valuePaint.setTypeface(Typeface.DEFAULT_BOLD);
		valuePaint.setTextSize(TEXT_SIZE);
		valuePaint.setAntiAlias(true);
	}
	
	public void initValue() {
		Resources res = getResources();
		BAR_HEIGHT = (int) res.getDimension(R.dimen.comparison_bar_height);
		BAR_MARGIN_TOP = (int) res.getDimension(R.dimen.comparison_bar_margin_top);
		TEXT_SIZE = (int) res.getDimension(R.dimen.comparison_bar_text_size_name);
		VALUE_MARGIN_SIDE = (int) res.getDimension(R.dimen.comparison_bar_value_margin_side);
		VALUE_MARGIN_TOP = (int) res.getDimension(R.dimen.comparison_bar_value_margin_top);
		MIDDLE_BAR_WIDTH = (int) res.getDimension(R.dimen.comparison_bar_middle_bar_width);
		
		barPosY = TEXT_SIZE + BAR_MARGIN_TOP;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (name != null)
			canvas.drawText(name, namePosX, TEXT_SIZE, namePaint);
		canvas.drawRect(0, barPosY, getWidth(), barPosY + BAR_HEIGHT, darkPaint);
		canvas.drawRect(0, barPosY, (getWidth() / 100) * relationHome, barPosY + BAR_HEIGHT, brightPaint);
		canvas.drawText(valueLeft, VALUE_MARGIN_SIDE, barPosY + VALUE_MARGIN_TOP + TEXT_SIZE, valuePaint);
		canvas.drawText(valueRight, rightValuePosX, barPosY + VALUE_MARGIN_TOP + TEXT_SIZE, valuePaint);
		darkPaint.setColor(Color.parseColor("#29000000"));
		canvas.drawRect(getWidth() / 2 - MIDDLE_BAR_WIDTH / 2, barPosY, getWidth() / 2 + MIDDLE_BAR_WIDTH / 2, barPosY + BAR_HEIGHT, darkPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int desiredWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		int width;
		int height = TEXT_SIZE + BAR_HEIGHT;

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
	
	public void setData(final String name, final String valueLeft, final String valueRight, float relationHome) {
		this.name = name;
		this.valueLeft = valueLeft;
		this.valueRight = valueRight;
		this.relationHome = relationHome;
		
		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
				namePosX = (int) (getWidth() / 2 - namePaint.measureText(name) / 2);
				rightValuePosX = (int) (getWidth() - VALUE_MARGIN_SIDE - valuePaint.measureText(valueRight));
			}
		});
	}

}
