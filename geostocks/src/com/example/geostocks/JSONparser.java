package com.example.geostocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
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
	 * Takes a search string and looks up the webservice for any JSONArray that
	 */
	public JSONArray search(String search) {
		try {
			URLEncoder.encode(search, "UTF-8");
			System.out.println("SEARCH "
					+ "http://dev.semprog.se/Gekko.svc/Search/" + search);
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
		int cp;
		while ((cp = rd.read()) != -1) {

			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static JSONArray readJson(String search) throws IOException,
			JSONException {
		System.out.println("BIGSEARCH " + search);
		InputStream is = new URL(search.replace(" ", "%20")).openStream();

		try {

			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			System.out.println("JSONTEXT " + jsonText);
			JSONArray json = new JSONArray(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

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

	public JSONObject details(String symbol) throws IOException {
		symbol = "http://dev.semprog.se/Gekko.svc/GetInfo/" + symbol;
		InputStream is = new URL(symbol).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			System.out.println("JSONTEXT " + jsonText);
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
