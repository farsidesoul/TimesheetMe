package au.com.bfbapps.timesheetme.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import au.com.bfbapps.timesheetme.UI.loginregister.LoginFragment;

public class LoginRegisterPagerAdapter extends FragmentPagerAdapter {
	public LoginRegisterPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		LoginFragment fragment = new LoginFragment();
		return fragment;
	}

	@Override
	public int getCount() {
		return 2;
	}
}
