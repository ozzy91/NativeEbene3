package com.ipol.nativelevel3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ipol.nativelevel3.gui.TopList;
import com.ipol.nativelevel3.gui.TopListBar;

public class Top10Activity extends Activity {
	
	private TopList topList;
	
//	private TopListBar topListBar1;
//	private TopListBar topListBar2;
//	private TopListBar topListBar3;
//	private TopListBar topListBar4;
//	private TopListBar topListBar5;
	
	private Button animateButton;
	private boolean animated;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top10);
		animateButton = (Button) findViewById(R.id.animate_button);
		
		topList = (TopList) findViewById(R.id.top_list);
//		topListBar1 = (TopListBar) findViewById(R.id.top_list_bar1);
//		topListBar2 = (TopListBar) findViewById(R.id.top_list_bar2);
//		topListBar3 = (TopListBar) findViewById(R.id.top_list_bar3);
//		topListBar4 = (TopListBar) findViewById(R.id.top_list_bar4);
//		topListBar5 = (TopListBar) findViewById(R.id.top_list_bar5);
		
		topList.addEntry("Diego", "1.", "Wolfsburg", "42", 100);
		topList.addEntry("Mirko Boland", "2.", "B'schweig", "34", 80.95f);
		topList.addEntry("Stefan Kie§ling", "3.", "Leverkusen", "32", 76.19f);
		topList.addEntry("M. Beister", "4.", "HSV", "29", 69.05f);
		topList.addEntry("Shinji Okazaki", null, "Mainz", "29", 69.05f);
		
//		topListBar1.setData("Diego", "1.", "Wolfsburg", "42", 100);
//		topListBar2.setData("Mirko Boland", "2.", "B'schweig", "34", 80.95f);
//		topListBar3.setData("Stefan Kie§ling", "3.", "Leverkusen", "32", 76.19f);
//		topListBar4.setData("M. Beister", "4.", "HSV", "29", 69.05f);
//		topListBar5.setData("Shinji Okazaki", null, "Mainz", "29", 69.05f);
	}
	
	public void animate(View view) {
		if (animated) {
			animated = false;
			animateButton.setText("Animate");
			
			topList.hideBars();
//			topListBar1.hideBar();
//			topListBar2.hideBar();
//			topListBar3.hideBar();
//			topListBar4.hideBar();
//			topListBar5.hideBar();
		} else {
			animated = true;
			animateButton.setText("Reset");
			
			topList.showBars();
//			topListBar1.showBar();
//			topListBar2.showBar();
//			topListBar3.showBar();
//			topListBar4.showBar();
//			topListBar5.showBar();
		}
	}

	
}
