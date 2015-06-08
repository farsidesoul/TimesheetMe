package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;
import au.com.bfbapps.timesheetme.models.Job;
import au.com.bfbapps.timesheetme.models.Task;

public class WeeklyEntryRecyclerViewAdapter extends RecyclerView.Adapter<WeeklyEntryRecyclerViewAdapter.WeeklyEntryItemViewHolder> {

	private Context mContext;
	private List<Entry> mEntries;
	private Date mStartDate;
	private Date mFinishDate;
	private LayoutInflater mInflater;
	private DatabaseHelper mDb;

	public WeeklyEntryRecyclerViewAdapter(Context context, List<Entry> entries, Date startDate, Date finishDate){
		mContext = context;
		mEntries = entries;
		mStartDate = startDate;
		mFinishDate = finishDate;
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

		Entry currentItem = mEntries.get(position);
		Job currentItemJob = mDb.getJobById(currentItem.getJobId());
		Task currentItemTask = mDb.getTaskById(currentItem.getTaskId());

		holder.dayName.setText(Dates.GetDayNameFromDate(currentItem.getDate()));
	}

	@Override
	public int getItemCount() {
		return mEntries.size();
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
