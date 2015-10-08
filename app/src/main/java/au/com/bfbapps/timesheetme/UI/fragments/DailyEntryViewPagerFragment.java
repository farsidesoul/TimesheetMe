package au.com.bfbapps.timesheetme.UI.fragments;


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
import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.adapters.DailyEntryRecyclerViewAdapter;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyEntryViewPagerFragment extends Fragment implements DailyEntryRecyclerViewAdapter.ClickListener {
	

	private RecyclerView mRecyclerView;
	private DailyEntryRecyclerViewAdapter mDailyEntryAdapter;
	private List<Entry> mDailyEntries;

	private DatabaseHelper mDb;

	public DailyEntryViewPagerFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_daily_entry_view_pager, container, false);

		Bundle fragmentExtras = getArguments();
		Date dateToShow = Dates.ConvertStringToDate(fragmentExtras.getString("date"));

		mDb = new DatabaseHelper(getActivity().getApplicationContext());

		mDailyEntries = mDb.getAllEntriesByDate(dateToShow);
		mRecyclerView = (RecyclerView)v.findViewById(R.id.daily_entry_list);
		mDailyEntryAdapter = new DailyEntryRecyclerViewAdapter(getActivity(), mDailyEntries);
		mDailyEntryAdapter.setClickListener(this);
		mRecyclerView.setAdapter(mDailyEntryAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		return v;
	}

	@Override
	public void itemClicked(View view, int position) {
		//TODO: Create method to go to detailed view of item clicked
		//mDailyEntryAdapter.delete(position);
	}

	
	
}
