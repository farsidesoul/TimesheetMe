package au.com.bfbapps.timesheetme.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Entry {

	@SerializedName("entryId")
	private int mEntryId;
	@SerializedName("date")
	private Date mDate;
	@SerializedName("startTime")
	private Date mStart;
	@SerializedName("finishTime")
	private Date mFinish;
    @SerializedName("totalBreak")
    private double mTotalBreak;
	@SerializedName("totalHoursWorked")
	private double mTotalHoursWorked;
    @SerializedName("jobId")
    private int mJobId;
    @SerializedName("taskId")
    private int mTaskId;

	public Entry(){

	}

    public Entry(int mEntryId, Date mDate, Date mStart, Date mFinish, double mTotalBreak, double mTotalHoursWorked, int mJobId, int mtaskId) {
        this.mEntryId = mEntryId;
        this.mDate = mDate;
        this.mStart = mStart;
        this.mFinish = mFinish;
        this.mTotalBreak = mTotalBreak;
        this.mTotalHoursWorked = mTotalHoursWorked;
        this.mJobId = mJobId;
        this.mTaskId = mtaskId;
    }

	public int getEntryId() {
		return mEntryId;
	}

	public void setEntryId(int entryId) {
		mEntryId = entryId;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public Date getStart() {
		return mStart;
	}

	public void setStart(Date start) {
		mStart = start;
	}

	public Date getFinish() {
		return mFinish;
	}

	public void setFinish(Date finish) {
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

	public int getJobId() {
		return mJobId;
	}

	public void setJobId(int jobId) {
		mJobId = jobId;
	}

	public int getTaskId() {
		return mTaskId;
	}

	public void setTaskId(int mtaskId) {
		this.mTaskId = mtaskId;
	}
}
