package au.com.bfbapps.timesheetme.models;

import java.util.Date;
import java.util.List;

public class WeeklyEntry {

	private String mDayOfWeek;
	private Date mDayDate;
	private List<Entry> mEntries;

	public WeeklyEntry(){

	}

	public WeeklyEntry(String dayOfWeek){
		mDayOfWeek = dayOfWeek;
	}

	public WeeklyEntry(String dayOfWeek, Date dayDate, List<Entry> entries){
		mDayOfWeek = dayOfWeek;
		mDayDate = dayDate;
		mEntries = entries;
	}

	public String getDayOfWeek() {
		return mDayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		mDayOfWeek = dayOfWeek;
	}

	public List<Entry> getEntries() {
		return mEntries;
	}

	public void setEntries(List<Entry> entries) {
		mEntries = entries;
	}

	public Date getDayDate() {
		return mDayDate;
	}

	public void setDayDate(Date dayDate) {
		mDayDate = dayDate;
	}
}
