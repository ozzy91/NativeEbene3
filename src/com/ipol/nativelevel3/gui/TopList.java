package com.ipol.nativelevel3.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipol.nativelevel3.R;
import com.ipol.nativelevel3.gui.TopListBar.BarStyle;

public class TopList extends LinearLayout {

	private static final int TIME_BETWEEN_ANIMATION = 150;

	private Context context;

	private ArrayList<TopListBar> entries;
	private TopListBar.BarStyle style = TopListBar.BarStyle.FULL_WIDTH;
	private boolean even;

	public TopList(Context context) {
		super(context);
		initView(context);
	}

	public TopList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TopList(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public void initView(Context context) {
		this.context = context;
		setOrientation(LinearLayout.VERTICAL);
		entries = new ArrayList<TopListBar>();

		TextView source = new TextView(context);
		source.setLayoutParams(getDefaultParams());
		source.setText("Quelle: Opta");
		source.setTextColor(Color.parseColor("#676767"));
		source.setTextSize(11);
		addView(source);
	}

	public void addEntry(String name, String position, String teamname,
			String value, float relation, String total) {
		TopListBar bar = new TopListBar(context);
		bar.setData(name, position, teamname, value, relation, total);
		bar.setLayoutParams(getDefaultParams());
		bar.setEven(even);
		bar.setStyle(style);
		even = !even;
		addView(bar, getChildCount() - 1);
		entries.add(bar);
	}

	public void addEntry(String name, String position, String teamname,
			String firstValue, float firstRelation, String secondValue,
			float secondRelation, String total) {
		setStyle(BarStyle.TWO_VALUES);
		TopListBar bar = new TopListBar(context);
		bar.setData(name, position, teamname, firstValue, firstRelation,
				secondValue, secondRelation, total);
		bar.setLayoutParams(getDefaultParams());
		bar.setEven(even);
		bar.setStyle(style);
		even = !even;
		addView(bar, getChildCount() - 1);
		entries.add(bar);
	}

	public void setStyle(BarStyle style) {
		if (style != null) {
			this.style = style;
			for (TopListBar bar : entries)
				bar.setStyle(style);
		}
	}

	public void showBars() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (final TopListBar bar : entries) {
					try {
						Thread.sleep(TIME_BETWEEN_ANIMATION);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					((Activity) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							bar.showBar();
						}
					});
				}
			}
		}).start();
	}

	public void hideBars() {
		for (TopListBar bar : entries)
			bar.hideBar();
	}

	public LinearLayout.LayoutParams getDefaultParams() {
		int elementMargin = (int) getResources().getDimension(
				R.dimen.top_list_element_margin);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.bottomMargin = elementMargin;
		return params;
	}

	public void reset() {
		removeAllViews();
		style = BarStyle.FULL_WIDTH;
		initView(context);
	}
}
