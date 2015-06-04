package au.com.bfbapps.timesheetme.models;

import com.google.gson.annotations.SerializedName;

public class Job {

	@SerializedName("jobId")
	private int mJobId;
	@SerializedName("jobName")
	private String mJobName;

	public Job(){

	}

	public Job(String jobName){
		mJobName = jobName;
	}

	public Job( int jobId, String jobName){
		mJobId = jobId;
		mJobName = jobName;
	}

	public int getJobId() {
		return mJobId;
	}

	public void setJobId(int jobId) {
		mJobId = jobId;
	}

	public String getJobName() {
		return mJobName;
	}

	public void setJobName(String jobName) {
		mJobName = jobName;
	}
}
