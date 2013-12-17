package com.example.geostocks;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
/*
 * Compare:
 * 
 * Written By: Joakim Bajoul Kakaei (881129-0298)
 * 
 * Description:
 * To be updated.
 */
public class Compare extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compare, menu);
		return true;
	}

}
