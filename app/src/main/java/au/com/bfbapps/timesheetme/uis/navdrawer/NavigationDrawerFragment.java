package au.com.bfbapps.timesheetme.uis.navdrawer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.adapters.NavViewAdapter;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements NavViewAdapter.ClickListener {

	public static final String PREF_FILE_NAME = "pref";
	public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

	private RecyclerView mRecyclerView;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private NavViewAdapter mNavViewAdapter;

	private boolean mUserLearnedDrawer;
	private boolean mFromSavedInstanceState;
	private View containerView;

	private Date startDate = null;
	private Date finishDate = null;


	public NavigationDrawerFragment() {
		// Required empty public constructor
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
		if (savedInstanceState != null) {
			mFromSavedInstanceState = true;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

		mRecyclerView = (RecyclerView) v.findViewById(R.id.nav_drawer_list);
		mNavViewAdapter = new NavViewAdapter(getActivity(), getNavItems());
		mNavViewAdapter.setClickListener(this);
		mRecyclerView.setAdapter(mNavViewAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		// Inflate the layout for this fragment
		return v;
	}

	public static List<NavDrawerItem> getNavItems() {
		List<NavDrawerItem> data = new ArrayList<>();
		int[] icons = {R.mipmap.ic_home_black_48dp,
				R.mipmap.ic_assessment_black_48dp,
				R.mipmap.ic_get_app_black_48dp};

		String[] titles = {"Home", "Weekly Review", "Export"};

		for (int i = 0; i < titles.length && i < icons.length; i++) {
			NavDrawerItem currentItem = new NavDrawerItem(icons[i], titles[i]);
			data.add(currentItem);
		}

		return data;
	}
	
	
	public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
		containerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
				drawerLayout,
				toolbar,
				R.string.drawer_open,
				R.string.drawer_closed) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!mUserLearnedDrawer) {
					mUserLearnedDrawer = true;
					saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
				}
				// Makes the activity draw the actionbar again
				getActivity().invalidateOptionsMenu();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);

				// Makes the activity draw the actionbar again
				getActivity().invalidateOptionsMenu();
			}
		};

		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(containerView);
		}

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

	}

	public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
		SharedPreferences sharedPreferences =
				context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(preferenceName, preferenceValue);
		editor.apply();
	}

	public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
		SharedPreferences sharedPreferences =
				context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(preferenceName, defaultValue);
	}

	@Override
	public void itemClicked(View view, int position) {
		// depending on position clicked, start relevant activity

		switch (position) {
			case 0:

				break;
			case 1:

				break;
			case 2:
				showExportDialog();
				break;

		}
	}

	private void showExportDialog() {

		final Calendar c = Calendar.getInstance();

		// Show export dialog
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.dialog_export);
		dialog.setTitle("Select dates for export");

		final TextView startTextView = (TextView) dialog.findViewById(R.id.export_start_date_textView);
		final TextView finishTextView = (TextView) dialog.findViewById(R.id.export_finish_date_textView);
		TextView cancelButton = (TextView) dialog.findViewById(R.id.export_cancel_button);
		TextView exportButton = (TextView) dialog.findViewById(R.id.export_save_button);

		startTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog startDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Calendar startCalendar = Calendar.getInstance();
						startCalendar.set(year, monthOfYear, dayOfMonth);
						startDate = startCalendar.getTime();
						startTextView.setText(Dates.ConvertDateToString(startDate));
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

				startDatePicker.show();
			}
		});

		finishTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog finishDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Calendar finishCalendar = Calendar.getInstance();
						finishCalendar.set(year, monthOfYear, dayOfMonth);
						finishDate = finishCalendar.getTime();
						finishTextView.setText(Dates.ConvertDateToString(finishDate));
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

				finishDatePicker.show();
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});

		exportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (startDate == null || finishDate == null) {
					Toast.makeText(getActivity(),
							String.format("Enter a %s date", (startDate == null ? "Start" : "Finish")),
							Toast.LENGTH_SHORT).show();
				} else {
					if(exportTimesheet(startDate, finishDate)){
						dialog.cancel();
					}
				}
			}
		});

		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				startDate = null;
				finishDate = null;
			}
		});

		dialog.show();
	}

	private boolean exportTimesheet(Date start, Date finish) {

		File directory = new File(Environment.getExternalStorageDirectory(), "bfbapps/timesheetme");
		directory.mkdirs();
		File exportFile = new File(directory,
				String.format("%s-%s Timesheet.csv",
						Dates.ConvertDateToExportString(start),
						Dates.ConvertDateToExportString(finish)));

		if (exportFile.exists()) exportFile.delete();

		PrintWriter printWriter = null;
		try {
			DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());

			printWriter = new PrintWriter(new FileWriter(exportFile));

			List<Entry> entries = db.getAllEntries();

			String header = "Date, Start Time, Finish Time, Break Time (mins), Hours Worked, Job Name, Task Name";
			printWriter.println(header);
			for (Entry entry : entries) {
				if (entry.getDate().after(start) && entry.getDate().before(finish)) {
					String line = String.format("%s, %s, %s, %.2f, %.2f, %s, %s",
							Dates.ConvertDateToString(entry.getDate()),
							entry.getStart(),
							entry.getFinish(),
							entry.getTotalBreak(),
							entry.getTotalHoursWorked(),
							entry.getJob().getJobName(),
							entry.getTask().getTaskName());
					printWriter.println(line);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "Export failed", Toast.LENGTH_SHORT).show();
			return false;
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}

		Toast.makeText(getActivity(),
				"Export successful - saved to bfbapps/timesheetme",
				Toast.LENGTH_SHORT).show();
		return true;
	}

}
