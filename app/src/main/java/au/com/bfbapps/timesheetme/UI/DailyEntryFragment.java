package au.com.bfbapps.timesheetme.UI;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;

public class DailyEntryFragment extends Fragment implements DailyEntryRecyclerViewAdapter.ClickListener {

	private TextView mDateTextView;
	private RecyclerView mRecyclerView;
	private DailyEntryRecyclerViewAdapter mDailyEntryAdapter;
	private List<Entry> mDailyEntries;

	private FloatingActionButton mAddTimesButton;
	private FloatingActionButton mSubmitTimesButton;
	private FloatingActionMenu mFloatingActionMenu;

	private DatabaseHelper mDb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_daily_entry, container, false);
		((MainActivity)getActivity()).setActionBarTitle("");

		mDb = new DatabaseHelper(getActivity().getApplicationContext());

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

		CreateTestData();

		mDailyEntries = mDb.getAllEntriesByDate(date);
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

	private void CreateTestData(){
		mDb.createEntry(new Entry(Dates.ConvertStringToDate("04/06/2015"), "08:00 AM", "11:30 AM", 0, 3.5, 1, 1));
		mDb.createEntry(new Entry(Dates.ConvertStringToDate("04/06/2015"), "12:30 PM", "01:00 PM", 0, 0.5, 2, 2));
		mDb.createEntry(new Entry(Dates.ConvertStringToDate("04/06/2015"), "01:30 PM", "03:00 PM", 0, 1.5, 3, 3));
		mDb.createEntry(new Entry(Dates.ConvertStringToDate("04/06/2015"), "03:30 PM", "05:00 PM", 0, 1.5, 4, 4));
	}
}
