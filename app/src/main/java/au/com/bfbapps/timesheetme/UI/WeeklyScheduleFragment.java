package au.com.bfbapps.timesheetme.UI;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import au.com.bfbapps.timesheetme.R;

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



		return v;
	}
	
	
}
