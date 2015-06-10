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

	public Entry(){

	}

	public Entry(Date mDate, String mStart, String mFinish, double mTotalBreak, double mTotalHoursWorked, Job mJobId, Task mtaskId) {
		this.mDate = mDate;
		this.mStart = mStart;
		this.mFinish = mFinish;
		this.mTotalBreak = mTotalBreak;
		this.mTotalHoursWorked = mTotalHoursWorked;
		this.mJob = mJobId;
		this.mTask = mtaskId;
	}

    public Entry(long mEntryId, Date mDate, String mStart, String mFinish, double mTotalBreak, double mTotalHoursWorked, Job mJobId, Task mtaskId) {
        this.mEntryId = mEntryId;
        this.mDate = mDate;
        this.mStart = mStart;
        this.mFinish = mFinish;
        this.mTotalBreak = mTotalBreak;
        this.mTotalHoursWorked = mTotalHoursWorked;
        this.mJob = mJobId;
        this.mTask = mtaskId;
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

	public void setTask(Task mtaskId) {
		this.mTask = mtaskId;
	}
}
