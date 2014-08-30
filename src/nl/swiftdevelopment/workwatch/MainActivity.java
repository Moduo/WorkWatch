package nl.swiftdevelopment.workwatch;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public GridView gridView;
	private Activity activity;
	private ArrayList<TimeBlock> listOfBlocks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity = this;
		gridView = (GridView) findViewById(R.id.grid_view);

		listOfBlocks = new ArrayList<TimeBlock>();

		TimeBlockListViewAdapter adapter = new TimeBlockListViewAdapter(this,
				android.R.layout.simple_list_item_1, listOfBlocks, this);

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
				
				TextView timeTV = (TextView) gridView.getChildAt((int) id).findViewById(R.id.block_time);
				String currentTime = timeTV.getText() + "";
				
				TimeBlock timeBlock = listOfBlocks.get(position);
				if (timeBlock.time.isRunning() == true){
					timeBlock.time.stop();
				}else{
					timeBlock.time.resume(currentTime);
				}
				
				
			}
			
		});

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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// @Override
	// protected void onDestroy() {
	// CloseAppDialogFragment dialog = new CloseAppDialogFragment();
	// dialog.show();
	// }
	@Override
	public void onBackPressed() {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_close_app)
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								activity.finish();
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});

		builder.show();

	}

	public void addTimeBlock(String label, View v) {
		listOfBlocks.add(new TimeBlock(new Time(this, listOfBlocks.size(), v),
				label));
	}

}
