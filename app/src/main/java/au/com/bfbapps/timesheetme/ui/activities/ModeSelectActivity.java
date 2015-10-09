package au.com.bfbapps.timesheetme.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import au.com.bfbapps.timesheetme.R;

public class ModeSelectActivity extends AppCompatActivity implements View.OnClickListener {

	public static final byte MODE_SIMPLE = 0;
	public static final byte MODE_ADVANCED = 1;

	private CardView mSimpleMode;
	private CardView mAdvancedMode;

	private SharedPreferences mPrefs;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mode_select);

		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mSimpleMode = (CardView) findViewById(R.id.card_simple_mode);
		mAdvancedMode = (CardView) findViewById(R.id.card_advanced_mode);
		mSimpleMode.setOnClickListener(this);
		mAdvancedMode.setOnClickListener(this);
	}

	public void onClick(View v){
		if (v == mSimpleMode){
			mPrefs.edit().putInt("mode", MODE_SIMPLE).apply();
			startClassActivity(SimpleDailyEntryActivity.class);
		} else if(v == mAdvancedMode){
			mPrefs.edit().putInt("mode", MODE_ADVANCED).apply();
			startClassActivity(AdvancedDailyEntryActivity.class);
		}
	}

	private void startClassActivity(Class c){
		startActivity(new Intent(this, c));
	}
	
}
