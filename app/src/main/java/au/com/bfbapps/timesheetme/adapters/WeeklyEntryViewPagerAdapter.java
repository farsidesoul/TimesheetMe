package au.com.bfbapps.timesheetme.adapters;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Date;

import au.com.bfbapps.timesheetme.ui.fragments.WeeklyEntryViewPagerFragment;
import au.com.bfbapps.timesheetme.utils.DateUtil;

public class WeeklyEntryViewPagerAdapter extends FragmentPagerAdapter {

	public WeeklyEntryViewPagerAdapter(Resources resources, FragmentManager fm) {
		super(fm);
	}


	@Override
	public Fragment getItem(int position) {

		Date pagerDate = new Date();

		Date weekStart = DateUtil.getStartOfWeek(DateUtil.addWeeksToDate(pagerDate, position - 1000));
		Bundle bundle = new Bundle();
		bundle.putString("weekStart", DateUtil.convertDateToString(weekStart));

		WeeklyEntryViewPagerFragment weeklyEntry = new WeeklyEntryViewPagerFragment();
		weeklyEntry.setArguments(bundle);
		return weeklyEntry;
	}

	@Override
	public int getCount() {
		return 2000;
	}

	@Override
	public int getItemPosition(Object object){
		return FragmentStatePagerAdapter.POSITION_NONE;
	}

}
