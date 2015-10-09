package au.com.bfbapps.timesheetme.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
