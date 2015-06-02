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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterFragment extends Fragment {

	private EditText mFirstNameEditText;
	private EditText mLastNameEditText;
	private EditText mEmailEditText;
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

		mFirstNameEditText = (EditText)rootView.findViewById(R.id.register_first_name);
		mLastNameEditText = (EditText)rootView.findViewById(R.id.register_last_name);
		mEmailEditText = (EditText)rootView.findViewById(R.id.register_email);
		mPasswordEditText = (EditText)rootView.findViewById(R.id.register_password);
		mPasswordConfirmEditText = (EditText)rootView.findViewById(R.id.register_password_confirm);

		mRegisterButton = (Button)rootView.findViewById(R.id.register_register_button);
		mRegisterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mFirstNameEditText.getText().toString().equals("")
						|| mLastNameEditText.getText().toString().equals("")
						|| mEmailEditText.getText().toString().equals("")
						|| mPasswordEditText.getText().toString().equals("")
						|| mPasswordConfirmEditText.getText().toString().equals("")){
					Toast.makeText(getActivity(), "Enter all information", Toast.LENGTH_SHORT).show();
				} else if (!mPasswordEditText.getText().toString().equals(mPasswordConfirmEditText.getText().toString())){
					Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
				} else {
					register();
				}
			}
		});

		return rootView;
	}

	private void register(){
		Map<String, String> params = new HashMap<>();
		params.put("tag", "register");
		params.put("firstName", mFirstNameEditText.getText().toString());
		params.put("lastName", mLastNameEditText.getText().toString());
		params.put("password", mPasswordEditText.getText().toString());
		params.put("email", mEmailEditText.getText().toString());
		ApiCalls.getRegisterInterface()
				.register(params, new Callback<JsonObject>() {
					@Override
					public void success(JsonObject s, Response response) {
						if(!ResponseChecker.CheckForErrorInResponse(s)){
							if (!ResponseChecker.CheckForErrorInResponse(s)) {
								Intent intent = new Intent(getActivity(), MainActivity.class);
								intent.putExtra("user", GsonConversions.createUser(s));
								startActivity(intent);
							} else {
								Toast.makeText(getActivity(),
										ResponseChecker.GetErrorMessage(s),
										Toast.LENGTH_SHORT).show();
							}
						}
					}

					@Override
					public void failure(RetrofitError error) {

					}
				});
	}
}
