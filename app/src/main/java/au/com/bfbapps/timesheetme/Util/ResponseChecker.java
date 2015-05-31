package au.com.bfbapps.timesheetme.Util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

public class ResponseChecker {
	public static boolean CheckForErrorInResponse(JsonObject s) {
		JsonElement e = s.get("error");
		return e.getAsBoolean();
	}

	public static String GetErrorMessage(JsonObject s){
		JsonElement e = s.get("error_msg");
		return e.getAsString();
	}
}
