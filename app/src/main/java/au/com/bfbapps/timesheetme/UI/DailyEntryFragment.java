package au.com.bfbapps.timesheetme.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

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

	private FloatingActionButton mAddTimesButton;
	private FloatingActionButton mSubmitTimesButton;
	private FloatingActionMenu mFloatingActionMenu;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_daily_entry, container, false);
		((MainActivity)getActivity()).setActionBarTitle("");

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


		// TEST DATA
		mDailyEntries = CreateTestData();

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

	private List<DailyEntry> CreateTestData(){
		List<DailyEntry> data = new ArrayList<>();
		data.add(new DailyEntry("27/6/15", "Possum Magic", "Graphic Design", "08:00", "11:00", "3"));
		data.add(new DailyEntry("27/6/15", "Serious Sam", "Programming", "11:00", "12:30", "1.5"));
		data.add(new DailyEntry("27/6/15", "Possum Magic", "Graphic Design", "13:30", "17:00", "3.5"));
		return data;
	}
}
