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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
	private double mTotalHoursWorked;
	private int mJobId;
	private int mTaskId;

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
		mFloatingActionMenu = (FloatingActionMenu)v.findViewById(R.id.fab);
		mAddTimesButton = (FloatingActionButton)v.findViewById(R.id.add_times_menu);
		mSubmitTimesButton = (FloatingActionButton)v.findViewById(R.id.submit_times_menu);

		mAddTimesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterStartTime();
				mFloatingActionMenu.close(true);

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

		if(hour < 10){
			hourString = "0" + hour;
		} else {
			hourString = "" + hour;
		}

		if(minute < 10){
			minuteString = "0" + minute;
		} else {
			minuteString = "" + minute;
		}

		if (hour < 13){
			amPm = "AM";
		} else {
			amPm = "PM";
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
					mDb.createEntry(new Entry(getDateFromActionBar(mDateTextView.getText().toString()),
							mStartTime, mFinishTime, 0, 0, 1, 1));
//					mViewPager.setAdapter(new DailyEntryViewPagerAdapter(getResources(),
//							getActivity().getSupportFragmentManager()));
//					mViewPager.setCurrentItem(currentPosition);
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
				mJobId = 0;
				mTaskId = 0;
				dialog.dismiss();
			}
		});

		dialog.show();

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
