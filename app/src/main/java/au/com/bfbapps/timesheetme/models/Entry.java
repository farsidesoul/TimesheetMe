package au.com.bfbapps.timesheetme.models;

import java.util.Date;

public class Entry {

	public static final byte NO_JOB_NO_TASK = -1;

	public static final byte MODE_SIMPLE = 1;
	public static final byte MODE_ADVANCED = 2;

	private long entryId;
	private Date date;
	private long start;
	private long finish;
    private int totalBreak;
	private int totalTimeWorkedInMinutes;
    private Job job;
    private Task task;
	private int mode;

	public Entry(){

	}

	public Entry(Date date, long start, long finish, int totalBreak, int mode) {
		this.date = date;
		this.start = start;
		this.finish = finish;
		this.totalBreak = totalBreak;
		totalTimeWorkedInMinutes = (int)((finish / 60000) - (start / 60000) - (totalBreak));
		this.mode = mode;
	}

	public Entry(Date date, long start, long finish, int totalBreak, Job job, Task task, int mode) {
		this.date = date;
		this.start = start;
		this.finish = finish;
		this.totalBreak = totalBreak;
		totalTimeWorkedInMinutes = (int)((finish / 60000) - (start / 60000) - (totalBreak));
		this.job = job;
		this.task = task;
		this.mode = mode;
	}

	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getFinish() {
		return finish;
	}

	public void setFinish(long finish) {
		this.finish = finish;
	}

	public int getTotalBreak() {
		return totalBreak;
	}

	public void setTotalBreak(int totalBreak) {
		this.totalBreak = totalBreak;
	}

	public int getTotalTimeWorkedInMinutes() {
		return totalTimeWorkedInMinutes;
	}

	public void setTotalTimeWorkedInMinutes(int totalTimeWorkedInMinutes) {
		this.totalTimeWorkedInMinutes = totalTimeWorkedInMinutes;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task taskId) {
		this.task = taskId;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
}
