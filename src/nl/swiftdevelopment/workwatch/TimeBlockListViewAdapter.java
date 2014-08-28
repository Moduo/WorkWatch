package nl.swiftdevelopment.workwatch;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TimeBlockListViewAdapter extends ArrayAdapter<TimeBlock> {

	ArrayList<TimeBlock> listOfBlocks = null;
	Activity activity;
	private LayoutInflater inflater = null;

	public TimeBlockListViewAdapter(Context context, int textViewResourceId,
			ArrayList<TimeBlock> listOfBlocks, Activity activity) {
		super(context, textViewResourceId, listOfBlocks);
		this.listOfBlocks = listOfBlocks;

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

			convertView = inflater.inflate(
					R.layout.time_block_view_cell, null);
			cell = new ListCell();

			// Creating views for the items
			cell.time = (TextView) convertView
					.findViewById(R.id.block_time);
			cell.title = (TextView) convertView
					.findViewById(R.id.block_title);

			convertView.setTag(cell);
		} else {

			// The event handler will always select the right view because
			// getTag points to the current button pressed
			cell = (ListCell) convertView.getTag();
		}

		TimeBlock timeBlock = this.listOfBlocks.get(position);
		
		// Putting TimeBlock objects in the views
		cell.time.setText("" + timeBlock.getTime());
		cell.title.setText(timeBlock.getTitle());

		return convertView;
	}

	private class ListCell {

		private TextView time, title;

	}
}
