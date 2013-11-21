package com.ipol.nativelevel3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ipol.nativelevel3.gui.TopList;
import com.ipol.nativelevel3.gui.TopListBar;
import com.ipol.nativelevel3.gui.TopListBar.BarStyle;

public class Top10Activity extends Activity {
	
	private TopList topList;
	
//	private TopListBar topListBar1;
//	private TopListBar topListBar2;
//	private TopListBar topListBar3;
//	private TopListBar topListBar4;
//	private TopListBar topListBar5;
	
	private Button animateButton;
	private Button styleButton;
	private Button valuesButton;
	private boolean animated;
	private boolean halfBar;
	private boolean twoValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top10);
		animateButton = (Button) findViewById(R.id.animate_button);
		styleButton = (Button) findViewById(R.id.style_button);
		valuesButton = (Button) findViewById(R.id.values_button);
		
		topList = (TopList) findViewById(R.id.top_list);
//		topListBar1 = (TopListBar) findViewById(R.id.top_list_bar1);
//		topListBar2 = (TopListBar) findViewById(R.id.top_list_bar2);
//		topListBar3 = (TopListBar) findViewById(R.id.top_list_bar3);
//		topListBar4 = (TopListBar) findViewById(R.id.top_list_bar4);
//		topListBar5 = (TopListBar) findViewById(R.id.top_list_bar5);
		
		topList.setStyle(TopListBar.BarStyle.FULL_WIDTH);
		topList.addEntry("Diego", "1.", "Wolfsburg", "42", 100, null);
		topList.addEntry("Mirko Boland", "2.", "B'schweig", "34", 80.95f, null);
		topList.addEntry("Stefan Kie§ling", "3.", "Leverkusen", "32", 76.19f, null);
		topList.addEntry("M. Beister", "4.", "HSV", "29", 69.05f, null);
		topList.addEntry("Shinji Okazaki", null, "Mainz", "29", 69.05f, null);
		topList.addEntry("van der Vaart", null, "HSV", "29", 69.05f, null);
		topList.addEntry("Kevin Volland", null, "Hoffenheim", "29", 69.05f, null);
		topList.addEntry("Adrian Ramos", "8.", "Hertha", "28", 66.67f, null);
		topList.addEntry("Hajime Hosogai", "9.", "Hertha", "27", 64.29f, null);
		topList.addEntry("Anthony Modeste", null, "Hoffenheim", "27", 64.29f, null);
		
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
		} else {
			animated = true;
			animateButton.setText("Reset");
			
			topList.showBars();
		}
	}
	
	public void toggleStyle(View view) {
		if (halfBar) {
			halfBar = false;
			styleButton.setText("Half Bar");
			
			topList.setStyle(BarStyle.FULL_WIDTH);
		} else {
			halfBar = true;
			styleButton.setText("Full Bar");
			
			topList.setStyle(BarStyle.HALF_WIDTH);
		}
	}
	
	public void toggleValues(View view) {
		if (twoValues) {
			twoValues = false;
			valuesButton.setText("Two Values");
			styleButton.setEnabled(true);
			styleButton.setText("Half Bar");
			halfBar = false;
			
			topList.reset();
			topList.addEntry("Diego", "1.", "Wolfsburg", "42", 100, "(7)");
			topList.addEntry("Mirko Boland", "2.", "B'schweig", "34", 80.95f, null);
			topList.addEntry("Stefan Kie§ling", "3.", "Leverkusen", "32", 76.19f, null);
			topList.addEntry("M. Beister", "4.", "HSV", "29", 69.05f, null);
			topList.addEntry("Shinji Okazaki", null, "Mainz", "29", 69.05f, null);
			topList.addEntry("van der Vaart", null, "HSV", "29", 69.05f, null);
			topList.addEntry("Kevin Volland", null, "Hoffenheim", "29", 69.05f, null);
			topList.addEntry("Adrian Ramos", "8.", "Hertha", "28", 66.67f, null);
			topList.addEntry("Hajime Hosogai", "9.", "Hertha", "27", 64.29f, null);
			topList.addEntry("Anthony Modeste", null, "Hoffenheim", "27", 64.29f, null);
		} else {
			twoValues = true;
			valuesButton.setText("One Value");
			styleButton.setEnabled(false);
			animated = false;
			animateButton.setText("Animate");
			
			topList.reset();
			topList.addEntry("Sidney Sam", "1.", "Leverkusen", "7", 53.85f, "6", 100, "13");
			topList.addEntry("R. Lewandowski", "2.", "Dortmund", "9", 69.23f, "3", 92.31f, "12");
			topList.addEntry("Marco Reus", null, "Dortmund", "7", 53.85f, "5", 92.31f, "12");
			topList.addEntry("Sidney Sam", "1.", "Leverkusen", "7", 53.85f, "6", 100, "13");
			topList.addEntry("R. Lewandowski", "2.", "Dortmund", "9", 69.23f, "3", 92.31f, "12");
			topList.addEntry("Marco Reus", null, "Dortmund", "7", 53.85f, "5", 92.31f, "12");
			topList.addEntry("Sidney Sam", "1.", "Leverkusen", "7", 53.85f, "6", 100, "13");
			topList.addEntry("R. Lewandowski", "2.", "Dortmund", "9", 69.23f, "3", 92.31f, "12");
			topList.addEntry("Marco Reus", null, "Dortmund", "7", 53.85f, "5", 92.31f, "12");
			topList.addEntry("Sidney Sam", "1.", "Leverkusen", "7", 53.85f, "6", 100, "13");
		}
	}

	
}
