package au.com.bfbapps.timesheetme.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.adapters.WeeklyViewListAdapter;

public class WeeklyScheduleFragment extends Fragment {

	private ListView mWeeklyListView;
	private ArrayList<String[]> mDays;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_weekly_entry, container, false);
		mWeeklyListView = (ListView)v.findViewById(R.id.weekly_entry_listView);

		((MainActivity)getActivity()).setActionBarTitle("Week in Review");
		createTestData();

		return v;
	}
	private void createTestData(){
		mDays = new ArrayList<String[]> ();
		mDays.add(new String[]{"Monday", "5"});
		mDays.add(new String[]{"Tuesday", "6"});
		mDays.add(new String[]{"Wednesday", "7"});
		mDays.add(new String[]{"Thursday", "5"});
		mDays.add(new String[]{"Friday", "5"});
		mDays.add(new String[]{"Saturday", "9"});
		mDays.add(new String[]{"Sunday", "7"});

		WeeklyViewListAdapter mAdapter = new WeeklyViewListAdapter(getActivity(), mDays);
		mWeeklyListView.setAdapter(mAdapter);
	}
	
	
}
