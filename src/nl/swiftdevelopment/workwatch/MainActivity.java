package nl.swiftdevelopment.workwatch;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class MainActivity extends Activity {

	private ArrayList<TimeBlock> listOfBlocks;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GridView gridView = (GridView) findViewById(R.id.grid_view);
		
		
		listOfBlocks = new ArrayList<TimeBlock>();
		
		TimeBlockListViewAdapter adapter = new TimeBlockListViewAdapter(this,
				android.R.layout.simple_list_item_1, listOfBlocks, this);

		gridView.setAdapter(adapter);
		
		Button addBtn = (Button) findViewById(R.id.addBtn);

		addBtn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	EditText label = (EditText) findViewById(R.id.timeLabel);
		        addTimeBlock(label.getText().toString());
		        label.setText("");
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
	
	
	public void addTimeBlock(String label){
		listOfBlocks.add(new TimeBlock(new Time(this), label));
	}

	
	

}
