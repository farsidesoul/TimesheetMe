package au.com.bfbapps.timesheetme.UI.activities;

import android.os.Bundle;
import android.widget.Toast;

public class SimpleDailyEntryActivity extends BaseModeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onAddButtonClick() {
		Toast.makeText(this, "Lalal", Toast.LENGTH_SHORT).show();
	}
}
