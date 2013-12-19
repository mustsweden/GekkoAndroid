package com.example.geostocks;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

/* companiesBuilder.
 * Made by: Joakim Bajoul Kakaei (881129-0298)
 * 
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
	private boolean isSelected = false;
	private String name;
	private String price;
	private String symbol;
	private String percent;
	private String change;
	private int imageView;
	private int imageButton;
	private String timestamp;
	private String volume;
	private String dayMax;
	private String dayMin;
	private String prevClose;

	public companiesBuilder(JSONObject job) {
		try {
			timestamp = fixDate2(job.getString("Datetime")).toString();
			prevClose = job.getString("PreviousClose");
			dayMin = job.getString("DayMinPrice");
			dayMax = job.getString("DayMaxPrice");
			volume = job.getString("Volume");
			name = job.getString(TAG_NAME);
			name = name.substring(0, Math.min(name.length(), 29)); // creates a
																	// substring
																	// to cut
																	// the
																	// length of
																	// some
																	// companynames.
			price = job.getString(TAG_PRICE);
			symbol = job.getString(TAG_SYMBOL).trim();
			percent = job.getString(TAG_PERCENT);
			change = job.getString(TAG_CHANGE);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String fixDate2(String jsonDate) {
		String timestamp = jsonDate.split("\\(")[1].split("\\+")[0];
		long timestampp = Long.parseLong(timestamp);
		java.util.Date date = new Date(timestampp);
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat dd = new SimpleDateFormat("EEE, d MMM yy", Locale.ENGLISH);
		dd.setCalendar(cal);
		dd.format(date);
		String formattedDate = dd.format(date);
		try {
			date = dd.parse(formattedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
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

	public int getimageView() {
		return imageView;
	}

	public boolean getSelected() {
		return isSelected;
	}

	public void setSelect(boolean flagged) {
		isSelected = flagged;
	}

	public int getimageButton() {
		return imageButton;
	}

	public String getTime() {
		return timestamp;
	}

	public String getPrev() {
		return prevClose;
	}

	public String getVol() {
		return volume;
	}

	public String getMax() {
		return dayMax;
	}

	public String getMin() {
		return dayMin;
	}

}
