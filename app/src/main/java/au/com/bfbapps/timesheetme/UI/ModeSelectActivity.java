package au.com.bfbapps.timesheetme.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import au.com.bfbapps.timesheetme.R;

public class ModeSelectActivity extends AppCompatActivity implements View.OnClickListener {

	private CardView mSimpleMode;
	private CardView mAdvancedMode;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mode_select);

		mSimpleMode = (CardView) findViewById(R.id.card_simple_mode);
		mAdvancedMode = (CardView) findViewById(R.id.card_advanced_mode);
		mSimpleMode.setOnClickListener(this);
		mAdvancedMode.setOnClickListener(this);
	}

	public void onClick(View v){
		if (v == mSimpleMode){
			Toast.makeText(this, "Simple Mode Bro", Toast.LENGTH_SHORT).show();
		} else if(v == mAdvancedMode){
			Toast.makeText(this, "Advanced Mode Bro", Toast.LENGTH_SHORT).show();
		}
	}
	
}
