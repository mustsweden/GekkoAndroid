package com.example.geostocks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.geostocks.R;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

/* MainActivity.
 * Made by: Joakim Bajoul Kakaei (881129-0298)
 * Description: Creates and sets up the MainActivity programmatically. 
 * 				It is mainly associated with activity_main.xml, but uses the
 * 				listview_layout.xml in connection to it.
 * 
 * 				The idea was to keep the class as "clean" as possible and use 
 * 				multiple classes to help making coding and efficiency more easier.
 */
public class MainActivity extends Activity {
	public static ArrayList<String> checkedCompanies = new ArrayList<String>();
	public Menu m; // Variable to store a menu-item as a public variable.
					// This helps with data allocation; instead of declaring the
					// searchview and searchmanager in the creation of the
					// application,
					// it will wait to do it once the user decides to start the
					// search.

	// an array to store all jsonobjects in.
	private JSONArray companies;
	// an arraylist to store multiple companyobjects created later.
	private List<companiesBuilder> allCompanies = new ArrayList<companiesBuilder>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			/*
			 * executes the AsyncTask (top10). When the task is executed, it
			 * then gets the JSONArray which is bouncing around.
			 */
			companies = new top10().execute("DO IT!").get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * The following snippet mainly creates the adapterobject and associates
		 * it with it's elements, context and layout.
		 */

		final ListView companyList = (ListView) findViewById(R.id.listView);
		final companiesAdapter compAdp = new companiesAdapter(this,
				R.layout.listview_layout);

		System.out.println("after"); // debugging
		/*
		 * a loop to create companyBuilder-objects from the JSONArray and then
		 * add those objects to an ArrayList (allCompanies).
		 */
		for (int i = 0; companies.length() > i; i++) {
			System.out.println("companies-looper"); // debugging

			System.out.println(companies.length()); // debugging
			try {
				System.out.println(companies.getJSONObject(i)); // debugging
				allCompanies.add(new companiesBuilder(companies
						.getJSONObject(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		/*
		 * this loop goes through every company that has been built and adds it
		 * to the custom listview adapter.
		 */

		for (companiesBuilder built : allCompanies) {
			compAdp.add(built);
		}

		companyList.setAdapter(compAdp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
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
		case R.id.search:
			openSearch(); // when searchbutton is clicked, it will start the
							// opensearch method.
			return true;
		case R.id.action_settings:
			// openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/*
	 * Written by: Joakim Bajoul Kakaei Description: Invoked when user presses
	 * search icon. Opens up the searchview in the menu.
	 */
	public void openSearch() {
		/*
		 * snippet taken from
		 * http://developer.android.com/training/search/setup.html with some
		 * changes to it.
		 */
		SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView sv = (SearchView) m.findItem(R.id.search).getActionView();
		sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
	}

	/*
	 * top10 (AsyncTask) Name: Joakim Bajoul Kakaei (881129-0298) Description:
	 * This class handles the connection between the JSONparser and the
	 * mainActivity using a different thread. It's mainly used to help with
	 * memory allocation as well as making sure the main-thread isn't too
	 * overloaded with too many assignments.
	 */
	private class top10 extends AsyncTask<String, String, JSONArray> {
		@Override
		protected JSONArray doInBackground(String... params) {
			JSONparser jparser = new JSONparser();
			companies = jparser.topCompanies();
			System.out.println("background"); // debugging
			return companies;
		}

		@Override
		protected void onPostExecute(JSONArray jarr) {
			System.out.println("onpost");

		}

	}
}
