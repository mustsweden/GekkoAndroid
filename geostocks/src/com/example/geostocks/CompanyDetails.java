package com.example.geostocks;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
	String symbol;
	companiesBuilder company;
	ImageButton img;
	Toast toast;
	TextView toastText;

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
		this.setTitle(company.getName() + "::" + company.getSymbol());
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
		price.setText(company.getPrice());
		change.setText(company.getPercent());
		prevClose.setText(company.getPrev());
		timestamp.setText(company.getTime());
		open.setText("Open" + "\t" + company.getPrev());
		high.setText("High" + "\t" + company.getMax());
		low.setText("Low" + "\t" + company.getMin());
		img.setSelected(false);
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

	public void addFav(companiesBuilder row) {
		System.out.println(row.getSymbol());
		FavoriteCompanies.INSTANCE.set(row);
	}

	public void removeFav(companiesBuilder built) {
		FavoriteCompanies.INSTANCE.remove(built);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.company_details, menu);
		return true;
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

}
