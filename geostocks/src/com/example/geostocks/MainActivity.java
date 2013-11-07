package com.example.geostocks;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

public class MainActivity extends Activity {

	public Menu m;  	// Variable to store a menu-item as a public variable.
						// This helps with data allocation; instead of declaring the
						// searchview and searchmanager in the creation of th application,
						// it will wait to do it once the user decides to start the search.
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		m = menu; //adds a reference to the variable m (menu).
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return (super.onCreateOptionsMenu(menu)); // returns the super for efficiency.
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			openSearch();  // when searchbutton is clicked, it will start the opensearch method.
			return true;
		case R.id.action_settings:
			// openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
/*
 * Written by: Joakim Bajoul Kakaei
 * Description: Invoked when user presses search icon.
 * Opens up the searchview in the menu.
 */
	public void openSearch() {
		/* snippet taken from http://developer.android.com/training/search/setup.html
		 * with some changes to it.
		 */
		SearchManager sm =
		           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		    SearchView sv =
		            (SearchView) m.findItem(R.id.search).getActionView();
		    sv.setSearchableInfo(
		            sm.getSearchableInfo(getComponentName()));
	}
}
