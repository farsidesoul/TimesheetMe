package au.com.bfbapps.timesheetme.UI;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.adapters.WeeklyViewListAdapter;

public class WeeklyScheduleFragment extends Fragment {

	private ListView mWeeklyListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_weekly_entry, container, false);
		mWeeklyListView = (ListView)v.findViewById(R.id.weekly_entry_listView);
		createTestData();

		WeeklyViewListAdapter mAdapter = new WeeklyViewListAdapter(getActivity(), mDays);
		mWeeklyListView.setAdapter(mAdapter);

		return v;
	}

	private ArrayList<String[]> mDays;
	private void createTestData(){
		mDays = new ArrayList<String[]> ();
		mDays.add(new String[]{"Monday", "5"});
		mDays.add(new String[]{"Tuesday", "6"});
		mDays.add(new String[]{"Wednesday", "7"});
		mDays.add(new String[]{"Thursday", "5"});
		mDays.add(new String[]{"Friday", "5"});
		mDays.add(new String[]{"Saturday", "9"});
		mDays.add(new String[]{"Sunday", "7"});
	}
	
	
}
