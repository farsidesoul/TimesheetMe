package au.com.bfbapps.timesheetme.ui.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.helper.DatabaseHelper;
import au.com.bfbapps.timesheetme.models.Entry;
import au.com.bfbapps.timesheetme.utils.DateUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimpleEntryDialogFragment extends DialogFragment {

	@Bind(R.id.text_start_time)
	TextView startTime;
	@Bind(R.id.text_finish_time)
	TextView finishTime;
	@Bind(R.id.checkbox_break)
	CheckBox tookBreak;
	@Bind(R.id.text_break_title)
	TextView breakTitle;
	@Bind(R.id.edit_break)
	EditText breakTime;
	@Bind(R.id.text_add)
	TextView add;
	@Bind(R.id.text_cancel)
	TextView cancel;

	Date now;
	boolean breakIsVisible;

	private long selectedStartTime;
	private long selectedFinishTime;
	private int selectedBreakLength;
	private OnAddClickListener onAddClickListener;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);

		// request a window without the title
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		return dialog;
	}

	@Override
	public void onStart() {
		super.onStart();
		int width = getResources().getDisplayMetrics().widthPixels;
		getDialog().getWindow().setLayout(width,
				WindowManager.LayoutParams.WRAP_CONTENT);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_simple_entry, container, false);
		ButterKnife.bind(this, v);
		setDefaultTimes();
		breakIsVisible = false;
		now = new Date();

		return v;
	}

	private void setDefaultTimes() {
		startTime.setText("9:00 am");
		finishTime.setText("5:00 pm");
	}

	@OnClick(R.id.text_start_time)
	public void setStartTime() {
		TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker timePicker, int hour, int minute) {
				selectedStartTime = getTimeFromPicker(hour, minute);
				startTime.setText(DateUtil.convertLongToTimeString(selectedStartTime));
			}
		}, 9, 0, true);
		timePickerDialog.show();
	}

	@OnClick(R.id.text_finish_time)
	public void selectFinishTime() {
		TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker timePicker, int hour, int minute) {
				selectedFinishTime = getTimeFromPicker(hour, minute);
				finishTime.setText(DateUtil.convertLongToTimeString(selectedFinishTime));
			}
		}, 17, 0, true);
		timePickerDialog.show();
	}

	@OnClick(R.id.checkbox_break)
	public void toggleBreakTime() {
			if (breakIsVisible) {
				breakTime.setVisibility(View.GONE);
				breakTitle.setVisibility(View.GONE);
				breakIsVisible = false;
			} else {
				breakTime.setVisibility(View.VISIBLE);
				breakTitle.setVisibility(View.VISIBLE);
				breakIsVisible = true;
			}
		}

	@OnClick(R.id.text_add)
	public void addTime(){
		if(timesAreValid()){
			Entry currentEntry = new Entry(now, selectedStartTime, selectedFinishTime,
					selectedBreakLength, Entry.MODE_SIMPLE);

			DatabaseHelper db = new DatabaseHelper(getActivity());
			db.createEntry(currentEntry);

			if(onAddClickListener != null) onAddClickListener.onAddClicked();
		}
	}

	private boolean timesAreValid() {
		if(selectedFinishTime - selectedStartTime < 0){
			Toast.makeText(getActivity(),
					"Please ensure finish time is after start time", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(selectedFinishTime - selectedStartTime + (selectedBreakLength * 60 * 1000) < 0){
			Toast.makeText(getActivity(),
					"Please ensure finish time is after start time including the break time",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@OnClick(R.id.text_cancel)
	public void cancelEntry(){
		if(!startTime.getText().toString().equals("")
				|| !finishTime.getText().toString().equals("")
				|| !breakTime.getText().toString().equals("")){
			final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
					.setMessage("You have unsaved changes, are you sure you wish to cancel?")
					.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							closeDialog();
						}
					})
					.setNegativeButton("NO", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Do nothing
						}
					}).create();
			alertDialog.show();
		} else {
			closeDialog();
		}
	}

	private long getTimeFromPicker(int hour, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		return cal.getTimeInMillis();
	}

	private void closeDialog(){
		dismiss();
	}

	public void setOnAddClickListener(OnAddClickListener listener){
		onAddClickListener = listener;
	}

	public interface OnAddClickListener {
		void onAddClicked();
	}
}
