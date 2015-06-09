package au.com.bfbapps.timesheetme.models;

import java.util.List;

public class WeeklyEntry {

	private String mDayOfWeek;
	private List<Entry> mEntries;

	public WeeklyEntry(String dayOfWeek, List<Entry> entries){
		mDayOfWeek = dayOfWeek;
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
}
