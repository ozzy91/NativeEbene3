package com.ipol.nativelevel3;

import com.ipol.nativelevel3.gui.LineupStatisticView;

import android.app.Activity;
import android.os.Bundle;

public class LineupActivity extends Activity {
	
	private LineupStatisticView lineup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lineup);
		lineup = (LineupStatisticView) findViewById(R.id.lineup);
		
		lineup.testCode();
	}


}
