package com.example.geostocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Written by: Joakim Bajoul Kakaei
 * Description: Class is designed to get and parse json-objects into an array-list.
 */

public class JSONparser {
	/*
	 * Create static instancevariables and instansiate them with null.
	 */
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	/*
	 * Takes a search string and looks up the webservice for a JSONArray with
	 * only objects that meet the search criteria.
	 */
	public JSONArray search(String search) {
		try {
			URLEncoder.encode(search, "UTF-8"); // Encodes the URL to a correct
												// string.
			return readJson("http://dev.semprog.se/Gekko.svc/Search/" + search);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int index;
		while ((index = rd.read()) != -1) {

			sb.append((char) index);
		}
		return sb.toString();
	}
	/* 
	Returns a JSONArray of the search query by contacting the webservice with the query's symbol. 
	Uses JSONparser.readAll
	*/
	private static JSONArray readJson(String search) throws IOException,
			JSONException {
		InputStream is = new URL(search.replace(" ", "%20")).openStream();
		try {

			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
	/* Returns a JSONArray that contains the top 10 increasing stocks */
	public JSONArray topCompanies() {
		try {
			return readJson("http://dev.semprog.se/Gekko.svc/GetTop/10");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

/*
Returns an array of JSONojbects containing the most recent news of the queried company
*/
	public JSONArray news(String symbol) throws IOException {
		symbol.replace(" ", "%20");
		symbol = "http://dev.semprog.se/Gekko.svc/News/" + symbol + "/" + 10;
		URLEncoder.encode(symbol.trim(), "UTF-8");
		System.out.println("OUTPUT " + symbol);
		InputStream is = new URL(symbol).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			is.close();
		}
	}

/*
Return the JSONObject of the search query (the clicked object in the listview)
*/

	public JSONObject details(String symbol) throws IOException {
		System.out.println("OUT " + symbol +"x");
		symbol = "http://dev.semprog.se/Gekko.svc/GetInfo/" + symbol;
		InputStream is = new URL(symbol).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			is.close();
		}
	}

}
