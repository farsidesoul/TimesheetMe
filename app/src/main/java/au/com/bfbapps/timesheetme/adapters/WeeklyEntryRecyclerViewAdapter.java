package au.com.bfbapps.timesheetme.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
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
		RelativeLayout.LayoutParams jobTaskHourparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		jobTaskHourparams.addRule(RelativeLayout.ALIGN_PARENT_END);


		RelativeLayout.LayoutParams taskNameParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		taskNameParams.setMargins(20, 0, 0, 0);

		LinearLayout dayLayout = new LinearLayout(mContext);
		dayLayout.setOrientation(LinearLayout.VERTICAL);

		List<Long> jobList = new ArrayList<>();

		// Run through our entries listed in this day
		for (Entry entry : currentItem.getEntries()){
			// If the job isn't within the jobList, we need to create the job and tasks assigned against it
			if (!jobList.contains(entry.getJob().getJobId())) {
				jobList.add(entry.getJob().getJobId());
				RelativeLayout jobLayout = new RelativeLayout(mContext);
				TextView jobName = new TextView(mContext);
				jobName.setText(entry.getJob().getJobName());
				jobName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				jobName.setTextColor(mContext.getResources().getColor(R.color.primaryColor));

				TextView hours = new TextView(mContext);
				hours.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				hours.setTextColor(mContext.getResources().getColor(R.color.primaryColor));


				double totalHours = 0;
				List<View> taskViewList = new ArrayList<>();
				for (int i = 0; i < currentItem.getEntries().size(); i++){
					if(currentItem.getEntries().get(i).getJob().getJobId() == (entry.getJob().getJobId())){
						RelativeLayout taskLayout = new RelativeLayout(mContext);
						TextView task = new TextView(mContext);
						task.setText(currentItem.getEntries().get(i).getTask().getTaskName());
						task.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
						task.setTextColor(mContext.getResources().getColor(R.color.primaryColor));

						TextView taskHours = new TextView(mContext);
						taskHours.setText(String.format("%.2f", currentItem.getEntries().get(i).getTotalHoursWorked()));
						taskHours.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
						taskHours.setTextColor(mContext.getResources().getColor(R.color.primaryColor));

						taskLayout.addView(task, taskNameParams);
						taskLayout.addView(taskHours, jobTaskHourparams);

						taskViewList.add(taskLayout);
						totalHours += currentItem.getEntries().get(i).getTotalHoursWorked();
					}
				}

				hours.setText(String.format("%.2f", totalHours));
				// Add the job name and hours to the view
				jobLayout.addView(jobName);
				jobLayout.addView(hours, jobTaskHourparams);
				dayLayout.addView(jobLayout);
				for(View v : taskViewList){
					Log.d("TASK", "Inflated Task");
					dayLayout.addView(v);
				}
			}
		}

		if(jobList.isEmpty()){
			TextView noJobsOnDay = new TextView(mContext);
			noJobsOnDay.setText("No entries for today");
			noJobsOnDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			noJobsOnDay.setTextColor(mContext.getResources().getColor(R.color.primaryColor));
			dayLayout.addView(noJobsOnDay);
		}
		holder.entryLayout.addView(dayLayout);
	}

	@Override
	public int getItemCount() {
		return mEntries.size();
	}

	class WeeklyEntryItemViewHolder extends RecyclerView.ViewHolder {

		LinearLayout entryLayout;
		TextView dayName;
		TextView dayDate;

		public WeeklyEntryItemViewHolder(View itemView) {
			super(itemView);
			entryLayout = (LinearLayout)itemView.findViewById(R.id.weekly_entry_linear_layout);
			dayName = (TextView)itemView.findViewById(R.id.weekly_entry_day_of_week);
			dayDate = (TextView)itemView.findViewById(R.id.weekly_entry_date);
		}


	}
}
