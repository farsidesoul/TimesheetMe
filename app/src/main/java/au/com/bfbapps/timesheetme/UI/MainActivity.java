package au.com.bfbapps.timesheetme.UI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.UI.navdrawer.NavigationDrawerFragment;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
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
		db.createJob(new Job("Job 1"));
		db.createJob(new Job("JOb 2"));

		db.createTask(new Task("Task 1"));
		db.createTask(new Task("Task 2"));
	}
}
