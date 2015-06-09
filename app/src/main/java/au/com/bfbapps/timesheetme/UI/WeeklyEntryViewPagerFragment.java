package au.com.bfbapps.timesheetme.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.adapters.WeeklyEntryRecyclerViewAdapter;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.WeeklyEntry;

public class WeeklyEntryViewPagerFragment extends Fragment {

	private List<WeeklyEntry> mWeeklyEntryList;
	private RecyclerView mRecyclerView;
	private WeeklyEntryRecyclerViewAdapter mWeeklyEntryAdapter;

	private DatabaseHelper mDb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_weekly_entry, container, false);

		((MainActivity)getActivity()).setActionBarTitle("Week in Review");

		mWeeklyEntryAdapter = new WeeklyEntryRecyclerViewAdapter(getActivity(), mWeeklyEntryList);

		return v;
	}
	
	
}