package nl.swiftdevelopment.workwatch.models;

import java.util.ArrayList;

import nl.swiftdevelopment.workwatch.R;
import nl.swiftdevelopment.workwatch.R.id;
import nl.swiftdevelopment.workwatch.R.layout;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryListViewAdapter extends ArrayAdapter<Category> {
	ArrayList<Category> listOfCategories = null;
	Activity activity;
	private LayoutInflater inflater = null;

	public CategoryListViewAdapter(Context context, int textViewResourceId,
			ArrayList<Category> listOfCategories, Activity activity) {
		super(context, textViewResourceId, listOfCategories);
		this.listOfCategories = listOfCategories;

		this.activity = activity;

		inflater = (LayoutInflater) this.activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListCell cell;

		// ConvertView creates views for each item in the list.
		// If there is no view created yet, convertView is null
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.category_view_cell, null);
			cell = new ListCell();

			// Creating views for the items
			cell.title = (TextView) convertView.findViewById(R.id.category_title);

			convertView.setTag(cell);
		} else {

			// The event handler will always select the right view because
			// getTag points to the current button pressed
			cell = (ListCell) convertView.getTag();
		}

		Category category = this.listOfCategories.get(position);

		// Putting TimeBlock objects in the views
		cell.title.setText(category.getTitle());

		return convertView;
	}

	private class ListCell {

		private TextView title;

	}
}
