package au.com.bfbapps.timesheetme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.utils.Dates;
import au.com.bfbapps.timesheetme.models.Entry;
import au.com.bfbapps.timesheetme.models.WeeklyEntry;

public class WeeklyEntryRecyclerViewAdapter extends RecyclerView.Adapter<WeeklyEntryRecyclerViewAdapter.WeeklyEntryItemViewHolder> {

	private Context mContext;
	private List<WeeklyEntry> mEntries;
	private LayoutInflater mInflater;

	public WeeklyEntryRecyclerViewAdapter(Context context, List<WeeklyEntry> entries){
		mContext = context;
		mEntries = entries;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public WeeklyEntryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mInflater.inflate(R.layout.fragment_weekly_entry_single_day, parent, false);

		return new WeeklyEntryItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(WeeklyEntryItemViewHolder holder, int position) {

		WeeklyEntry currentItem = mEntries.get(position);
		holder.dayName.setText(currentItem.getDayOfWeek());
		holder.dayDate.setText(Dates.ConvertDateToString(currentItem.getDayDate()));

		// This works for setting the job layout.
		// Inside this we need to create each view dynamically
		// We can then put the views into the layout in the order required.
		RelativeLayout.LayoutParams jobTaskHourParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		jobTaskHourParams.addRule(RelativeLayout.ALIGN_PARENT_END);

		LinearLayout dayLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams dayLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
				jobName.setTextColor(mContext.getResources().getColor(R.color.primaryText));

				TextView hours = new TextView(mContext);
				hours.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				hours.setTextColor(mContext.getResources().getColor(R.color.primaryText));
				hours.setGravity(Gravity.END);

				// This is the total hours for the job
				double totalHours = 0;
				// Used to add task list views to an array
				List<View> taskViewList = new ArrayList<>();
				// Key Value pair of task names and their hours assigned against them
				Map<String, Double> taskIds = new HashMap<>();

				// Run through the list of entries within our current day
				for (int i = 0; i < currentItem.getEntries().size(); i++){
					// If this entries job id is one in our list of entries
					if(currentItem.getEntries().get(i).getJob().getJobId() == (entry.getJob().getJobId())){
						// If the task name does not exist within the HashMap, add it
						if(!taskIds.containsKey(currentItem.getEntries().get(i).getTask().getTaskName())) {
							taskIds.put(currentItem.getEntries().get(i).getTask().getTaskName(), currentItem.getEntries().get(i).getTotalHoursWorked());
						} else {
							// Else take the current total hours assigned for this entry and add it to key
							double currentTotal = taskIds.get(currentItem.getEntries().get(i).getTask().getTaskName());
							taskIds.put(currentItem.getEntries().get(i).getTask().getTaskName(), currentTotal + currentItem.getEntries().get(i).getTotalHoursWorked());
						}
						totalHours += currentItem.getEntries().get(i).getTotalHoursWorked();
					}
				}

				// Run through each key value pair and assign the name and hours to appropriate view
				for (Map.Entry<String, Double> kv : taskIds.entrySet()){
					RelativeLayout taskLayout = new RelativeLayout(mContext);
					TextView task = new TextView(mContext);
					task.setText(kv.getKey());
					task.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
					task.setTextColor(mContext.getResources().getColor(R.color.secondaryText));
					task.setPadding(16, 0, 16, 0);

					TextView taskHours = new TextView(mContext);
					taskHours.setText(String.format("%.2f", kv.getValue()));
					taskHours.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
					taskHours.setTextColor(mContext.getResources().getColor(R.color.secondaryText));
					taskHours.setGravity(Gravity.END);

					taskLayout.addView(task);
					taskLayout.addView(taskHours, jobTaskHourParams);

					taskViewList.add(taskLayout);
				}

				// Set the hours now that all our tasks have been totalled for this job
				hours.setText(String.format("%.2f", totalHours));
				// Add the job name and hours to the view
				jobLayout.addView(jobName);
				jobLayout.addView(hours, jobTaskHourParams);
				dayLayout.addView(jobLayout);
				// run through each of the task views and add it to the linear layout
				for(View v : taskViewList){
					dayLayout.addView(v);
				}
			}
		}

		// If we don't have any jobs for the day, indicate so to user
		if(jobList.isEmpty()){
			TextView noJobsOnDay = new TextView(mContext);
			noJobsOnDay.setText("No entries for today");
			noJobsOnDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			noJobsOnDay.setTextColor(mContext.getResources().getColor(R.color.secondaryText));
			noJobsOnDay.setPadding(16, 0, 16, 0);
			dayLayout.addView(noJobsOnDay);
		}

		// Add an empty view to add some spacing
		View blankView = new View(mContext);
		blankView.setMinimumHeight(40);

		// Finally add the view to the layout
		holder.entryLayout.addView(dayLayout, dayLayoutParams);
		holder.entryLayout.addView(blankView);

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
