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
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * SearchActivity:
 * 
 * Written By: Joakim Bajoul Kakaei (881129-0298)
 * 
 * Description: Handles the searchesqueries that MainActivity sends to it by using AsyncTask.
 * It also sets up a listview with correct items by recycling the adapter class.
 * This activiy also uses the gesturedetector to handle flings in the same fashion as main.
 */
public class SearchActivity extends Activity implements OnGestureListener {
	JSONArray companies;
	String query;
	GestureDetector detector;
	Menu m;
	/*
	 * Handle the threshold.
	 */
	private static final int SWIPE_THRESHOLD = 100;
	private static final int SWIPE_VELOCITY_THRESHOLD = 100;

	private List<companiesBuilder> allCompanies = new ArrayList<companiesBuilder>();
	companiesAdapter compAdp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		handleIntent(getIntent());
		detector = new GestureDetector(this, this);
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
		/*
		 * a loop to create companyBuilder-objects from the JSONArray and then
		 * add those objects to an ArrayList (allCompanies).
		 */try {
			for (int i = 0; companies.length() > i; i++) {
				try {
					allCompanies.add(new companiesBuilder(companies
							.getJSONObject(i)));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		} catch (NullPointerException e) {
			LayoutInflater inflater = getLayoutInflater();
			Toast toast = new Toast(this);
			View viewLayout = inflater.inflate(R.layout.cust_toast,
					(ViewGroup) findViewById(R.id.cust_toast));
			TextView toastText = (TextView) viewLayout
					.findViewById(R.id.toastText);
			toastText.setText("The search " + "'" + query + "'"
					+ " did not find any results!");
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(viewLayout);
			toast.show();
			this.finish();
		}
		/*
		 * this loop goes through every company that has been built and adds it
		 * to the custom listview adapter.
		 */

		for (companiesBuilder built : allCompanies) {
			for (int i = 0; i < MainActivity.checkedCompanies.size(); i++) {
				if (MainActivity.checkedCompanies.get(i).equals(
						built.getSymbol())) {
					built.setSelect(true);
				}
			}
			compAdp.add(built);
		}
		companyList.setAdapter(compAdp);
		companyList.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent e) {
				detector.onTouchEvent(e);
				return false;
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
			// use the query to search your data somehow
		}
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

	/*
	 * searchList: Written By: Joakim Bajoul Kakaei (881129-0298)
	 * 
	 * Description: Opens a parallel thread to handle calls to the JSONparser.
	 * This thread is used to search for items that meet the criteria of the
	 * handled intent sent from main by searching for keywords.
	 */
	private class searchList extends AsyncTask<String, String, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... params) {
			JSONparser jparser = new JSONparser();

			companies = jparser.search(query);
			return companies;
		}

		@Override
		protected void onPostExecute(JSONArray jarr) {
			System.out.println("onpost");

		}

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		boolean result = false;
		try {
			float diffY = e2.getY() - e1.getY();
			float diffX = e2.getX() - e1.getX();
			if (Math.abs(diffX) > Math.abs(diffY)) {
				if (Math.abs(diffX) > SWIPE_THRESHOLD
						&& Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
					if (diffX > 0) {
						System.out.println("RIGHT");
					} else {
						System.out.println("LEFT");
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		super.dispatchTouchEvent(ev);
		return detector.onTouchEvent(ev);
	}	

}
