package au.com.bfbapps.timesheetme.UI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.UI.navdrawer.NavigationDrawerFragment;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
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

	public void setActionBarTitle(String title){
		if(getSupportActionBar() != null)
			getSupportActionBar().setTitle(title);
	}

	private void CreateInitialJobAndTaskDate(){
		DatabaseHelper db = new DatabaseHelper(getApplicationContext());

	}
}
