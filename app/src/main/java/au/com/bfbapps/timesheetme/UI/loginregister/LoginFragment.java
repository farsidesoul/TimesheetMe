package au.com.bfbapps.timesheetme.UI.loginregister;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.Util.ApiCalls;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginFragment extends Fragment {

	private EditText mUsernameEditText;
	private EditText mPasswordEditText;
	private Button mLoginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_login, container, false);

		mUsernameEditText = (EditText)rootView.findViewById(R.id.login_username_editText);
		mPasswordEditText = (EditText)rootView.findViewById(R.id.login_password_editText);
		mLoginButton = (Button)rootView.findViewById(R.id.login_login_button);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				login(mUsernameEditText.getText().toString(),
						mPasswordEditText.getText().toString());

			}
		});

		return rootView;
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
								Log.d("LOGIN", response.toString());
							}

							@Override
							public void failure(RetrofitError error) {
								Log.d("LOGIN", error.toString());

							}
						});
	}
}
