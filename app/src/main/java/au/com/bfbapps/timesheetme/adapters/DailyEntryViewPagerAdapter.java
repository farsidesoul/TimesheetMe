package au.com.bfbapps.timesheetme.adapters;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import au.com.bfbapps.timesheetme.UI.DailyEntryViewPagerFragment;
import au.com.bfbapps.timesheetme.Util.Dates;

public class DailyEntryViewPagerAdapter extends FragmentPagerAdapter {

	public DailyEntryViewPagerAdapter(Resources resources, FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Date pagerDate = new Date();

		String dayToShow = Dates.ConvertDateToString(
				Dates.AddDaysToDate(pagerDate, position - 5000));

		Bundle bundle = new Bundle();
		bundle.putString("date", dayToShow);

		DailyEntryViewPagerFragment dailyEntryViewPagerFragment = new DailyEntryViewPagerFragment();
		dailyEntryViewPagerFragment.setArguments(bundle);

		return dailyEntryViewPagerFragment;
	}

	@Override
	 public int getItemPosition(Object object){
		return FragmentStatePagerAdapter.POSITION_NONE;
	}

	@Override
	public int getCount() {
		return 10000;
	}
}
