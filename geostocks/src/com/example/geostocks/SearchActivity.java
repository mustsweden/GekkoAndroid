package com.example.geostocks;

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
import android.widget.ListView;

public class SearchActivity extends Activity {
	JSONArray companies;
	String query;
	private List<companiesBuilder> allCompanies = new ArrayList<companiesBuilder>();

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
		final companiesAdapter compAdp = new companiesAdapter(this,
				R.layout.listview_layout);
		companyList.setAdapter(compAdp);
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

		for (final companiesBuilder built : allCompanies) {
			compAdp.add(built);
		}
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
