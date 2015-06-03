package au.com.bfbapps.timesheetme.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DailyEntry {

	@SerializedName("entryId")
	private int mEntryId;
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
    private int mJobId;
    @SerializedName("taskId")
    private int mtaskId;

    public DailyEntry(int mEntryId, Date mDate, String mStart, String mFinish, double mTotalBreak, double mTotalHoursWorked, int mJobId, int mtaskId) {
        this.mEntryId = mEntryId;
        this.mDate = mDate;
        this.mStart = mStart;
        this.mFinish = mFinish;
        this.mTotalBreak = mTotalBreak;
        this.mTotalHoursWorked = mTotalHoursWorked;
        this.mJobId = mJobId;
        this.mtaskId = mtaskId;
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

	public int getJobId() {
		return mJobId;
	}

	public void setJobId(int jobId) {
		mJobId = jobId;
	}

	public int getMtaskId() {
		return mtaskId;
	}

	public void setMtaskId(int mtaskId) {
		this.mtaskId = mtaskId;
	}
}
