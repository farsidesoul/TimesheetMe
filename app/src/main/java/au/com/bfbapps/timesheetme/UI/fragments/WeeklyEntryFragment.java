package au.com.bfbapps.timesheetme.UI.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.UI.activities.BaseModeActivity;
import au.com.bfbapps.timesheetme.adapters.WeeklyEntryViewPagerAdapter;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Job;
import au.com.bfbapps.timesheetme.models.Task;

public class WeeklyEntryFragment extends Fragment {

	private ViewPager mViewPager;

	private List<Job> mJobList;
	private List<Task> mTaskList;

	private DatabaseHelper mDb;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_weekly_entry, container, false);
		((BaseModeActivity)getActivity()).setActionBarTitle("Week in Review");

		mDb = new DatabaseHelper(getActivity().getApplicationContext());
		mJobList = mDb.getAllJobs();
		mTaskList = mDb.getAllTasks();

		mViewPager = (ViewPager)v.findViewById(R.id.weekly_entry_pager);
		mViewPager.setAdapter(new WeeklyEntryViewPagerAdapter(getResources(),
				getChildFragmentManager()));
		mViewPager.setCurrentItem(1000, false);

		mViewPager.post(new Runnable() {
			public void run() {
				mViewPager.setCurrentItem(1000, false);
			}
		});

		mViewPager.getAdapter().notifyDataSetChanged();
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		return v;
	}
}
