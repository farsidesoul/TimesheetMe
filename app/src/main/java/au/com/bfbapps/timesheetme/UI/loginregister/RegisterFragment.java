package au.com.bfbapps.timesheetme.UI.loginregister;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.Util.ApiCalls;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterFragment extends Fragment {

	private EditText mEmailEditText;
	private EditText mUserNameEditText;
	private EditText mPasswordEditText;
	private EditText mPasswordConfirmEditText;
	private Button mRegisterButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_register, container, false);

		mEmailEditText = (EditText)rootView.findViewById(R.id.register_email);
		mUserNameEditText = (EditText)rootView.findViewById(R.id.register_username);
		mPasswordEditText = (EditText)rootView.findViewById(R.id.register_password);
		mPasswordConfirmEditText = (EditText)rootView.findViewById(R.id.register_password_confirm);

		mRegisterButton = (Button)rootView.findViewById(R.id.register_register_button);
		mRegisterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

		return rootView;
	}

	private void register(){
		Map<String, String> params = new HashMap<>();
		params.put("tag", "register");
		params.put("username", mUserNameEditText.getText().toString());
		params.put("password", mPasswordEditText.getText().toString());
		params.put("email", mEmailEditText.getText().toString());
		ApiCalls.getRegisterInterface()
				.register(params, new Callback<JsonObject>() {
					@Override
					public void success(JsonObject s, Response response) {
					}

					@Override
					public void failure(RetrofitError error) {

					}
				});
	}
}
