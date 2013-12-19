package com.example.geostocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Written By: Joakim Bajoul Kakaei (881129-0298)
 * 
 * Description: The main objective of this class is to set up the activity that handles the companies detailed view.
 * Gets all the objects that are associated with the layout activity_company_details.
 * After getting the objects they are handled inside the setup method to format text, images and handle the overall functionallity.
 */
public class CompanyDetails extends Activity {
	JSONObject companyObj = new JSONObject();
	JSONArray news = new JSONArray();
	String symbol;
	companiesBuilder company;
	private List<NewsBuilder> allNews = new ArrayList<NewsBuilder>();
	ImageButton img;
	Toast toast;
	TextView toastText;
	Menu m;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_details);
		handleIntent(getIntent());
		img = new ImageButton(this);

		/*
		 * Set up and associate the custom toast with it's layout.
		 */
		LayoutInflater inflater = getLayoutInflater();
		// inflate layout for the custom toast animation.
		toast = new Toast(this);
		View viewLayout = inflater.inflate(R.layout.cust_toast,
				(ViewGroup) findViewById(R.id.cust_toast));
		toastText = (TextView) viewLayout.findViewById(R.id.toastText);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(viewLayout);
		try {
			/*
			 * executes the AsyncTask (getObject). When the task is executed, it
			 * then gets the JSONArray which is bouncing around.
			 */
			companyObj = new getObject().execute("DO IT!").get();
			company = new companiesBuilder(companyObj);
			// companiesBuilder details = new
			// companiesBuilder(companyObj.getJSONObject(1));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		textualSetup();
		if (news != null) {

			this.setTitle(company.getName() + "::" + company.getSymbol());
			final ListView newsList = (ListView) findViewById(R.id.news_list);
			final NewsAdapter newAdp = new NewsAdapter(this,
					R.layout.newslist_layout);

			/*
			 * a loop to create companyBuilder-objects from the JSONArray and
			 * then add those objects to an ArrayList (allCompanies).
			 */
			for (int i = 0; news.length() > i; i++) {
				try {
					allNews.add(new NewsBuilder(news.getJSONObject(i)));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
			/*
			 * this loop goes through every company that has been built and adds
			 * it to the custom listview adapter.
			 */

			for (NewsBuilder built : allNews) {
				newAdp.add(built);
			}
			newsList.setAdapter(newAdp);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {

		symbol = intent.getStringExtra("Symbol");
		// use the query to search your data somehow

	}

	/*
	 * textualSetup: Associates of the detailed information with variables as
	 * well as starts binding them together.
	 */
	private void textualSetup() {
		final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255,
				255, 255));
		final StyleSpan ss = new StyleSpan(android.graphics.Typeface.BOLD);
		SpannableStringBuilder sb;

		TextView price = new TextView(this);
		TextView change = new TextView(this);
		TextView prevClose = new TextView(this);
		TextView timestamp = new TextView(this);
		TextView open = new TextView(this);
		TextView high = new TextView(this);
		TextView low = new TextView(this);
		img = (ImageButton) this.findViewById(R.id.add_fav);
		price = (TextView) this.findViewById(R.id.Price);
		change = (TextView) this.findViewById(R.id.Change);
		prevClose = (TextView) this.findViewById(R.id.PrevClose);
		timestamp = (TextView) this.findViewById(R.id.Time);
		open = (TextView) this.findViewById(R.id.Open);
		high = (TextView) this.findViewById(R.id.High);
		low = (TextView) this.findViewById(R.id.Low);

		// Spannable textToSpan = new SpannableString();
		price.setText(company.getPrice() + "$");
		if (Double.parseDouble(company.getChange()) < 0) {
			change.setText(company.getPercent() + "%");
			prevClose.setText("-" + company.getPrev() + " USD");
			change.setTextColor(Color.parseColor("#E51400"));
			prevClose.setTextColor(Color.parseColor("#E51400"));
		} else if (Double.parseDouble(company.getChange()) > 0) {
			change.setText("+" + company.getPercent() + "%");
			prevClose.setText("+" + company.getPrev() + " USD");
			change.setTextColor(Color.parseColor("#339933"));
			prevClose.setTextColor(Color.parseColor("#339933"));
		} else {
			change.setText(company.getPercent() + "%");
			prevClose.setText(company.getPrev());
		}
		timestamp.setText("Trading: " + company.getTime());
		sb = new SpannableStringBuilder("Open:  " + company.getPrev());
		sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		sb.setSpan(ss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		open.setText(sb);

		sb = new SpannableStringBuilder("High:  " + company.getMax());
		sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		sb.setSpan(ss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		high.setText(sb);

		sb = new SpannableStringBuilder("Low:  " + company.getMin());
		sb.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		sb.setSpan(ss, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		low.setText(sb);

		for (int i = 0; i < FavoriteCompanies.INSTANCE.get().size(); i++) {
			if (FavoriteCompanies.INSTANCE.get().get(i).getSymbol()
					.equals(company.getSymbol())) {
				System.out.println("DONE");
				img.setSelected(true);
			}
		}
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View button) {
				if (button.isSelected()) {
					img.setSelected(false);
					toastText.setText(company.getName() + " "
							+ "removed from favorites");
					removeFav(company);
				} else {
					img.setSelected(true);
					toastText.setText(company.getName() + " "
							+ " added to favorites!");
					addFav(company);
				}
				toast.show();
			}
		});
	}

	public void addFav(companiesBuilder company) {
		System.out.println(company.getSymbol());
		FavoriteCompanies.INSTANCE.set(company);
	}

	public void removeFav(companiesBuilder built) {
		FavoriteCompanies.INSTANCE.remove(built);
	}

	/*
	 * getObject (AsyncTask) Name: Joakim Bajoul Kakaei (881129-0298)
	 * 
	 * Description: This class handles the connection between the JSONparser and
	 * the mainActivity using a different thread. It's mainly used to help with
	 * memory allocation as well as making sure the main-thread isn't too
	 * overloaded with too many assignments.
	 */

	private class getObject extends AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONparser jparser = new JSONparser();
			try {
				companyObj = jparser.details(symbol);
				news = jparser.news(symbol);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return companyObj;
		}

		@Override
		protected void onPostExecute(JSONObject job) {

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

}
