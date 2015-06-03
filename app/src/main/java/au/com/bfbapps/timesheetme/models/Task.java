package au.com.bfbapps.timesheetme.models;

import com.google.gson.annotations.SerializedName;

public class Task {

	@SerializedName("taskId")
	private int mTaskId;
	@SerializedName("taskName")
	private String mTaskName;

	public Task(){

	}

	public Task(int taskId, String taskName){
		mTaskId = taskId;
		mTaskName = taskName;
	}

	public int getTaskId() {
		return mTaskId;
	}

	public void setTaskId(int taskId) {
		mTaskId = taskId;
	}

	public String getTaskName() {
		return mTaskName;
	}

	public void setTaskName(String taskName) {
		mTaskName = taskName;
	}
}
