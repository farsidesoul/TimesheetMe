package au.com.bfbapps.timesheetme.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

	@SerializedName("userId")
	private String mUserId;
	@SerializedName("firstName")
	private String mFirstName;
	@SerializedName("lastName")
	private String mLastName;
	@SerializedName("username")
	private String mUsername;
	@SerializedName("email")
	private String mEmail;
	@SerializedName("organisation")
	private String mOrganisation;

	public User(String userId, String firstName, String lastName, String username, String email, String organisation){
		mUserId = userId;
		mFirstName = firstName;
		mLastName = lastName;
		mUsername = username;
		mEmail = email;
		mOrganisation = organisation;
	}

	public User(Parcel in){
		readFromParcel(in);
	}

	public String getUserId() {
		return mUserId;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public String getLastName() {
		return mLastName;
	}

	public String getUsername() {
		return mUsername;
	}

	public String getEmail() {
		return mEmail;
	}

	public String getOrganisation() {
		return mOrganisation;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(mUserId);
		parcel.writeString(mFirstName);
		parcel.writeString(mLastName);
		parcel.writeString(mUsername);
		parcel.writeString(mEmail);
		parcel.writeString(mOrganisation);
	}

	private void readFromParcel(Parcel in){
		mUserId = in.readString();
		mFirstName = in.readString();
		mLastName = in.readString();
		mUsername = in.readString();
		mEmail = in.readString();
		mOrganisation = in.readString();
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel parcel) {
			return new User(parcel);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
