package au.com.bfbapps.timesheetme.UI.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.UI.MainActivity;
import au.com.bfbapps.timesheetme.Util.ApiCalls;
import au.com.bfbapps.timesheetme.Util.GsonConversions;
import au.com.bfbapps.timesheetme.Util.ResponseChecker;
import au.com.bfbapps.timesheetme.models.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginFragment extends Fragment {

	private EditText mEmailEditText;
	private EditText mPasswordEditText;
	private Button mLoginButton;
	private User mUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_login, container, false);

		mEmailEditText = (EditText)rootView.findViewById(R.id.login_email_editText);
		mPasswordEditText = (EditText)rootView.findViewById(R.id.login_password_editText);
		mLoginButton = (Button)rootView.findViewById(R.id.login_login_button);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				login(mEmailEditText.getText().toString(),
						mPasswordEditText.getText().toString());

			}
		});

		return rootView;
	}

	private void login(String email, String password){
		Map<String, String> params = new HashMap<>();
		params.put("tag", "login");
		params.put("email", email);
		params.put("password", password);

		ApiCalls.getLoginInterface()
				.login(params,
						new Callback<JsonObject>() {
							@Override
							public void success(JsonObject s, Response response) {

								if (!ResponseChecker.CheckForErrorInResponse(s)) {
									mUser = GsonConversions.createUser(s);
									Log.d("USER", mUser.getFirstName());
									Intent intent = new Intent(getActivity(), MainActivity.class);
									intent.putExtra("user", mUser);
									startActivity(intent);
								} else {
									Toast.makeText(getActivity(),
											ResponseChecker.GetErrorMessage(s),
											Toast.LENGTH_SHORT).show();
								}
							}

							@Override
							public void failure(RetrofitError error) {
								Toast.makeText(getActivity(), "Cannot connect to server.", Toast.LENGTH_SHORT).show();
							}
						});
	}

}
