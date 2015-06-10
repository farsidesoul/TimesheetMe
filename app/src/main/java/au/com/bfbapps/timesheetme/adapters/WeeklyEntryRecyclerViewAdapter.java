package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;
import au.com.bfbapps.timesheetme.models.Job;
import au.com.bfbapps.timesheetme.models.Task;
import au.com.bfbapps.timesheetme.models.WeeklyEntry;

public class WeeklyEntryRecyclerViewAdapter extends RecyclerView.Adapter<WeeklyEntryRecyclerViewAdapter.WeeklyEntryItemViewHolder> {

	private Context mContext;
	private List<WeeklyEntry> mEntries;
	private LayoutInflater mInflater;
	private DatabaseHelper mDb;

	public WeeklyEntryRecyclerViewAdapter(Context context, List<WeeklyEntry> entries){
		mContext = context;
		mEntries = entries;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public WeeklyEntryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mInflater.inflate(R.layout.fragment_weekly_entry_single_day, parent, false);

		WeeklyEntryItemViewHolder holder = new WeeklyEntryItemViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(WeeklyEntryItemViewHolder holder, int position) {
		mDb = new DatabaseHelper(mContext.getApplicationContext());

		WeeklyEntry currentItem = mEntries.get(position);

		holder.dayName.setText(currentItem.getDayOfWeek());
		holder.dayDate.setText(Dates.ConvertDateToString(currentItem.getDayDate()));

		List<String> jobList = new ArrayList<>();
		List<Task> taskList = new ArrayList<>();

		for (Entry entry : currentItem.getEntries()){
			if (jobList.contains(entry.getJob().getJobName())){

			} else {
				jobList.add(entry.getJob().getJobName());
			}
		}
	}

	@Override
	public int getItemCount() {
		return mEntries.size();
	}

	private void getRelevantEntries(){

	}

	class WeeklyEntryItemViewHolder extends RecyclerView.ViewHolder {

		TextView dayName;
		TextView dayDate;
		TextView jobName;
		TextView taskList;
		TextView totalHours;
		TextView taskHours;

		public WeeklyEntryItemViewHolder(View itemView) {
			super(itemView);
			dayName = (TextView)itemView.findViewById(R.id.weekly_entry_day_of_week);
			dayDate = (TextView)itemView.findViewById(R.id.weekly_entry_date);
			jobName = (TextView)itemView.findViewById(R.id.weekly_entry_job_name);
			taskList = (TextView)itemView.findViewById(R.id.weekly_entry_task_name);
			totalHours = (TextView)itemView.findViewById(R.id.weekly_entry_job_total_hours);
			taskHours = (TextView)itemView.findViewById(R.id.weekly_entry_task_hours);
		}


	}
}
