package au.com.bfbapps.timesheetme.models;

import com.google.gson.annotations.SerializedName;

public class DailyEntry {

	@SerializedName("date")
	private String mDate;
	@SerializedName("title")
	private String mTitle;
	@SerializedName("subTitle")
	private String mSubTitle;
	@SerializedName("start")
	private String mStart;
	@SerializedName("finish")
	private String mFinish;
	@SerializedName("totalHours")
	private String mTotalHours;

	public DailyEntry(String date, String title, String subTitle, String start, String finish, String totalHours){
		mDate = date;
		mTitle = title;
		mSubTitle = subTitle;
		mStart = start;
		mFinish = finish;
		mTotalHours = totalHours;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String date) {
		mDate = date;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getSubTitle() {
		return mSubTitle;
	}

	public void setSubTitle(String subTitle) {
		mSubTitle = subTitle;
	}

	public String getStart() {
		return mStart;
	}

	public void setStart(String start) {
		mStart = start;
	}

	public String getFinish() {
		return mFinish;
	}

	public void setFinish(String finish) {
		mFinish = finish;
	}

	public String getTotalHours() {
		return mTotalHours;
	}

	public void setTotalHours(String totalHours) {
		mTotalHours = totalHours;
	}
}
