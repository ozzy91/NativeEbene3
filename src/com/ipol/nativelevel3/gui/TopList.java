package com.ipol.nativelevel3.gui;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;

import com.ipol.nativelevel3.R;

public class TopList extends LinearLayout {

	private Context context;

	private ArrayList<TopListBar> entries;

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
	}

	public void addEntry(String name, String position, String teamname, String value, float relation) {
		TopListBar bar = new TopListBar(context);
		bar.setData(name, position, teamname, value, relation);
		bar.setLayoutParams(getDefaultParams());
		addView(bar);
		entries.add(bar);
	}
	
	public void showBars() {
		for (TopListBar bar : entries)
			bar.showBar();
	}

	public void hideBars() {
		for (TopListBar bar : entries)
			bar.hideBar();
	}

	public LinearLayout.LayoutParams getDefaultParams() {
		int elementMargin = (int) getResources().getDimension(R.dimen.top_list_element_margin);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.bottomMargin = elementMargin;
		return params;
	}
}
