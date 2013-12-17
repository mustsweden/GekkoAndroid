package com.example.geostocks;

import java.util.ArrayList;

/*
 * Written By: Joakim Bajoul Kakaei
 * Description: An enum that functions as a singleton (design pattern) variable with 
 * getter, setter and remover methods. 
 * This singleton is used to flag companyobjects throughout the 
 * project if they have been added for comparison or not.
 * The methods themselves are self-explanatory..
 */
public enum checkedCompanies {
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
