package au.com.bfbapps.timesheetme.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
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
	private FloatingActionButton mGotoDateButton;
	private FloatingActionMenu mFloatingActionMenu;
	private FloatingActionButton mBackToTodayButton;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_daily_entry, container, false);
		((MainActivity)getActivity()).setActionBarTitle("");

		Log.d("day int", Dates.GetDayNameFromDate(new Date()) + "");

		mDb = new DatabaseHelper(getActivity().getApplicationContext());
		mJobList = mDb.getAllJobs();
		mTaskList = mDb.getAllTasks();

		mViewPager = (ViewPager)v.findViewById(R.id.daily_entry_pager);
		mViewPager.setAdapter(new DailyEntryViewPagerAdapter(getResources(), getActivity().getSupportFragmentManager()));
		mViewPager.setCurrentItem(5000, false);

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
				Log.d("Position", position + "");
				setDateOnActionBar(Dates.AddDaysToDate(new Date(), position - 5000));
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
		mGotoDateButton = (FloatingActionButton)v.findViewById(R.id.goto_date_menu);
		mBackToTodayButton = (FloatingActionButton)v.findViewById(R.id.back_to_today_menu);

		mAddTimesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enterStartTime();
				mFloatingActionMenu.close(true);

			}
		});

		mGotoDateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDate();
				mFloatingActionMenu.close(true);
			}
		});

		mBackToTodayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mViewPager.setCurrentItem(5000, false);
				setDateOnActionBar(new Date());
				mViewPager.getAdapter().notifyDataSetChanged();
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
						if (mFinishLong <= mStartLong){
							Toast.makeText(getActivity(),
									"Finish time must be after start",
									Toast.LENGTH_SHORT)
									.show();
							enterFinishTime();
						} else {
							enterJobAndTask();
						}
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

		totalBreakEditText.requestFocus();
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (!jobTextView.getText().toString().equals("")
						&& !taskTextView.getText().toString().equals("")) {

					Job job;
					Task task;

					if(mDb.getJobByName(jobTextView.getText().toString()) != null){
						job = mDb.getJobByName(jobTextView.getText().toString());
					} else {
						long jobId = mDb.createJob(new Job(jobTextView.getText().toString()));
						job = mDb.getJobById(jobId);
					}

					if(mDb.getTaskByName(taskTextView.getText().toString()) != null){
						task = mDb.getTaskByName(taskTextView.getText().toString());
					} else {
						long taskId = mDb.createTask(new Task(taskTextView.getText().toString()));
						task = mDb.getTaskById(taskId);
					}

					mBreak = (totalBreakEditText.getText().toString().equals("") ? 0 : Double.valueOf(totalBreakEditText.getText().toString()));
					mTotalHoursWorked = calculateHoursWorked(mStartLong, mFinishLong) - (mBreak / 60);

					mDb.createEntry(new Entry(getDateFromActionBar(mDateTextView.getText().toString()),
							mStartTime, mFinishTime, mBreak, mTotalHoursWorked, job, task));

					// Re-populate the job and task lists so we can re-use them immediately
					mJobList = mDb.getAllJobs();
					mTaskList = mDb.getAllTasks();

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

	private void selectDate(){
		Calendar mCurrentDate = Calendar.getInstance();
		int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
		int month = mCurrentDate.get(Calendar.MONTH);
		int year = mCurrentDate.get(Calendar.YEAR);

		final DatePickerDialog datePickerDialog =
				new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int yearSelected, int monthOfYearSelected, int dayOfMonthSelected) {
						Calendar selectedDate = Calendar.getInstance();
						selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonthSelected);
						selectedDate.set(Calendar.MONTH, monthOfYearSelected);
						selectedDate.set(Calendar.YEAR, yearSelected);
						int positionOfSelection = getPositionOfSelection(selectedDate.getTime());
						mViewPager.setCurrentItem(positionOfSelection);
						mViewPager.getAdapter().notifyDataSetChanged();
						setDateOnActionBar(selectedDate.getTime());
					}
				}, year, month, day);

		datePickerDialog.setTitle("Select Date");
		datePickerDialog.show();

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

	/**
	 * Gets todays date and selection, converts both to long format and
	 * calculates the position.
	 * @param selection Date to get the current position of
	 * @return position for the date selected
	 */
	private int getPositionOfSelection(Date selection){
		long today = new Date().getTime();
		today = today / (1000*60*60*24);
		long newPosition = selection.getTime();
		newPosition = newPosition / (1000*60*60*24);
		return (int)(5000 - (today - newPosition));
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
