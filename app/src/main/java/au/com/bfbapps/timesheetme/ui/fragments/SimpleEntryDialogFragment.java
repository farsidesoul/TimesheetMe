package au.com.bfbapps.timesheetme.ui.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Date;

import au.com.bfbapps.timesheetme.R;
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

		breakIsVisible = false;
		now = new Date();

		return v;
	}

	@OnClick(R.id.text_start_time)
	public void setStartTime() {
		TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker timePicker, int i, int i1) {

			}
		}, 9, 0, true);
		timePickerDialog.show();
	}

	@OnClick(R.id.text_finish_time)
	public void selectFinishTime() {
		TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker timePicker, int i, int i1) {

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

	}

	@OnClick(R.id.text_cancel)
	public void cancelEntry(){
		dismiss();
	}
}
