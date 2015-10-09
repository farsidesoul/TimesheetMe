package au.com.bfbapps.timesheetme.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Entry {

	@SerializedName("entryId")
	private long mEntryId;
	@SerializedName("date")
	private Date mDate;
	@SerializedName("startTime")
	private String mStart;
	@SerializedName("finishTime")
	private String mFinish;
    @SerializedName("totalBreak")
    private double mTotalBreak;
	@SerializedName("totalHoursWorked")
	private double mTotalHoursWorked;
    @SerializedName("jobId")
    private Job mJob;
    @SerializedName("taskId")
    private Task mTask;
	@SerializedName("mode")
	private int mMode;

	public Entry(){

	}

	public Entry(Date date, String start, String finish, double totalBreak, double totalHoursWorked, Job job, Task task, int mode) {
		mDate = date;
		mStart = start;
		mFinish = finish;
		mTotalBreak = totalBreak;
		mTotalHoursWorked = totalHoursWorked;
		mJob = job;
		mTask = task;
		mMode = mode;
	}

	public Entry(long entryId, Date date, String start, String finish, double totalBreak, double totalHoursWorked, Job job, Task task, int mode) {
		mEntryId = entryId;
		mDate = date;
		mStart = start;
		mFinish = finish;
		mTotalBreak = totalBreak;
		mTotalHoursWorked = totalHoursWorked;
		mJob = job;
		mTask = task;
		mMode = mode;
	}

	public long getEntryId() {
		return mEntryId;
	}

	public void setEntryId(long entryId) {
		mEntryId = entryId;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
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

	public double getTotalBreak() {
		return mTotalBreak;
	}

	public void setTotalBreak(double totalBreak) {
		mTotalBreak = totalBreak;
	}

	public double getTotalHoursWorked() {
		return mTotalHoursWorked;
	}

	public void setTotalHoursWorked(double totalHoursWorked) {
		mTotalHoursWorked = totalHoursWorked;
	}

	public Job getJob() {
		return mJob;
	}

	public void setJob(Job job) {
		mJob = job;
	}

	public Task getTask() {
		return mTask;
	}

	public void setTask(Task taskId) {
		this.mTask = taskId;
	}

	public int getMode() {
		return mMode;
	}

	public void setMode(int mode) {
		mMode = mode;
	}
}
