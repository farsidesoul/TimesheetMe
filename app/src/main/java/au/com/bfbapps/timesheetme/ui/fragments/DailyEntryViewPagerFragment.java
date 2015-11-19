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
import au.com.bfbapps.timesheetme.adapters.DailyEntryRecyclerViewAdapter;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyEntryViewPagerFragment extends Fragment implements DailyEntryRecyclerViewAdapter.ClickListener {
	

	private RecyclerView recyclerView;
	private DailyEntryRecyclerViewAdapter dailyEntryAdapter;
	private List<Entry> dailyEntries;

	private DatabaseHelper db;

	public DailyEntryViewPagerFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_daily_entry_view_pager, container, false);

		Bundle fragmentExtras = getArguments();
		Date dateToShow = DateUtil.convertStringToDate(fragmentExtras.getString("date"));

		db = new DatabaseHelper(getActivity().getApplicationContext());

		dailyEntries = db.getAllEntriesByDate(dateToShow);
		recyclerView = (RecyclerView)v.findViewById(R.id.daily_entry_list);
		dailyEntryAdapter = new DailyEntryRecyclerViewAdapter(getActivity(), dailyEntries);
		dailyEntryAdapter.setClickListener(this);
		recyclerView.setAdapter(dailyEntryAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		return v;
	}

	@Override
	public void itemClicked(View view, int position) {
		//TODO: Create method to go to detailed view of item clicked
		//dailyEntryAdapter.delete(position);
	}

	
	
}
