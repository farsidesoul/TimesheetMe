package au.com.bfbapps.timesheetme.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		int mode = prefs.getInt("mode", -1);

		if(mode != -1){
			switch (mode){
				case ModeSelectActivity.MODE_SIMPLE:
					startClassActivity(SimpleDailyEntryActivity.class);
					break;
				case ModeSelectActivity.MODE_ADVANCED:
					startClassActivity(AdvancedDailyEntryActivity.class);
					break;
			}
		} else {
			startClassActivity(ModeSelectActivity.class);
		}
	}

	private void startClassActivity(Class a){
		startActivity(new Intent(this, a));
	}
}
