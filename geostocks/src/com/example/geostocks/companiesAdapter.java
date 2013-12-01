package com.example.geostocks;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/* companiesAdapter.
 * Made by: Joakim Bajoul Kakaei (881129-0298)
 * Description: Since the listview we have decided to use has multiple objects on each row, 
 * the listview's adapter needs to be customized to meet those requirements. 
 * This class may be revised later on (to add or remove objects from the rowItemObjects).
 */
public class companiesAdapter extends ArrayAdapter<companiesBuilder> implements
		OnClickListener {
	private final int companiesBuilderResource;
	private ArrayList<companiesBuilder> companies;
	private Context Viewcontext;

	public companiesAdapter(final Context context,
			final int companiesBuilderResource) {
		super(context, 0);
		this.companiesBuilderResource = companiesBuilderResource;
		companies = new ArrayList<companiesBuilder>();
		this.Viewcontext = context;
	}

	public void addChecked(companiesBuilder row) {
		System.out.println(row.getSymbol());
		checkedCompanies.INSTANCE.set(row);
	}

	public void removeChecked(companiesBuilder built) {
		checkedCompanies.INSTANCE.remove(built);
	}

	public void add(companiesBuilder row) {
		companies.add(row);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return companies.size();
	}

	@Override
	public companiesBuilder getItem(int i) {
		return companies.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {

		// We need to get the best view (re-used if possible) and then
		// retrieve its corresponding rowItem, to optimize lookup efficiency.
		final View view = getourView(convertView);
		final rowItem rowItem = getrowItem(view);
		// final companiesBuilder company = getItem(position);
		final companiesBuilder row = companies.get(position);
		// Setting up both the titleobject's with their corresponding variables
		// (from the row-object).
		rowItem.titleView_left.setText(row.getName());
		rowItem.titleView_right.setText(row.getPrice());

		// Setting up the subtitle's items will be a little bit tougher, since
		// it requires
		// some manipulating of the xml-data.
		rowItem.subTitleView_left.setText(row.getSymbol());

		/*
		 * If-clauses to change the right subtitle's color palette to make it
		 * easier for the user to distinguish increase and decrease.
		 */
		if (Double.parseDouble(row.getChange()) < 0) {
			rowItem.subTitleView_right
					.setTextColor(Color.parseColor("#E51400"));
			rowItem.subTitleView_right.setText(row.getPercent() + "%" + " "
					+ "( " + row.getChange() + ")");
		} else if (Double.parseDouble(row.getChange()) > 0) {
			rowItem.subTitleView_right
					.setTextColor(Color.parseColor("#339933"));
			rowItem.subTitleView_right.setText(row.getPercent() + "%" + " "
					+ "( +" + row.getChange() + ")");
		} else {
			rowItem.subTitleView_right.setText(row.getPercent() + "%" + " "
					+ "(" + row.getChange() + ")");
		}

		/*
		 * Setting up the image variable in correct state if said variable has
		 * been manipulated. This is done using singleton variables.
		 */
		rowItem.imageButton.setSelected(false); // attribute has to be set to
												// false manually,
												// or first item in listview
												// will always be
												// checked.
		for (int i = 0; checkedCompanies.INSTANCE.get().size() > i; i++) {
			if (checkedCompanies.INSTANCE.get().get(i).getName()
					.equals(row.getName())) {
				// System.out.println(checkedCompanies.INSTANCE.get().get(i)
				// .getName());
				rowItem.imageButton.setSelected(true);
			}

		}

		view.setOnClickListener(new OnClickListener() {
			/*
			 * Add an onClickListener that is associated with view (view being
			 * every row).
			 */
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Viewcontext, CompanyDetails.class);
				intent.putExtra("Symbol", row.getSymbol());
				Viewcontext.startActivity(intent);
				new AlertDialog.Builder(getContext()).setTitle(row.getName())
						.show();
			}

		});
		rowItem.imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View button) {
				if (button.isSelected()) {
					button.setSelected(false);
					checkedCompanies.INSTANCE.remove(row);
				} else {
					button.setSelected(true);
					checkedCompanies.INSTANCE.set(row);

				}
			}
		});
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
			rowItem.imageButton = (ImageButton) ourView
					.findViewById(R.id.add_button);
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
		public ImageButton imageButton;

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}
