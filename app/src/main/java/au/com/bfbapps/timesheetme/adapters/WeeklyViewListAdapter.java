package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import au.com.bfbapps.timesheetme.R;

public class WeeklyViewListAdapter extends ArrayAdapter<String[]> {

	private Context mContext;
	private ArrayList<String[]> mDays;

	public WeeklyViewListAdapter(Context context, ArrayList<String[]> days) {
		super(context, R.layout.fragment_weekly_entry_single_day, days);
		mContext = context;
		mDays = days;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_weekly_entry_single_day, parent, false);

			holder = new ViewHolder();
			holder.day = (TextView)convertView.findViewById(R.id.weekly_entry_day_of_week);
//			holder.hoursWorked = (TextView)convertView.findViewById(R.id.weekly_entry_hours_worked);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}

		holder.day.setText(mDays.get(position)[0]);
		holder.hoursWorked.setText(mDays.get(position)[1]);
		return convertView;
	}

	private static class ViewHolder {
		public TextView day;
		public TextView hoursWorked;
	}
}
