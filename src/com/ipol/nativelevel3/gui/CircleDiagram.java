package com.ipol.nativelevel3.gui;

import com.ipol.nativelevel3.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class CircleDiagram extends View {
	
	private static int TEXT_SIZE;
	
	private TextPaint namePaint;

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

	public void initView(Context contetx) {
		initValues();
		initPaints();
	}
	
	public void initValues() {
		Resources res = getResources();
		TEXT_SIZE = (int) res.getDimension(R.dimen.statistics_name_text_size);
	}
	
	public void initPaints() {
		namePaint = new TextPaint();
		namePaint.setColor(Color.WHITE);
		namePaint.setTextSize(TEXT_SIZE);
		namePaint.setAntiAlias(true);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
}
