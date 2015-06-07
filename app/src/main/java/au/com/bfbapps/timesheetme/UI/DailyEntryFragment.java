package au.com.bfbapps.timesheetme.UI;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.adapters.DailyEntryViewPagerAdapter;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;
import au.com.bfbapps.timesheetme.models.Job;
import au.com.bfbapps.timesheetme.models.Task;

public class DailyEntryFragment extends Fragment {

	private TextView mDateTextView;

	private FloatingActionButton mAddTimesButton;
	private FloatingActionButton mSubmitTimesButton;
	private FloatingActionMenu mFloatingActionMenu;

	private ViewPager mViewPager;

	private List<Job> mJobList;
	private List<Task> mTaskList;

	private String mStartTime;
	private String mFinishTime;
	private double mBreak;
	private long mStartLong;
	private long mFinishLong;
	private double mTotalHoursWorked;

	private DatabaseHelper mDb;

	private int currentPosition;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_daily_entry, container, false);
		((MainActivity)getActivity()).setActionBarTitle("");

		mDb = new DatabaseHelper(getActivity().getApplicationContext());
		mJobList = mDb.getAllJobs();
		mTaskList = mDb.getAllTasks();

		mViewPager = (ViewPager)v.findViewById(R.id.daily_entry_pager);
		mViewPager.setAdapter(new DailyEntryViewPagerAdapter(getResources(), getActivity().getSupportFragmentManager()));
		mViewPager.setCurrentItem(5000, false);
		currentPosition = 5000;

		mViewPager.post(new Runnable() {
			public void run() {
				mViewPager.setCurrentItem(5000, false);
			}
		});
		mViewPager.getAdapter().notifyDataSetChanged();
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				setDateOnActionBar(Dates.AddDaysToDate(new Date(), position - 5000));
				currentPosition = position;
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		mDateTextView = (TextView)v.findViewById(R.id.daily_entry_date_field);
		setDateOnActionBar(new Date());

		// Floating Action Button menu
//		mFloatingActionMenu = (FloatingActionMenu)v.findViewById(R.id.fab);
		mAddTimesButton = (FloatingActionButton)v.findViewById(R.id.add_times_menu);
//		mSubmitTimesButton = (FloatingActionButton)v.findViewById(R.id.submit_times_menu);

		mAddTimesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterStartTime();
//				mFloatingActionMenu.close(true);

			}
		});



		return v;
	}

	private void setDateOnActionBar(Date date){
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
		mDateTextView.setText(dateFormat.format(date));
	}

	private Date getDateFromActionBar(String date){
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String convertTimeToString(int hour, int minute){
		String hourString;
		String minuteString;
		String amPm;

		if (hour < 13){
			amPm = "AM";
		} else {
			amPm = "PM";
		}

		if (hour > 12){
			hour -= 12;
		} else if (hour == 0){
			hour = 12;
		}
		hourString = "" + hour;

		if(minute < 10){
			minuteString = "0" + minute;
		} else {
			minuteString = "" + minute;
		}

		return hourString + ":" + minuteString + " " + amPm;
	}

	private void enterStartTime(){
		Calendar mCurrentTime = Calendar.getInstance();
		int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mCurrentTime.get(Calendar.MINUTE);
		final TimePickerDialog timePickerDialog =
				new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int hour, int minute) {
						mStartTime = convertTimeToString(hour, minute);
						Date date = Dates.ConvertTimeToDate(mStartTime);
						mStartLong = date.getTime();
						enterFinishTime();
					}
				}, hour, minute, true);
		timePickerDialog.setTitle("Enter Start Time");
		timePickerDialog.show();
	}

	private void enterFinishTime(){
		Calendar mCurrentTime = Calendar.getInstance();
		int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mCurrentTime.get(Calendar.MINUTE);
		TimePickerDialog timePickerDialog =
				new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int hour, int minute) {
						mFinishTime = convertTimeToString(hour, minute);
						Date date = Dates.ConvertTimeToDate(mFinishTime);
						mFinishLong = date.getTime();
						enterJobAndTask();
					}
				}, hour, minute, true);
		timePickerDialog.setTitle("Enter Finish Time");
		timePickerDialog.show();
	}

	private void enterJobAndTask(){
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.fragment_daily_entry_add_job_task_popup);
		dialog.setTitle("Enter Job and Task");

		final EditText totalBreakEditText = (EditText)dialog.findViewById(R.id.daily_entry_break_editText);
		final AutoCompleteTextView jobTextView =
				(AutoCompleteTextView) dialog.findViewById(R.id.daily_entry_job_picker);
		final AutoCompleteTextView taskTextView =
				(AutoCompleteTextView) dialog.findViewById(R.id.daily_entry_task_picker);
		TextView saveButton = (TextView)dialog.findViewById(R.id.daily_entry_save_button);
		TextView cancelButton = (TextView)dialog.findViewById(R.id.daily_entry_cancel_button);

		ArrayAdapter<String> jobAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, extractJobName(mJobList));
		jobTextView.setAdapter(jobAdapter);

		ArrayAdapter<String> taskAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, extractTaskName(mTaskList));
		taskTextView.setAdapter(taskAdapter);

		jobTextView.setThreshold(1);
		taskTextView.setThreshold(1);

		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (!jobTextView.getText().toString().equals("")
						&& !taskTextView.getText().toString().equals("")) {

					long mJobId;
					long mTaskId;

					if(mDb.getJobByName(jobTextView.getText().toString()) != null){
						mJobId = mDb.getJobByName(jobTextView.getText().toString()).getJobId();
					} else {
						mJobId = mDb.createJob(new Job(jobTextView.getText().toString()));
					}

					if(mDb.getTaskByName(taskTextView.getText().toString()) != null){
						mTaskId = mDb.getTaskByName(taskTextView.getText().toString()).getTaskId();
					} else {
						mTaskId = mDb.createTask(new Task(taskTextView.getText().toString()));
					}

					mBreak = (totalBreakEditText.getText().toString().equals("") ? 0 : Double.valueOf(totalBreakEditText.getText().toString()));
					mTotalHoursWorked = calculateHoursWorked(mStartLong, mFinishLong) - (mBreak / 60);

					mDb.createEntry(new Entry(getDateFromActionBar(mDateTextView.getText().toString()),
							mStartTime, mFinishTime, mBreak, mTotalHoursWorked, mJobId, mTaskId));
					mViewPager.getAdapter().notifyDataSetChanged();
				}
				dialog.dismiss();
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mStartTime = null;
				mFinishTime = null;
				dialog.dismiss();
			}
		});

		dialog.show();

	}

	/**
	 * Calculates the total worked hours
	 * @param start start time in long format
	 * @param finish finish time in long format
	 * @return total hours worked
	 */
	private double calculateHoursWorked(long start, long finish){
		return (double)(((finish - start) / 60000) / 60);
	}

	private ArrayList<String> extractJobName(List<Job> list){
		ArrayList<String> extract = new ArrayList<>();
		for(Job job : list){
			extract.add(job.getJobName());
		}
		return extract;
	}

	private ArrayList<String> extractTaskName(List<Task> list){
		ArrayList<String> extract = new ArrayList<>();
		for (Task task : list){
			extract.add(task.getTaskName());
		}
		return extract;
	}
	
	

}
