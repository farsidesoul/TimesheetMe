package au.com.bfbapps.timesheetme.Util;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

public class ApiCalls {

	private static final String API_FILE = "/index.php";

	private static RestAdapter GetRestAdapter(){

		return new RestAdapter.Builder()
				.setRequestInterceptor(new RequestInterceptor() {
					@Override
					public void intercept(RequestInterceptor.RequestFacade request) {
						request.addHeader("Accept", "application/json");
						request.addHeader("Content-Type", "application/json");
					}
				})
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.setEndpoint("http://bfbapps.com.au/mobile_api")
				.build();
	}

	//region Register
	private static RegisterInterface sRegisterInterface;
	public static RegisterInterface getRegisterInterface(){
		if (sRegisterInterface == null) {
			sRegisterInterface = GetRestAdapter()
					.create(RegisterInterface.class);
		}
		return sRegisterInterface;
	}

	public interface RegisterInterface {
		@POST(API_FILE)
		void register(@Body Map<String, String> params, Callback<JsonObject> message);
	}
	//endregion

	//region Login
	private static LoginInterface sLoginInterface;
	public static LoginInterface getLoginInterface(){
		if (sLoginInterface == null){
			sLoginInterface = GetRestAdapter()
					.create(LoginInterface.class);
		}
		return sLoginInterface;
	}

	public interface LoginInterface {
		@POST(API_FILE)
		void login(@Body Map<String, String> params, Callback<JsonObject> message);
	}
	//endregion
}
