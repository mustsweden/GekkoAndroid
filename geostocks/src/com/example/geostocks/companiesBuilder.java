package com.example.geostocks;

import org.json.JSONException;
import org.json.JSONObject;

/* companiesBuilder.
 * Made by: Joakim Bajoul Kakaei (881129-0298)
 * Description: This class is created for a couple of reasons, 
 * the main one to create a company-object from a JSONObject, 
 * parsing "only" the information necessary for the lisviews.
 * The second reason being to make it easier
 * for the threads in the classes to allocate less memory.
 * It also helps with the formatting of the classes that need companyobjects for the listview.
 */
public class companiesBuilder {

	/*
	 * Static tags for easier, and safer coding when parsing objects.
	 */
	private static final String TAG_PERCENT = "ChangePercent";
	private static final String TAG_CHANGE = "ChangeValue";
	private static final String TAG_PRICE = "CurrentPrice";
	private static final String TAG_NAME = "Name";
	private static final String TAG_SYMBOL = "Symbol";

	/*
	 * String variables to save each object's values in.
	 */
	private String name;
	private String price;
	private String symbol;
	private String percent;
	private String change;
	private String title;
	private String subtitle;
	private int image;

	public companiesBuilder(JSONObject job) {
		System.out.println("CompaniesBuilder");
		try {
			name = job.getString(TAG_NAME);
			name = name.substring(0, Math.min(name.length(), 29)); // creates a
																	// substring
																	// to cut
																	// the
																	// length of
																	// some
																	// companynames.
			price = job.getString(TAG_PRICE);
			symbol = job.getString(TAG_SYMBOL);
			percent = job.getString(TAG_PERCENT);
			change = job.getString(TAG_CHANGE);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * A bunch of get-methods to make other classes able to get the objects
	 * created in this class.
	 */
	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getPercent() {
		return percent;
	}

	public String getChange() {
		return change;
	}

	public int getImage() {
		return image;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}
}
