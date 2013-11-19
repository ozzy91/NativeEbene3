package com.ipol.nativelevel3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.ipol.nativelevel3.gui.ComparisonBar;

public class MainActivity extends Activity {
	
	private ComparisonBar comparisonBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		comparisonBar = (ComparisonBar) findViewById(R.id.comparison_bar);
		comparisonBar.setData("Ballkontakte", "781", "559", 58.28f);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
