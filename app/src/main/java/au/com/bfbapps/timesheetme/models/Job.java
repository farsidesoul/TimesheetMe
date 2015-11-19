package au.com.bfbapps.timesheetme.models;

public class Job {

	private long jobId;
	private String jobName;

	public Job(){

	}

	public Job(String jobName){
		this.jobName = jobName;
	}

	public Job(long jobId, String jobName){
		this.jobId = jobId;
		this.jobName = jobName;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
}
