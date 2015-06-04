package au.com.bfbapps.timesheetme.UI;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.UI.navdrawer.NavigationDrawerFragment;
import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.adapters.DailyEntryRecyclerViewAdapter;
import au.com.bfbapps.timesheetme.adapters.DailyEntryViewPagerAdapter;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;

public class DailyEntryFragment extends Fragment {

	private TextView mDateTextView;

	private FloatingActionButton mAddTimesButton;
	private FloatingActionButton mSubmitTimesButton;
	private FloatingActionMenu mFloatingActionMenu;

	private ViewPager mViewPager;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_daily_entry, container, false);
		((MainActivity)getActivity()).setActionBarTitle("");

		mViewPager = (ViewPager)v.findViewById(R.id.daily_entry_pager);
		mViewPager.setAdapter(new DailyEntryViewPagerAdapter(getResources(), getActivity().getSupportFragmentManager()));
		mViewPager.setCurrentItem(5000, false);
		mViewPager.post(new Runnable() {
			public void run() {
				mViewPager.setCurrentItem(5000, false);
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

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
		mDateTextView = (TextView)v.findViewById(R.id.daily_entry_date_field);
		mDateTextView.setText(dateFormat.format(date));

		// Floating Action Button menu
		mFloatingActionMenu = (FloatingActionMenu)v.findViewById(R.id.fab);
		mAddTimesButton = (FloatingActionButton)v.findViewById(R.id.add_times_menu);
		mSubmitTimesButton = (FloatingActionButton)v.findViewById(R.id.submit_times_menu);

		mAddTimesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Clicked Add item", Toast.LENGTH_SHORT).show();
				mFloatingActionMenu.close(true);

			}
		});

		return v;
	}
	
	

}
