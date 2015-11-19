package au.com.bfbapps.timesheetme.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

	private String userId;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String organisation;

	public User(String userId, String firstName, String lastName, String username, String email, String organisation){
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.organisation = organisation;
	}

	public User(Parcel in){
		readFromParcel(in);
	}

	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getOrganisation() {
		return organisation;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(userId);
		parcel.writeString(firstName);
		parcel.writeString(lastName);
		parcel.writeString(username);
		parcel.writeString(email);
		parcel.writeString(organisation);
	}

	private void readFromParcel(Parcel in){
		userId = in.readString();
		firstName = in.readString();
		lastName = in.readString();
		username = in.readString();
		email = in.readString();
		organisation = in.readString();
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
