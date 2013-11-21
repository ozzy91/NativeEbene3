package com.ipol.nativelevel3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ipol.nativelevel3.gui.AverageBars;
import com.ipol.nativelevel3.gui.CircleDiagram;
import com.ipol.nativelevel3.gui.ComparisonBar;

public class MainActivity extends Activity {
	
	private ComparisonBar comparisonBar1;
	private ComparisonBar comparisonBar2;
	private ComparisonBar comparisonBar3;
	private ComparisonBar comparisonBar4;
	
	private AverageBars averageBar1;
	private CircleDiagram circleDiagram1;

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
		averageBar1 = (AverageBars) findViewById(R.id.average_bars1);
		circleDiagram1 = (CircleDiagram) findViewById(R.id.circle_diagram1);
		
		comparisonBar1.setData("Ballkontakte", "915", "466", 66.26f);
		comparisonBar2.setData("Ballbesitz in %", "72", "28", 72.3f);
		comparisonBar3.setData("Passquote in %", "91", "78", 53.97f);
		comparisonBar4.setData("Erfolgreiche PŠsse", "657", "193", 77.29f);
		
		averageBar1.setData("Ballkontakte", "863", "623", 100f, 72.2f);
		circleDiagram1.setData("Ballbesitz", 71.01f, 50f);
	}
	
	public void animate(View view) {
		if (animated) {
			animated = false;
			animateButton.setText("Animate");
			
			comparisonBar1.hideBar();
			comparisonBar2.hideBar();
			comparisonBar3.hideBar();
			comparisonBar4.hideBar();
			
			averageBar1.hideBar();
			circleDiagram1.hideBar();
		} else {
			animated = true;
			animateButton.setText("Reset");
			
			comparisonBar1.showBar();
			comparisonBar2.showBar();
			comparisonBar3.showBar();
			comparisonBar4.showBar();
			
			averageBar1.showBar();
			circleDiagram1.showBar();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			Intent intent = new Intent(this, Top10Activity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	

}
