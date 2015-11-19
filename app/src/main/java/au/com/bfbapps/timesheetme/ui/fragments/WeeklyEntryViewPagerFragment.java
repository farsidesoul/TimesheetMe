package au.com.bfbapps.timesheetme.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.utils.DateUtil;
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
		View v = inflater.inflate(R.layout.fragment_weekly_entry_view_pager, container, false);

		Bundle fragmentExtras = getArguments();
		Date startOfWeek = DateUtil.convertStringToDate(fragmentExtras.getString("weekStart"));

		mDb = new DatabaseHelper(getActivity().getApplicationContext());

		mWeeklyEntryList = mDb.getEntriesForWeek(startOfWeek);

		// add in database method to grab weekly entries
		mRecyclerView = (RecyclerView)v.findViewById(R.id.weekly_entry_list);
		mWeeklyEntryAdapter = new WeeklyEntryRecyclerViewAdapter(getActivity(), mWeeklyEntryList);
		mRecyclerView.setAdapter(mWeeklyEntryAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		return v;
	}
	
	
}
