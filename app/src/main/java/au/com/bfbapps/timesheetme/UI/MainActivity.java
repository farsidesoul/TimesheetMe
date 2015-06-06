package au.com.bfbapps.timesheetme.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.UI.navdrawer.NavigationDrawerFragment;
import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;
import au.com.bfbapps.timesheetme.models.Job;
import au.com.bfbapps.timesheetme.models.Task;
import au.com.bfbapps.timesheetme.models.User;


public class MainActivity extends ActionBarActivity {

	private Toolbar mToolbar;
	private User mUser;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_appbar);


		Bundle extras = getIntent().getExtras();
		if(extras != null){
			mUser = extras.getParcelable("user");
		}

		CreateInitialJobAndTaskDate();

		mToolbar = (Toolbar)findViewById(R.id.app_bar);
		setSupportActionBar(mToolbar);
		if(getSupportActionBar() != null){
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setTitle("");
		}


		NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
		drawerFragment.setUp(R.id.fragment_navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout),
				mToolbar);

		Bundle fragmentExtras = new Bundle();
		fragmentExtras.putParcelable("user", mUser);

		DailyEntryFragment dailyEntryFragment = new DailyEntryFragment();
		dailyEntryFragment.setArguments(fragmentExtras);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_content_pager, dailyEntryFragment)
				.commit();



	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	public void setActionBarTitle(String title){
		if(getSupportActionBar() != null)
			getSupportActionBar().setTitle(title);
	}

	private void CreateInitialJobAndTaskDate(){
		DatabaseHelper db = new DatabaseHelper(getApplicationContext());

		db.createJob(new Job("Big Ship"));
		db.createJob(new Job("Show and Tell"));
		db.createJob(new Job("Website"));
		db.createJob(new Job("Business Meeting"));

		db.createTask(new Task("Ship Design"));
		db.createTask(new Task("School stuff"));
		db.createTask(new Task("Design and Development"));
		db.createTask(new Task("Boring old people"));

		db.createEntry(new Entry(Dates.ConvertStringToDate("04/06/2015"), "08:00 AM", "11:30 AM", 0, 3.5, 1, 1));
		db.createEntry(new Entry(Dates.ConvertStringToDate("04/06/2015"), "12:30 PM", "01:00 PM", 0, 0.5, 2, 2));
		db.createEntry(new Entry(Dates.ConvertStringToDate("04/06/2015"), "01:30 PM", "03:00 PM", 0, 1.5, 1, 3));
		db.createEntry(new Entry(Dates.ConvertStringToDate("04/06/2015"), "03:30 PM", "05:00 PM", 0, 1.5, 1, 4));

		db.createEntry(new Entry(Dates.ConvertStringToDate("03/06/2015"), "08:00 AM", "11:30 AM", 0, 2, 1, 1));
		db.createEntry(new Entry(Dates.ConvertStringToDate("03/06/2015"), "12:30 PM", "01:00 PM", 0, 1, 2, 2));
		db.createEntry(new Entry(Dates.ConvertStringToDate("03/06/2015"), "01:30 PM", "03:00 PM", 0, 3, 3, 3));
		db.createEntry(new Entry(Dates.ConvertStringToDate("03/06/2015"), "03:30 PM", "05:00 PM", 0, 2, 4, 4));

		db.createEntry(new Entry(Dates.ConvertStringToDate("02/06/2015"), "08:00 AM", "11:30 AM", 0, 6, 2, 4));
		db.createEntry(new Entry(Dates.ConvertStringToDate("02/06/2015"), "12:30 PM", "01:00 PM", 0, 2, 3, 1));
		db.createEntry(new Entry(Dates.ConvertStringToDate("02/06/2015"), "01:30 PM", "03:00 PM", 0, 1, 4, 2));
		db.createEntry(new Entry(Dates.ConvertStringToDate("02/06/2015"), "03:30 PM", "05:00 PM", 0, 1.5, 4, 2));

		db.createEntry(new Entry(Dates.ConvertStringToDate("01/06/2015"), "08:00 AM", "11:30 AM", 0, 1, 1, 1));
		db.createEntry(new Entry(Dates.ConvertStringToDate("01/06/2015"), "12:30 PM", "01:00 PM", 0, 4, 2, 2));
		db.createEntry(new Entry(Dates.ConvertStringToDate("01/06/2015"), "01:30 PM", "03:00 PM", 0, 2.5, 3, 3));
		db.createEntry(new Entry(Dates.ConvertStringToDate("01/06/2015"), "03:30 PM", "05:00 PM", 0, 1, 4, 4));

		db.createEntry(new Entry(Dates.ConvertStringToDate("29/05/2015"), "08:00 AM", "11:30 AM", 0, 3.5, 1, 1));
		db.createEntry(new Entry(Dates.ConvertStringToDate("29/05/2015"), "12:30 PM", "01:00 PM", 0, 0.5, 2, 2));
		db.createEntry(new Entry(Dates.ConvertStringToDate("29/05/2015"), "01:30 PM", "03:00 PM", 0, 1.5, 1, 3));
		db.createEntry(new Entry(Dates.ConvertStringToDate("29/05/2015"), "03:30 PM", "05:00 PM", 0, 1.5, 1, 3));

		db.createEntry(new Entry(Dates.ConvertStringToDate("26/05/2015"), "08:00 AM", "11:30 AM", 0, 2, 1, 2));
		db.createEntry(new Entry(Dates.ConvertStringToDate("26/05/2015"), "12:30 PM", "01:00 PM", 0, 1, 2, 3));
		db.createEntry(new Entry(Dates.ConvertStringToDate("26/05/2015"), "01:30 PM", "03:00 PM", 0, 3, 2, 4));
		db.createEntry(new Entry(Dates.ConvertStringToDate("26/05/2015"), "03:30 PM", "05:00 PM", 0, 2, 3, 1));
	}
}
