package au.com.bfbapps.timesheetme.models;

import com.google.gson.annotations.SerializedName;

public class Task {

	@SerializedName("taskId")
	private long mTaskId;
	@SerializedName("taskName")
	private String mTaskName;

	public Task(){

	}

	public Task(String taskName){
		mTaskName = taskName;
	}

	public Task(long taskId, String taskName){
		mTaskId = taskId;
		mTaskName = taskName;
	}

	public long getTaskId() {
		return mTaskId;
	}

	public void setTaskId(long taskId) {
		mTaskId = taskId;
	}

	public String getTaskName() {
		return mTaskName;
	}

	public void setTaskName(String taskName) {
		mTaskName = taskName;
	}
}
