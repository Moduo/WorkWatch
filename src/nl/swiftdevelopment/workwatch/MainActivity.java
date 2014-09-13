package nl.swiftdevelopment.workwatch;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setTitle(getResources().getText(R.string.categories));

		final CategoryListViewAdapter adapter = new CategoryListViewAdapter(
				this, android.R.layout.simple_list_item_1,
				Category.listOfCategories, this);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.create_category) {
			createCategory(item);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, WatchOverviewActivity.class);

		intent.putExtra("categoryId", "" + position);

		startActivity(intent);
	}

	private void createCategory(MenuItem item) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Add a category");
		// alert.setMessage("");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = "" + input.getText();

				// Create a new array list of time blocks for the category and
				// add it to the 2D array listOfCategoryTimeBlocks
				Category.listOfCategoryTimeBlocks
						.add(TimeBlock.listOfTimeBlocks = new ArrayList<TimeBlock>());

				new Category(value, Category.listOfCategoryTimeBlocks.size() -1);
				Log.d("CATEGORIES", "The size of listOfCategories: "
						+ Category.listOfCategories.size() + "\nSize of ListOfCategoryTimeBlocks: " + Category.listOfCategoryTimeBlocks.size());
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}

}
