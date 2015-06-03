package au.com.bfbapps.timesheetme.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Log tag
    private static final String TAG = "DatabaseHelper";

    // Current Db Version
    private static final int DATABASE_VERSION = 1;

    // DB Name
    private static final String DATABASE_NAME = "timesheetMe";

    // Tables
    private static final String TABLE_DAILY_ENTRY = "dailyEntry";
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
            + TABLE_DAILY_ENTRY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_DATE
            + " DATE," + COLUMN_START_TIME + " DATETIME," + COLUMN_FINISH_TIME + " DATETIME,"
            + COLUMN_TOTAL_BREAK + " REAL," + COLUMN_TOTAL_HOURS_WORKED + " REAL,"
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_ENTRY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

        // Create new Tables
        onCreate(sqLiteDatabase);
    }
}
