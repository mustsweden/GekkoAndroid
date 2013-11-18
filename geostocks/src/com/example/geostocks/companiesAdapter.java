package com.example.geostocks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/* companiesAdapter.
 * Made by: Joakim Bajoul Kakaei (881129-0298)
 * Description: Since the listview we have decided to use has multiple objects on each row, 
 * the listview's adapter needs to be customized to meet those requirements. 
 * This class may be revised later on (to add or remove objects from the rowItemObjects).
 */
public class companiesAdapter extends ArrayAdapter<companiesBuilder> {

	private final int companiesBuilderResource;

	public companiesAdapter(final Context context,
			final int companiesBuilderResource) {
		super(context, 0);
		this.companiesBuilderResource = companiesBuilderResource;
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {

		// We need to get the best view (re-used if possible) and then
		// retrieve its corresponding rowItem, to optimize lookup efficiency.
		final View view = getourView(convertView);
		final rowItem rowItem = getrowItem(view);
		final companiesBuilder company = getItem(position);

		// Setting up both the titleobject's with their corresponding variables
		// (from the company-object).
		rowItem.titleView_left.setText(company.getName());
		rowItem.titleView_right.setText(company.getPrice());

		// Setting up the subtitle's items will be a little bit tougher, since
		// it requires
		// some manipulating of the xml-data.
		rowItem.subTitleView_left.setText(company.getSymbol());

		/*
		 * If-clauses to change the right subtitle's color palette to make it
		 * easier for the user to distinguish increase and decrease.
		 */
		if (Double.parseDouble(company.getChange()) < 0) {
			rowItem.subTitleView_right
					.setTextColor(Color.parseColor("#E51400"));
			rowItem.subTitleView_right.setText(company.getPercent() + "%" + " "
					+ "( -" + company.getChange() + ")");
		} else if (Double.parseDouble(company.getChange()) > 0) {
			rowItem.subTitleView_right
					.setTextColor(Color.parseColor("#339933"));
			rowItem.subTitleView_right.setText(company.getPercent() + "%" + " "
					+ "( +" + company.getChange() + ")");
		} else {
			rowItem.subTitleView_right.setText(company.getPercent() + "%" + " "
					+ "(" + company.getChange() + ")");
		}
		// Setting image view is simple enough...
		rowItem.imageView.setImageResource(company.getImage());

		return view;
	}

	private View getourView(final View convertView) {
		// The ourView will be recycling / reusing the convertview if
		// possible.
		// Guess why? Exactly. CUZ WE LOOOOOOOOOOVE HAVING EFFICIENT CODE! <3
		View ourView = null;

		if (null == convertView) {
			final Context context = getContext();
			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			ourView = inflater.inflate(companiesBuilderResource, null);
		} else {
			ourView = convertView;
		}

		return ourView;
	}

	private rowItem getrowItem(final View ourView) {
		// Recycling and reusing tags and objects is the name of the game!
		final Object tag = ourView.getTag();
		rowItem rowItem = null;

		/*
		 * Sets up the listview's rowitems with their corresponding listview
		 * items (textviews and images).
		 */
		if (null == tag || !(tag instanceof rowItem)) {
			rowItem = new rowItem();
			rowItem.titleView_right = (TextView) ourView
					.findViewById(R.id.title_right);
			rowItem.titleView_left = (TextView) ourView
					.findViewById(R.id.title_left);
			rowItem.subTitleView_left = (TextView) ourView
					.findViewById(R.id.subtitle_left);
			rowItem.subTitleView_right = (TextView) ourView
					.findViewById(R.id.subtitle_right);
			rowItem.imageView = (ImageView) ourView.findViewById(R.id.icon);

			ourView.setTag(rowItem);

		} else {
			rowItem = (rowItem) tag;
		}

		return rowItem;
	}

	/*
	 * since views are recycled, these references will always be there to be
	 * reused. Again, it's all about optimization!
	 */
	private static class rowItem {
		public TextView titleView_left;
		public TextView titleView_right;
		public TextView subTitleView_left;
		public TextView subTitleView_right;
		public ImageView imageView;
	}
}
