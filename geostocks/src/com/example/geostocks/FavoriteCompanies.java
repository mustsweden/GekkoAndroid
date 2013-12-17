package com.example.geostocks;

import java.util.ArrayList;

/*
 * FavoriteCompanies:
 * Written By: Joakim Bajoul Kakaei (8811290298)
 * 
 * Description: Alot like it's sibling checkedCompanies, 
 * this will function as a global variable (singleton design).
 * This will add and remove companies that the user decides to favorite as well as store them
 * for as long as the activity is running.
 * And exactly like checkedCompanies it's methods are very self-explanatory.
 */
public enum FavoriteCompanies {
	INSTANCE;
	ArrayList<companiesBuilder> companies = new ArrayList<companiesBuilder>();

	public ArrayList<companiesBuilder> get() {
		return companies;
	}

	public void set(companiesBuilder built) {
		companies.add(built);
	}

	public void remove(companiesBuilder built) {
		for (int index = 0; index < companies.size(); index++) {
			if (companies.get(index).getName().equals(built.getName())) {
				companies.remove(index);
			}
		}
	}
}
