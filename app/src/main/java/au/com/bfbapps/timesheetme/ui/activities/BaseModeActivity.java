package au.com.bfbapps.timesheetme.ui.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import au.com.bfbapps.timesheetme.utils.Dates;
import au.com.bfbapps.timesheetme.adapters.DailyEntryViewPagerAdapter;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseModeActivity extends AppCompatActivity {

	public static final int MAX_PAGES = 10000;
	public static final int CURRENT_DAY_PAGE = MAX_PAGES / 2;

	protected DatabaseHelper db;
	@Bind(R.id.text_title)
	TextView toolbarTitle;
	@Bind(R.id.app_bar)
	Toolbar toolbar;
	@Bind(R.id.daily_entry_pager)
	ViewPager entriesViewPager;
	@Bind(R.id.daily_entry_date_field)
	TextView dateTextView;
	@Bind(R.id.fab)
	FloatingActionMenu floatingActionMenu;
	@Bind(R.id.add_times_menu)
	FloatingActionButton addTimesButton;
	@Bind(R.id.goto_date_menu)
	FloatingActionButton gotoDateButton;
	@Bind(R.id.back_to_today_menu)
	FloatingActionButton backToTodayButton;
	@Bind(R.id.navigation_view)
	NavigationView navigationView;
	@Bind(R.id.drawer_layout)
	DrawerLayout drawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_appbar);
		ButterKnife.bind(this);
		navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);

		setupToolbar();

		ActionBarDrawerToggle actionBarDrawerToggle =
				new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
						R.string.drawer_closed) {
					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
					}

					@Override
					public void onDrawerOpened(View drawerView) {
						super.onDrawerOpened(drawerView);
					}
				};
		drawerLayout.setDrawerListener(actionBarDrawerToggle);
		actionBarDrawerToggle.syncState();

		db = new DatabaseHelper(getApplicationContext());

		setupViewPager();

		setDateOnActionBar(new Date());
	}

	private void setupViewPager() {
		entriesViewPager.setAdapter(new DailyEntryViewPagerAdapter(getResources(), getSupportFragmentManager()));
		entriesViewPager.setCurrentItem(CURRENT_DAY_PAGE, false);
		entriesViewPager.getAdapter().notifyDataSetChanged();
		entriesViewPager.setOffscreenPageLimit(0);
		entriesViewPager.addOnPageChangeListener(onPageChangeListener);
	}

	private void setupToolbar() {
		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}
		setActionBarTitle("TimesheetMe");
	}

	public void setActionBarTitle(String title) {
		toolbarTitle.setText(title);
	}

	protected void setDateOnActionBar(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
		dateTextView.setText(dateFormat.format(date));
	}

	protected Date getDateFromActionBar(String date) {
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String convertTimeToString(int hour, int minute) {
		String hourString;
		String minuteString;
		String amPm;

		if (hour < 13) {
			amPm = "AM";
		} else {
			amPm = "PM";
		}

		if (hour > 12) {
			hour -= 12;
		} else if (hour == 0) {
			hour = 12;
		}
		hourString = "" + hour;

		if (minute < 10) {
			minuteString = "0" + minute;
		} else {
			minuteString = "" + minute;
		}

		return hourString + ":" + minuteString + " " + amPm;
	}

	private void selectDate() {
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
						entriesViewPager.setCurrentItem(positionOfSelection);
						entriesViewPager.getAdapter().notifyDataSetChanged();
						setDateOnActionBar(selectedDate.getTime());
					}
				}, year, month, day);

		datePickerDialog.setTitle("Select Date");
		datePickerDialog.show();
	}

	private int getPositionOfSelection(Date selection) {
		long today = new Date().getTime();
		today = today / (1000 * 60 * 60 * 24);
		long newPosition = selection.getTime();
		newPosition = newPosition / (1000 * 60 * 60 * 24);
		return (int) (CURRENT_DAY_PAGE - (today - newPosition));
	}

	private final NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
			new NavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(MenuItem menuItem) {
					if (menuItem.isChecked()) {
						menuItem.setChecked(false);
					} else {
						menuItem.setChecked(true);
					}

					drawerLayout.closeDrawers();

					switch (menuItem.getItemId()) {
						case R.id.home:
							break;
						case R.id.weekly_report:
							break;
						case R.id.export:
							break;
					}
					return true;
				}
			};

	private final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
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
	};

	@OnClick(R.id.add_times_menu)
	protected void addMenuClicked(){
		onAddButtonClick();
	}

	@OnClick(R.id.goto_date_menu)
	protected void gotoDateMenuClicked(){
		selectDate();
		floatingActionMenu.close(true);
	}

	@OnClick(R.id.back_to_today_menu)
	protected void backToTodayMenuClicked(){
		entriesViewPager.setCurrentItem(CURRENT_DAY_PAGE, false);
		setDateOnActionBar(new Date());
		entriesViewPager.getAdapter().notifyDataSetChanged();
		floatingActionMenu.close(true);
	}

	protected abstract void onAddButtonClick();

}
