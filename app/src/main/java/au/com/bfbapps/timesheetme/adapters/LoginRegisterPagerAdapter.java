package au.com.bfbapps.timesheetme.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import au.com.bfbapps.timesheetme.UI.loginregister.LoginFragment;
import au.com.bfbapps.timesheetme.UI.loginregister.RegisterFragment;

public class LoginRegisterPagerAdapter extends FragmentPagerAdapter {
	public LoginRegisterPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		switch (i){
			case 0:
				return new LoginFragment();
			case 1:
				return new RegisterFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position){
			case 0:
				return "Login";
			case 1:
				return "Register";
		}
		return super.getPageTitle(position);
	}
}
