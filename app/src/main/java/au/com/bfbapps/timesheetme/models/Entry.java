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
    private long mJobId;
    @SerializedName("taskId")
    private long mTaskId;

	public Entry(){

	}

	public Entry(Date mDate, String mStart, String mFinish, double mTotalBreak, double mTotalHoursWorked, long mJobId, long mtaskId) {
		this.mDate = mDate;
		this.mStart = mStart;
		this.mFinish = mFinish;
		this.mTotalBreak = mTotalBreak;
		this.mTotalHoursWorked = mTotalHoursWorked;
		this.mJobId = mJobId;
		this.mTaskId = mtaskId;
	}

    public Entry(long mEntryId, Date mDate, String mStart, String mFinish, double mTotalBreak, double mTotalHoursWorked, long mJobId, long mtaskId) {
        this.mEntryId = mEntryId;
        this.mDate = mDate;
        this.mStart = mStart;
        this.mFinish = mFinish;
        this.mTotalBreak = mTotalBreak;
        this.mTotalHoursWorked = mTotalHoursWorked;
        this.mJobId = mJobId;
        this.mTaskId = mtaskId;
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

	public long getJobId() {
		return mJobId;
	}

	public void setJobId(long jobId) {
		mJobId = jobId;
	}

	public long getTaskId() {
		return mTaskId;
	}

	public void setTaskId(long mtaskId) {
		this.mTaskId = mtaskId;
	}
}
