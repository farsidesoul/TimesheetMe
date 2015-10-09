package au.com.bfbapps.timesheetme.ui.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.ui.navdrawer.NavigationDrawerFragment;
import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.adapters.DailyEntryViewPagerAdapter;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;

public abstract class BaseModeActivity extends AppCompatActivity {

	public static final int MAX_PAGES = 10000;
	public static final int CURRENT_DAY_PAGE = MAX_PAGES / 2;

	protected DatabaseHelper mDb;
	private Toolbar mToolbar;
	protected ViewPager mViewPager;
	protected TextView mDateTextView;
	protected FloatingActionMenu mFloatingActionMenu;
	protected FloatingActionButton mAddTimesButton;
	protected FloatingActionButton mGotoDateButton;
	protected FloatingActionButton mBackToTodayButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_appbar);

		mToolbar = (Toolbar) findViewById(R.id.app_bar);
		setupToolbar();

		NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
		drawerFragment.setUp(R.id.fragment_navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout),
				mToolbar);

		mDb = new DatabaseHelper(getApplicationContext());

		mDateTextView = (TextView) findViewById(R.id.daily_entry_date_field);
		mFloatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab);
		mAddTimesButton = (FloatingActionButton) findViewById(R.id.add_times_menu);
		mGotoDateButton = (FloatingActionButton) findViewById(R.id.goto_date_menu);
		mBackToTodayButton = (FloatingActionButton) findViewById(R.id.back_to_today_menu);

		mAddTimesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onAddButtonClick();
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
				mViewPager.setCurrentItem(CURRENT_DAY_PAGE, false);
				setDateOnActionBar(new Date());
				mViewPager.getAdapter().notifyDataSetChanged();
				mFloatingActionMenu.close(true);
			}
		});

		// View Pager
		mViewPager = (ViewPager) findViewById(R.id.daily_entry_pager);
		setupViewPager();

		setDateOnActionBar(new Date());

	}

	private void setupViewPager() {
		mViewPager.setAdapter(new DailyEntryViewPagerAdapter(getResources(), getSupportFragmentManager()));
		mViewPager.setCurrentItem(CURRENT_DAY_PAGE, false);
		mViewPager.getAdapter().notifyDataSetChanged();
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				setDateOnActionBar(Dates.AddDaysToDate(new Date(), position - CURRENT_DAY_PAGE));
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	private void setupToolbar() {
		setSupportActionBar(mToolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setTitle("");
		}
	}

	public void setActionBarTitle(String title) {
		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle(title);
	}

	protected void setDateOnActionBar(Date date){
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
		mDateTextView.setText(dateFormat.format(date));
	}

	protected Date getDateFromActionBar(String date){
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String convertTimeToString(int hour, int minute){
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

	private void selectDate(){
		Calendar mCurrentDate = Calendar.getInstance();
		int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
		int month = mCurrentDate.get(Calendar.MONTH);
		int year = mCurrentDate.get(Calendar.YEAR);

		final DatePickerDialog datePickerDialog =
				new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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

	private int getPositionOfSelection(Date selection){
		long today = new Date().getTime();
		today = today / (1000*60*60*24);
		long newPosition = selection.getTime();
		newPosition = newPosition / (1000*60*60*24);
		return (int)(CURRENT_DAY_PAGE - (today - newPosition));
	}

	protected abstract void onAddButtonClick();

}
