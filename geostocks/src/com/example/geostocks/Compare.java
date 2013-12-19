package com.example.geostocks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/*
 * Compare:
 * 
 * Written By: Joakim Bajoul Kakaei (881129-0298)
 * 
 * Description:
 * To be updated.
 */
public class Compare extends Activity {
	Menu m;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		m = menu; // adds a reference to the variable m (menu).
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return (super.onCreateOptionsMenu(menu)); // returns the super for
													// efficiency.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_favorites:

			Intent intent = new Intent(this, FavoriteView.class);
			this.startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
