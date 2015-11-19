package au.com.bfbapps.timesheetme.models;

public class Task {

	private long taskId;
	private String taskName;

	public Task(){

	}

	public Task(String taskName){
		this.taskName = taskName;
	}

	public Task(long taskId, String taskName){
		this.taskId = taskId;
		this.taskName = taskName;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
