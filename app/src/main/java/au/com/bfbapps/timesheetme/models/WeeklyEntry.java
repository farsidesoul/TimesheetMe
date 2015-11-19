package au.com.bfbapps.timesheetme.models;

import java.util.Date;
import java.util.List;

public class WeeklyEntry {

	private String dayOfWeek;
	private Date dayDate;
	private List<Entry> entries;

	public WeeklyEntry(){

	}

	public WeeklyEntry(String dayOfWeek){
		this.dayOfWeek = dayOfWeek;
	}

	public WeeklyEntry(String dayOfWeek, Date dayDate, List<Entry> entries){
		this.dayOfWeek = dayOfWeek;
		this.dayDate = dayDate;
		this.entries = entries;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}
}
