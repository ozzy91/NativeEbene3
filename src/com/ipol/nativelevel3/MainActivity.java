package com.ipol.nativelevel3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.ipol.nativelevel3.gui.AverageBars;
import com.ipol.nativelevel3.gui.ComparisonBar;

public class MainActivity extends Activity {
	
	private ComparisonBar comparisonBar1;
	private ComparisonBar comparisonBar2;
	private ComparisonBar comparisonBar3;
	private ComparisonBar comparisonBar4;
	private ComparisonBar comparisonBar5;
	
	private AverageBars averageBar1;

	private Button animateButton;
	private boolean animated;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		animateButton = (Button) findViewById(R.id.animate_button);
		comparisonBar1 = (ComparisonBar) findViewById(R.id.comparison_bar1);
		comparisonBar2 = (ComparisonBar) findViewById(R.id.comparison_bar2);
		comparisonBar3 = (ComparisonBar) findViewById(R.id.comparison_bar3);
		comparisonBar4 = (ComparisonBar) findViewById(R.id.comparison_bar4);
		comparisonBar5 = (ComparisonBar) findViewById(R.id.comparison_bar5);
		averageBar1 = (AverageBars) findViewById(R.id.average_bars1);
		
		comparisonBar1.setData("Ballkontakte", "915", "466", 66.26f);
		comparisonBar2.setData("Ballbesitz in %", "72", "28", 72.3f);
		comparisonBar3.setData("Passquote in %", "91", "78", 53.97f);
		comparisonBar4.setData("Erfolgreiche Pässe", "657", "193", 77.29f);
		comparisonBar5.setData("Kurzpässe", "629", "159", 79.82f);
		
		averageBar1.setData("Ballkontakte", "863", "623", 100f, 72.2f);
	}
	
	public void animate(View view) {
		if (animated) {
			System.out.println("reset");
			animated = false;
			animateButton.setText("Animate");
			
			comparisonBar1.hideBar();
			comparisonBar2.hideBar();
			comparisonBar3.hideBar();
			comparisonBar4.hideBar();
			comparisonBar5.hideBar();
			
			averageBar1.hideBar();
		} else {
			System.out.println("animate");
			animated = true;
			animateButton.setText("Reset");
			
			comparisonBar1.showBar();
			comparisonBar2.showBar();
			comparisonBar3.showBar();
			comparisonBar4.showBar();
			comparisonBar5.showBar();
			
			averageBar1.showBar();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
