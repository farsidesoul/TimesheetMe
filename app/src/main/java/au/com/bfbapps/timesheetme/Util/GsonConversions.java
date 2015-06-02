package au.com.bfbapps.timesheetme.Util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import au.com.bfbapps.timesheetme.models.User;

public class GsonConversions {

	public static User createUser(JsonObject s){
		Gson gson = new Gson();
		JsonElement user = s.get("user");
		return gson.fromJson(user, User.class);
	}
}
