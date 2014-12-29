package nl.swiftdevelopment.workwatch;

import java.util.ArrayList;

import nl.swiftdevelopment.workwatch.models.Category;
import nl.swiftdevelopment.workwatch.models.Time;
import nl.swiftdevelopment.workwatch.models.TimeBlock;
import nl.swiftdevelopment.workwatch.models.TimeBlockListViewAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class WatchOverviewActivity extends Activity {

	public GridView gridView;
	private Activity activity;
	private Category category;
	private int categoryId;
	private TimeBlockListViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_watch_overview);
		activity = this;

		Intent intent = getIntent();

		category = Category.getCategory(Integer.parseInt(intent
				.getStringExtra("categoryId")));
		categoryId = Integer.parseInt(intent.getStringExtra("categoryId"));

		Toast.makeText(
				this,
				"Category with id: " + intent.getStringExtra("categoryId")
						+ " is here.", Toast.LENGTH_SHORT).show();
		gridView = (GridView) findViewById(R.id.grid_view);

		// We only want 1 list of Time Block's, so it's not overwritten when
		// this activity is started again.
		// if (TimeBlock.listOfTimeBlocks == null) {
		// TimeBlock.listOfTimeBlocks = new ArrayList<TimeBlock>();
		// }

		if(adapter == null){
		adapter = new TimeBlockListViewAdapter(
				this, android.R.layout.simple_list_item_1,
				Category.listOfCategoryTimeBlocks.get(categoryId), this);
		}
		gridView.setAdapter(adapter);

		/****************************** Add watch ***************************/
		Button addBtn = (Button) findViewById(R.id.addBtn);

		addBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText label = (EditText) findViewById(R.id.timeLabel);
				addTimeBlock(label.getText().toString(), v);
				label.setText("");
			}
		});

		/****************************** Stop watch ***************************/
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				TimeBlock timeBlock = Category.listOfCategoryTimeBlocks.get(
						categoryId).get(position);
				if (timeBlock.getTime().isRunning() == true) {
					timeBlock.getTime().stop();
				} else {
					timeBlock.getTime().resume();
				}

			}

		});
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				ArrayList<TimeBlock> category = Category.listOfCategoryTimeBlocks
						.get(categoryId);
				category.get(position).getTime().stop();
				category.remove(position);

				category.get(position).resetIdsOfTimers();

				adapter.notifyDataSetChanged();
				Log.d("REMOVE", "Item with position " + position
						+ " has been removed.");
				return true;
			}

		});

		/******************** Action bar ***********************/
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(category.getTitle());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Old onBackPressed with warning message
	// @Override
	// public void onBackPressed() {
	// // Use the Builder class for convenient dialog construction
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// builder.setMessage(R.string.dialog_close_app)
	// .setPositiveButton(R.string.yes,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// activity.finish();
	// }
	// })
	// .setNegativeButton(R.string.cancel,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// // User cancelled the dialog
	// }
	// });
	//
	// builder.show();
	//
	// }
	@Override
	public void onBackPressed() {
		// NavUtils.navigateUpFromSameTask(this);

		// This was to go to MainActivity without finishing this one to keep the
		// TimeBlocks running.
		Intent intent = new Intent(activity, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}

	public void addTimeBlock(String label, View v) {
		new TimeBlock(new Time(this, Category.listOfCategoryTimeBlocks.get(
				categoryId).size(), v), label, category);
		Log.d("SIZE",
				"List size: "
						+ Category.listOfCategoryTimeBlocks.get(categoryId)
								.size());
		if (Category.listOfCategoryTimeBlocks.get(categoryId).size() > 1) {
			// Get the previously made time block.
			TimeBlock timeBlock = Category.listOfCategoryTimeBlocks.get(
					categoryId)
					.get(Category.listOfCategoryTimeBlocks.get(categoryId)
							.size() - 2);
			if (timeBlock.getTime().isRunning() == true) {
				timeBlock.getTime().stop();
			}
		}

		hideSoftKeyboard(this);
	}

}
