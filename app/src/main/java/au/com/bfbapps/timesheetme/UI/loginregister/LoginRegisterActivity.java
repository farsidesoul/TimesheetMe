package au.com.bfbapps.timesheetme.UI.loginregister;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.Util.SlidingTabLayout;
import au.com.bfbapps.timesheetme.adapters.LoginRegisterPagerAdapter;

public class LoginRegisterActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private SlidingTabLayout mSlidingTabLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_register);

		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(new LoginRegisterPagerAdapter(getSupportFragmentManager()));
		mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.tabs);
		mSlidingTabLayout.setDistributeEvenly(true);
		mSlidingTabLayout.setViewPager(mViewPager);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_login_register, menu);
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
}
