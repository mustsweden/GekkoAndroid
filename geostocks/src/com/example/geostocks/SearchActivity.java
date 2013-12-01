package com.example.geostocks;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

public class SearchActivity extends Activity {
	JSONArray companies;
	String query;

	private List<companiesBuilder> allCompanies = new ArrayList<companiesBuilder>();
	companiesAdapter compAdp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		handleIntent(getIntent());
		try {
			/*
			 * executes the AsyncTask (top10). When the task is executed, it
			 * then gets the JSONArray which is bouncing around.
			 */
			companies = new searchList().execute("DO IT!").get();
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

		final ListView companyList = (ListView) findViewById(R.id.listView_search);
		compAdp = new companiesAdapter(this, R.layout.listview_layout);
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
			for (int i = 0; i < MainActivity.checkedCompanies.size(); i++) {
				System.out.println("CHECKED"
						+ MainActivity.checkedCompanies.get(i));
				if (MainActivity.checkedCompanies.get(i).equals(
						built.getSymbol())) {
					built.setSelect(true);
				}
			}
			compAdp.add(built);
		}
		companyList.setAdapter(compAdp);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			super.onBackPressed();
			System.out.println(checkedCompanies.INSTANCE.get().size());
			System.out.println("BACK PRESSED!");
			for (int i = 0; i < checkedCompanies.INSTANCE.get().size(); i++) {
				System.out.println("CHECKED      "
						+ checkedCompanies.INSTANCE.get().get(i).getName());
			}
		}
		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
			System.out.println(query);
			// use the query to search your data somehow
		}
	}

	private class searchList extends AsyncTask<String, String, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... params) {
			JSONparser jparser = new JSONparser();

			companies = jparser.search(query);

			System.out.println("background"); // debugging
			return companies;
		}

		@Override
		protected void onPostExecute(JSONArray jarr) {
			System.out.println("onpost");

		}

	}

}
