package au.com.bfbapps.timesheetme.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.adapters.DailyEntryRecyclerViewAdapter;
import au.com.bfbapps.timesheetme.models.DailyEntry;

public class DailyEntryFragment extends Fragment implements DailyEntryRecyclerViewAdapter.ClickListener {

	private TextView mDateTextView;
	private RecyclerView mRecyclerView;
	private DailyEntryRecyclerViewAdapter mDailyEntryAdapter;
	private List<DailyEntry> mDailyEntries;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_daily_entry, container, false);
		((MainActivity)getActivity()).setActionBarTitle("");

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
		mDateTextView = (TextView)v.findViewById(R.id.daily_entry_date_field);
		mDateTextView.setText(dateFormat.format(date));

		mDailyEntries = new ArrayList<>();
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
	}
}
