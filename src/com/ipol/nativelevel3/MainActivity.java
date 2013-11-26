package com.ipol.nativelevel3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ipol.nativelevel3.gui.AverageBarsView;
import com.ipol.nativelevel3.gui.CircleDiagramView;
import com.ipol.nativelevel3.gui.ComparisonBarView;

public class MainActivity extends Activity {
	
	private ComparisonBarView comparisonBar1;
	private ComparisonBarView comparisonBar2;
	private ComparisonBarView comparisonBar3;
	private ComparisonBarView comparisonBar4;
	
	private AverageBarsView averageBar1;
	private CircleDiagramView circleDiagram1;

	private Button animateButton;
	private boolean animated;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		animateButton = (Button) findViewById(R.id.animate_button);
		comparisonBar1 = (ComparisonBarView) findViewById(R.id.comparison_bar1);
		comparisonBar2 = (ComparisonBarView) findViewById(R.id.comparison_bar2);
		comparisonBar3 = (ComparisonBarView) findViewById(R.id.comparison_bar3);
		comparisonBar4 = (ComparisonBarView) findViewById(R.id.comparison_bar4);
		averageBar1 = (AverageBarsView) findViewById(R.id.average_bars1);
//		averageBar2 = (AverageBarsView) findViewById(R.id.average_bars2);
		circleDiagram1 = (CircleDiagramView) findViewById(R.id.circle_diagram1);
		
		comparisonBar1.setData("915", "466", 66.26f);
		comparisonBar2.setData("72", "28", 72.3f);
		comparisonBar3.setData("91", "78", 53.97f);
		comparisonBar4.setData("657", "193", 77.29f);
		
		averageBar1.setData("Ballkontakte", "863", "623", 100f, 72.2f);
//		averageBar2.setData("Ballkontakte", "863", "623", 100f, 72.2f);
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
//			averageBar2.hideBar();
			circleDiagram1.hideBar();
		} else {
			animated = true;
			animateButton.setText("Reset");
			
			comparisonBar1.showBar();
			comparisonBar2.showBar();
			comparisonBar3.showBar();
			comparisonBar4.showBar();
			
			averageBar1.showBar();
//			averageBar2.showBar();
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
		if (item.getItemId() == R.id.action_top10) {
			Intent intent = new Intent(this, Top10Activity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.action_lineup) {
			Intent intent = new Intent(this, LineupActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	

}
