package au.com.bfbapps.timesheetme.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.com.bfbapps.timesheetme.Util.Dates;
import au.com.bfbapps.timesheetme.models.Entry;
import au.com.bfbapps.timesheetme.models.Job;
import au.com.bfbapps.timesheetme.models.Task;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Log tag
    private static final String TAG = "DatabaseHelper";

    // Current Db Version
    private static final int DATABASE_VERSION = 1;

    // DB Name
    private static final String DATABASE_NAME = "timesheetMe";

    // Tables
    private static final String TABLE_ENTRY = "dailyEntry";
    private static final String TABLE_JOB = "job";
    private static final String TABLE_TASK = "task";

    // Common Column names
    private static final String COLUMN_ID = "id";

    // Daily Entry table column names
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_START_TIME = "startTime";
    private static final String COLUMN_FINISH_TIME = "finishTime";
    private static final String COLUMN_TOTAL_BREAK = "totalBreak";
    private static final String COLUMN_TOTAL_HOURS_WORKED = "totalHoursWorked";
    private static final String COLUMN_JOB_ID = "jobId";
    private static final String COLUMN_TASK_ID = "taskId";

    // Job Table Columns
    private static final String COLUMN_JOB_NAME = "jobName";

    // Task Table Columns
    private static final String COLUMN_TASK_NAME = "taskName";

    // Table Creation
    // Daily Entry Table create
    private static final String CREATE_TABLE_DAILY_ENTRY = "CREATE TABLE "
            + TABLE_ENTRY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_DATE
            + " DATE," + COLUMN_START_TIME + " DATETIME," + COLUMN_FINISH_TIME + " DATETIME,"
            + COLUMN_TOTAL_BREAK + " INTEGER," + COLUMN_TOTAL_HOURS_WORKED + " REAL,"
            + COLUMN_JOB_ID + " INTEGER," + COLUMN_TASK_ID + " INTEGER, FOREIGN KEY("
            + COLUMN_JOB_ID + ") REFERENCES " + TABLE_JOB + "(" + COLUMN_ID + "), FOREIGN KEY("
            + COLUMN_TASK_ID + ") REFERENCES " + TABLE_TASK + "(" + COLUMN_ID + "))";

    // Job Table Create
    private static final String CREATE_TABLE_JOB = "CREATE TABLE " + TABLE_JOB
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_JOB_NAME + " TEXT)";

    // Task Table Create
    private static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_TASK_NAME + " TEXT)";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_JOB);
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK);
        sqLiteDatabase.execSQL(CREATE_TABLE_DAILY_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

        // Create new Tables
        onCreate(sqLiteDatabase);
    }


	//region Entry CRUD
	/**
	 * Create entry in DB
	 * @param entry Entry to be inserted into DB
	 * @return entryId of inserted entry
	 */
    public long createEntry(Entry entry){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_DATE, Dates.ConvertDateToString(entry.getDate()));
		values.put(COLUMN_START_TIME, entry.getStart());
		values.put(COLUMN_FINISH_TIME, entry.getFinish());
		values.put(COLUMN_TOTAL_BREAK, entry.getTotalBreak());
		values.put(COLUMN_TOTAL_HOURS_WORKED, entry.getTotalHoursWorked());
		values.put(COLUMN_JOB_ID, entry.getJobId());
		values.put(COLUMN_TASK_ID, entry.getTaskId());

		// insert row
		return db.insert(TABLE_ENTRY, null, values);


    }

	/**
	 * Fetch a single entry
	 */
	public Entry getEntry(long entryId){
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_ENTRY + " WHERE "
				+ COLUMN_ID + " = " + entryId;

		Log.e(TAG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		Entry entry = new Entry();

		if (c != null) {
			c.moveToFirst();

			entry.setEntryId(c.getInt(c.getColumnIndex(COLUMN_ID)));
			entry.setDate(Dates.ConvertStringToDate(c.getString(c.getColumnIndex(COLUMN_DATE))));
			entry.setStart(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
			entry.setFinish(c.getString(c.getColumnIndex(COLUMN_FINISH_TIME)));
			entry.setTotalBreak(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_BREAK)));
			entry.setTotalHoursWorked(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_HOURS_WORKED)));
			entry.setJobId(c.getInt(c.getColumnIndex(COLUMN_JOB_ID)));
			entry.setTaskId(c.getInt(c.getColumnIndex(COLUMN_TASK_ID)));
		}
		return entry;
	}

	/**
	 * Get all entries entered
	 * @return ArrayList of entries
	 */
    public List<Entry> getAllEntries(){
		List<Entry> entries = new ArrayList<>();
		String selectQuery = "SELECT * FROM " + TABLE_ENTRY;

		Log.e(TAG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// Loop through the rows and add to the list
		if (c.moveToFirst()){
			do{
				Entry entry = new Entry();
				entry.setEntryId(c.getInt(c.getColumnIndex(COLUMN_ID)));
				entry.setDate(Dates.ConvertStringToDate(c.getString(c.getColumnIndex(COLUMN_DATE))));
				entry.setStart(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
				entry.setFinish(c.getString(c.getColumnIndex(COLUMN_FINISH_TIME)));
				entry.setTotalBreak(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_BREAK)));
				entry.setTotalHoursWorked(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_HOURS_WORKED)));
				entry.setJobId(c.getInt(c.getColumnIndex(COLUMN_JOB_ID)));
				entry.setTaskId(c.getInt(c.getColumnIndex(COLUMN_TASK_ID)));

				entries.add(entry);

			} while (c.moveToNext());
		}
		c.close();
		return entries;
	}

	public List<Entry> getAllEntriesByDate(Date date){
		List<Entry> entries = new ArrayList<>();

		String selectQuery = "SELECT * FROM " + TABLE_ENTRY + " WHERE "
				+ COLUMN_DATE + " = '" + Dates.ConvertDateToString(date) + "'";

		Log.e(TAG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if(c.moveToFirst()){
			do {
				Entry entry = new Entry();
				entry.setEntryId(c.getInt(c.getColumnIndex(COLUMN_ID)));
				entry.setDate(Dates.ConvertStringToDate(c.getString(c.getColumnIndex(COLUMN_DATE))));
				entry.setStart(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
				entry.setFinish(c.getString(c.getColumnIndex(COLUMN_FINISH_TIME)));
				entry.setTotalBreak(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_BREAK)));
				entry.setTotalHoursWorked(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_HOURS_WORKED)));
				entry.setJobId(c.getInt(c.getColumnIndex(COLUMN_JOB_ID)));
				entry.setTaskId(c.getInt(c.getColumnIndex(COLUMN_TASK_ID)));

				entries.add(entry);
			} while (c.moveToNext());
		}
		c.close();
		return entries;

	}

	/**
	 * Get all Entries by a job id
	 * @param jobId job Id to get entries for
	 * @return list of entries
	 */
	public List<Entry> getAllEntriesByJobId(long jobId){
		List<Entry> entries = new ArrayList<>();

		String selectQuery = "SELECT * FROM " + TABLE_ENTRY + " te, "
				+ TABLE_JOB + " tj, WHERE tj." + COLUMN_ID + " = te."
				+ COLUMN_JOB_ID;

		Log.e(TAG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if(c.moveToFirst()){
			do {
				Entry entry = new Entry();
				entry.setEntryId(c.getInt(c.getColumnIndex(COLUMN_ID)));
				entry.setDate(Dates.ConvertStringToDate(c.getString(c.getColumnIndex(COLUMN_DATE))));
				entry.setStart(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
				entry.setFinish(c.getString(c.getColumnIndex(COLUMN_FINISH_TIME)));
				entry.setTotalBreak(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_BREAK)));
				entry.setTotalHoursWorked(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_HOURS_WORKED)));
				entry.setJobId(c.getInt(c.getColumnIndex(COLUMN_JOB_ID)));
				entry.setTaskId(c.getInt(c.getColumnIndex(COLUMN_TASK_ID)));

				entries.add(entry);
			} while (c.moveToNext());
		}
		c.close();
		return entries;
	}

	public List<Entry> getAllEntriesByTaskId(long taskId){
		List<Entry> entries = new ArrayList<>();

		String selectQuery = "SELECT * FROM " + TABLE_ENTRY + " te, "
				+ TABLE_TASK + " tt, WHERE tt." + COLUMN_ID + " = te."
				+ COLUMN_TASK_ID;

		Log.e(TAG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if(c.moveToFirst()){
			do {
				Entry entry = new Entry();
				entry.setEntryId(c.getInt(c.getColumnIndex(COLUMN_ID)));
				entry.setDate(Dates.ConvertStringToDate(c.getString(c.getColumnIndex(COLUMN_DATE))));
				entry.setStart(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
				entry.setFinish(c.getString(c.getColumnIndex(COLUMN_FINISH_TIME)));
				entry.setTotalBreak(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_BREAK)));
				entry.setTotalHoursWorked(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_HOURS_WORKED)));
				entry.setJobId(c.getInt(c.getColumnIndex(COLUMN_JOB_ID)));
				entry.setTaskId(c.getInt(c.getColumnIndex(COLUMN_TASK_ID)));

				entries.add(entry);
			} while (c.moveToNext());
		}
		c.close();
		return entries;
	}

	/**
	 * Update an entry with new values
	 * @param entry entry to update
	 * @return id of updated entry
	 */
	public int updateEntry(Entry entry){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_DATE, Dates.ConvertDateToString(entry.getDate()));
		values.put(COLUMN_START_TIME, entry.getStart());
		values.put(COLUMN_FINISH_TIME, entry.getFinish());
		values.put(COLUMN_TOTAL_BREAK, entry.getTotalBreak());
		values.put(COLUMN_TOTAL_HOURS_WORKED, entry.getTotalHoursWorked());
		values.put(COLUMN_JOB_ID, entry.getJobId());
		values.put(COLUMN_TASK_ID, entry.getTaskId());

		return db.update(TABLE_ENTRY, values, COLUMN_ID + " = ?",
				new String[] { String.valueOf(entry.getEntryId())});
	}

	/**
	 * Delete entry by Id
	 * @param entryId Id of entry to delete
	 */
	public void deleteEntry(long entryId){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ENTRY, COLUMN_ID + " = ?",
				new String[] { String.valueOf(entryId)});
	}
	//endregion

	//region Task CRUD

	/**
	 * Create a new task
	 * @param task task to be inserted
	 * @return id of task inserted
	 */
	public long createTask(Task task){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_TASK_NAME, task.getTaskName());

		// insert row
		return db.insert(TABLE_TASK, null, values);
	}


	/**
	 * Get ArrayList of all tasks
	 * @return ArrayList of tasks
	 */
	public List<Task> getAllTasks(){
		List<Task> tasks = new ArrayList<>();
		String selectQuery = "SELECT * FROM " + TABLE_TASK;

		Log.e(TAG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				Task task = new Task();
				task.setTaskId(c.getInt(c.getColumnIndex(COLUMN_ID)));
				task.setTaskName(c.getString(c.getColumnIndex(COLUMN_TASK_NAME)));

				tasks.add(task);
			} while (c.moveToNext());
		}
		c.close();
		return tasks;
	}

	public Task getTaskById(long taskId){
		Task task = new Task();
		String selectQuery = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_ID + " = " + taskId;

		Log.e(TAG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.getCount() > 0){
			c.moveToFirst();

			task.setTaskId(c.getInt(c.getColumnIndex(COLUMN_ID)));
			task.setTaskName(c.getString(c.getColumnIndex(COLUMN_TASK_NAME)));

			c.close();
			return task;
		}
		return null;
	}

	/**
	 * Get task by name
	 * @param taskName task name to search for
	 * @return new Task
	 */
	public Task getTaskByName(String taskName){
		Task task = new Task();
		String selectQuery = "SELECT * FROM " + TABLE_TASK + " WHERE "
				+ COLUMN_TASK_NAME + " = '" + taskName + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.getCount() > 0){
			c.moveToFirst();

			task.setTaskId(c.getInt(c.getColumnIndex(COLUMN_ID)));
			task.setTaskName(c.getString(c.getColumnIndex(COLUMN_TASK_NAME)));

			c.close();
			return task;
		}
		return null;
	}

	/**
	 * Update task
	 * @param task task details to update
	 * @return id of task updated
	 */
	public int updateTask(Task task){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_TASK_NAME, task.getTaskName());

		return db.update(TABLE_TASK, values, COLUMN_ID + " = ?",
				new String[] { String.valueOf(task.getTaskId())});
	}

	/**
	 * Delete a task by id
	 * @param task task to be deleted
	 * @param shouldDeleteAllEntries if true, delete all entries that have this taskId
	 */
	public void deleteTask(Task task, boolean shouldDeleteAllEntries){
		SQLiteDatabase db = this.getWritableDatabase();

		if(shouldDeleteAllEntries){
			List<Entry> allEntires = getAllEntriesByTaskId(task.getTaskId());

			// Delete all Entries
			for (Entry entries : allEntires){
				deleteEntry(entries.getEntryId());
			}
		}

		db.delete(TABLE_TASK, COLUMN_ID + " = ?",
				new String[]{String.valueOf(task.getTaskId())});
	}

	//endregion

	//region Job CRUD

	/**
	 * Create a new job
	 * @param job job to be inserted
	 * @return id of job inserted
	 */
	public long createJob(Job job){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_JOB_NAME, job.getJobName());

		// insert row
		return db.insert(TABLE_JOB, null, values);
	}


	/**
	 * Get ArrayList of all jobs
	 * @return ArrayList of jobs
	 */
	public List<Job> getAllJobs(){
		List<Job> jobs = new ArrayList<>();
		String selectQuery = "SELECT * FROM " + TABLE_JOB;

		Log.e(TAG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				Job job = new Job();
				job.setJobId(c.getInt(c.getColumnIndex(COLUMN_ID)));
				job.setJobName(c.getString(c.getColumnIndex(COLUMN_JOB_NAME)));

				jobs.add(job);
			} while (c.moveToNext());
		}
		c.close();
		return jobs;
	}

	public Job getJobById(long jobId){

		String selectQuery = "SELECT * FROM " + TABLE_JOB + " WHERE " + COLUMN_ID + " = " + jobId;

		Log.e(TAG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.getCount() > 0){
			c.moveToFirst();
			Job job = new Job();
			job.setJobId(c.getInt(c.getColumnIndex(COLUMN_ID)));
			job.setJobName(c.getString(c.getColumnIndex(COLUMN_JOB_NAME)));

			c.close();
			return job;
		}
		return null;
	}

	/**
	 * Get Job by name
	 * @param jobName job name to search for
	 * @return job
	 */
	public Job getJobByName(String jobName){

		String selectQuery = "SELECT * FROM " + TABLE_JOB + " WHERE "
				+ COLUMN_JOB_NAME + " = '" + jobName + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.getCount() > 0){
			c.moveToFirst();
			Job job = new Job();
			job.setJobId(c.getInt(c.getColumnIndex(COLUMN_ID)));
			job.setJobName(c.getString(c.getColumnIndex(COLUMN_JOB_NAME)));

			c.close();
			return job;
		}
		return null;
	}

	/**
	 * Update job
	 * @param job Job details to update
	 * @return id of job updated
	 */
	public int updateJob(Job job){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_JOB_NAME, job.getJobName());

		return db.update(TABLE_JOB, values, COLUMN_ID + " = ?",
				new String[] { String.valueOf(job.getJobId())});
	}

	/**
	 * Delete a job by id
	 * @param job task to be deleted
	 * @param shouldDeleteAllEntries if true, delete all entries that have this taskId
	 */
	public void deleteJob(Job job, boolean shouldDeleteAllEntries){
		SQLiteDatabase db = this.getWritableDatabase();

		if(shouldDeleteAllEntries){
			List<Entry> allEntires = getAllEntriesByJobId(job.getJobId());

			// Delete all Entries
			for (Entry entries : allEntires){
				deleteEntry(entries.getEntryId());
			}
		}

		db.delete(TABLE_TASK, COLUMN_ID + " = ?",
				new String[]{String.valueOf(job.getJobId())});
	}
	//endregion

	// Close the db
	public void closeDB(){
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
}
