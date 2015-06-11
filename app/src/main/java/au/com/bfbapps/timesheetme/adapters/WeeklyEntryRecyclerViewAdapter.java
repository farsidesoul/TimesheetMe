package au.com.bfbapps.timesheetme.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

		// This works for setting the job layout.
		// Inside this we need to create each view dynamically
		// We can then put the views into the layout in the order required.
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_END);

		LinearLayout l = new LinearLayout(mContext);
		l.setOrientation(LinearLayout.VERTICAL);


		List<Job> jobList = new ArrayList<>();
		List<Task> taskList = new ArrayList<>();

		for (Entry entry : currentItem.getEntries()){
			if (jobList.contains(entry.getJob())){


			} else {
				jobList.add(entry.getJob());
//				TextView t = new TextView(mContext);
//				t.setText(entry.getJob().getJobName());
//				t.setTextColor(mContext.getResources().getColor(R.color.primaryColor));
			}
			RelativeLayout layout = new RelativeLayout(mContext);
			TextView t = new TextView(mContext);
			t.setText(entry.getJob().getJobName());
			t.setTextColor(mContext.getResources().getColor(R.color.primaryColor));

			TextView hours = new TextView(mContext);
			hours.setText(entry.getTotalHoursWorked() + "");
			hours.setTextColor(mContext.getResources().getColor(R.color.primaryColor));

			layout.addView(t);
			layout.addView(hours, params);

			RelativeLayout layout2 = new RelativeLayout(mContext);
			TextView task = new TextView(mContext);
			task.setText(entry.getTask().getTaskName());
			task.setTextColor(mContext.getResources().getColor(R.color.primaryColor));

			TextView taskHours = new TextView(mContext);
			taskHours.setText(entry.getTotalHoursWorked() + "");
			taskHours.setTextColor(mContext.getResources().getColor(R.color.primaryColor));

			layout2.addView(task);
			layout2.addView(taskHours, params);

			l.addView(layout);
			l.addView(layout2);
		}


		holder.entryLayout.addView(l);
	}

	@Override
	public int getItemCount() {
		return mEntries.size();
	}

	private void getRelevantEntries(){

	}

	class WeeklyEntryItemViewHolder extends RecyclerView.ViewHolder {

		LinearLayout entryLayout;
		TextView dayName;
		TextView dayDate;
		TextView jobName;
		TextView taskList;
		TextView totalHours;
		TextView taskHours;

		public WeeklyEntryItemViewHolder(View itemView) {
			super(itemView);
			entryLayout = (LinearLayout)itemView.findViewById(R.id.weekly_entry_linear_layout);
			dayName = (TextView)itemView.findViewById(R.id.weekly_entry_day_of_week);
			dayDate = (TextView)itemView.findViewById(R.id.weekly_entry_date);
			jobName = (TextView)itemView.findViewById(R.id.weekly_entry_job_name);
			taskList = (TextView)itemView.findViewById(R.id.weekly_entry_task_name);
			totalHours = (TextView)itemView.findViewById(R.id.weekly_entry_job_total_hours);
			taskHours = (TextView)itemView.findViewById(R.id.weekly_entry_task_hours);
		}


	}
}
