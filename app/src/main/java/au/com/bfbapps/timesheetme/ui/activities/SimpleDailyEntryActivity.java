package au.com.bfbapps.timesheetme.ui.activities;

import android.os.Bundle;

import au.com.bfbapps.timesheetme.ui.fragments.SimpleEntryDialogFragment;

public class SimpleDailyEntryActivity extends BaseModeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	protected void onAddButtonClick() {
		SimpleEntryDialogFragment fragment = new SimpleEntryDialogFragment();
		fragment.show(getSupportFragmentManager(), "");
	}
}
