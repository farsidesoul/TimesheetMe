package au.com.bfbapps.timesheetme;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import au.com.bfbapps.timesheetme.Util.ApiCalls;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends ActionBarActivity {

	private EditText mUsernameEditText;
	private EditText mPasswordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		register();

		mUsernameEditText = (EditText)findViewById(R.id.login_username_editText);
		mPasswordEditText = (EditText)findViewById(R.id.login_password_editText);

		Button loginButton = (Button)findViewById(R.id.login_login_button);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mUsernameEditText.getText().toString().equals("")
						&& !mPasswordEditText.getText().toString().equals("")){
					login(mUsernameEditText.getText().toString(),
							mPasswordEditText.getText().toString());
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_login, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void register(){
		Map<String, String> params = new HashMap<>();
		params.put("tag", "register");
		params.put("firstName", "Bilbo");
		params.put("lastName", "Baggins");
		params.put("username", "c");
		params.put("password", "b");
		params.put("email", "Bilbo@bilbo.com");
		params.put("organisation", "1");
		ApiCalls.getRegisterInterface()
				.register(params, new Callback<String>() {
					@Override
					public void success(String s, Response response) {
						Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
					}

					@Override
					public void failure(RetrofitError error) {

					}
				});
	}

	private void login(String username, String password){
		Map<String, String> params = new HashMap<>();
		params.put("tag", "login");
		params.put("username", username);
		params.put("password", password);

		ApiCalls.getLoginInterface()
				.login(params,
						new Callback<String>() {
							@Override
							public void success(String s, Response response) {
								Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
							}

							@Override
							public void failure(RetrofitError error) {

							}
						});
	}
}
