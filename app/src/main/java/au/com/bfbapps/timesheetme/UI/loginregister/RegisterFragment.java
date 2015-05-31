package au.com.bfbapps.timesheetme.UI.loginregister;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import au.com.bfbapps.timesheetme.R;
import au.com.bfbapps.timesheetme.Util.ApiCalls;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_register, container, false);


		return rootView;
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
						Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
					}

					@Override
					public void failure(RetrofitError error) {

					}
				});
	}
}
